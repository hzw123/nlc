package com.nlc.nraas.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.Attribute;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeritrixJmxClient {
	
	private final static Logger logger = LoggerFactory.getLogger(HeritrixJmxClient.class);
	
	private final static Map<String, MBeanServerConnection> connectionMap = new HashMap<String, MBeanServerConnection>();

	private static MBeanServerConnection getConnection(String hostName, String portNum) throws Exception {
		if (StringUtils.isBlank(hostName)) {
			hostName = "localhost";
		}
		if (StringUtils.isBlank(portNum)) {
			portNum = "8849";
		}
//		System.out.println("portNum=" + portNum);
		String hostKey = hostName + ":" + portNum;
		if (connectionMap.containsKey(hostKey)) {
			return connectionMap.get(hostKey);
		}
		JMXServiceURL u = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"
				+ hostName + ":" + portNum + "/jmxrmi");

		JMXConnector jmxConnector = JMXConnectorFactory.connect(u);
		MBeanServerConnection connection = jmxConnector.getMBeanServerConnection();
		connectionMap.put(hostKey, connection);
		
		return connection;
	}
	
	/**
	 * 获取某个heritrix服务器上的实例数
	 * 
	 * @return
	 * @throws Exception 
	 */
	public static List<Map<String, String>> getInstances(String host, String port) throws Exception {
		
		MBeanServerConnection connection = getConnection(host, port);
		
		Set<ObjectInstance> beans = connection.queryMBeans(new ObjectName("*:*,type=CrawlService"), null);
		List<Map<String, String>> instances = new ArrayList<Map<String, String>>();
		for (ObjectInstance objectInstance : beans) {
			ObjectName mBeanName = objectInstance.getObjectName();
			Map<String, String> instance = new HashMap<String, String>();
			instance.put("name", mBeanName.getKeyProperty("name"));
			instance.put("beanName", mBeanName.toString());
			instance.put("status", connection.getAttribute(mBeanName, "Status").toString());
			instances.add(instance);
		}
		
		instances.sort(new Comparator<Map<String, String>>() {
		    @Override
		    public int compare(Map<String, String> m1, Map<String, String> m2) {
		        return m1.get("name").compareTo(m2.get("name"));
		     }
		});
		return instances;
    }
	
	/**
	 * 启动抓取
	 * 
	 * @return
	 * @throws Exception 
	 */
	public static Object startCrawl(String host, String port, String beanName) throws Exception {
		
		return executeOperation(host, port, beanName, "startCrawling");
    }
	
	/**
	 * 与某个heritrix服务器上的某个名称的实例添加一个抓取任务
	 * 
	 * @param host
	 * @param port
	 * @param beanName
	 * @param profileName
	 * @param jobName
	 * @param jobDesc
	 * @param seeds
	 * @return
	 * @throws Exception
	 */
	public static Object addJobBasedon(String host, String port, String beanName, String profileName, String jobName,
			String jobDesc, String seeds) throws Exception {
		if (StringUtils.isBlank(profileName)) {
			profileName = "default";
		}
		return executeOperation(host, port, beanName, "addJobBasedon", profileName, jobName, jobDesc, seeds);
	}
	
	/**
	 * 添加一个抓取任务，使用外部的order文件
	 * 
	 * @param host
	 * @param port
	 * @param beanName
	 * @param profileUrl
	 * @param jobName
	 * @param jobDesc
	 * @param seeds
	 * @return
	 * @throws Exception
	 */
	public static Object addJob(String host, String port, String beanName, String profileUrl, String jobName,
			String jobDesc, String seeds) throws Exception {
		return executeOperation(host, port, beanName, "addJob", profileUrl, jobName, jobDesc, seeds);
	}
	
	/**
	 * 获取抓取任务的运行状态数值
	 * 
	 * @param host
	 * @param port
	 **@param beanName
	 * @param jobName
	 * @param jobUid
	 * @return
	 * @throws Exception
	 */
	public static List<Attribute> getJobReport(String host, String port, String beanName, String jobName, String jobUid) throws Exception {
		
		String jobBeanName = getServiceJobBeanName(beanName, jobName, jobUid);
		executeOperation(host, port, jobBeanName, "progressStatisticsLegend");
		executeOperation(host, port, jobBeanName, "progressStatistics");
		executeOperation(host, port, jobBeanName, "frontierReport", "all");
		return getAttributes(host, port, jobBeanName, new String[]{
				/** The time in seconds elapsed since the crawl began. */
		        "CrawlTime",
		        /** The current download rate in URI/s. */
		        "CurrentDocRate",
		        /** The current download rate in kB/s. */
		        "CurrentKbRate",
		        /** The number of URIs discovered by Heritrix. */
		        "DiscoveredCount",
		        /** The average download rate in URI/s. */
		        "DocRate",
		        /** The number of URIs downloaded by Heritrix. */
		        "DownloadedCount",
		        /** A string summarizing the Heritrix frontier. */
		        "FrontierShortReport",
		        /** The average download rate in kB/s. */
		        "KbRate",
		        /** The job status (Heritrix status). */
		        "Status",
		        /** The number of active toe threads. */
		        "ThreadCount",
		        "ThreadsShortReport",
		        "TotalData",
		        "Name",
		        "UID"
		});
	}
	
	/**
	 * 暂停一个进行中的任务
	 * 
	 * @param host
	 * @param port
	 * @param beanName
	 * @param jobName
	 * @param jobUid
	 * @return
	 * @throws Exception
	 */
	public static Object pause(String host, String port, String beanName, String jobName, String jobUid) throws Exception {
		String jobBeanName = getServiceJobBeanName(beanName, jobName, jobUid);
		return executeOperation(host, port, jobBeanName, "pause");
	}
	
	
	/**
	 * 继续执行一个处于暂停状态的任务
	 * 
	 * @param host
	 * @param port
	 * @param beanName
	 * @param jobName
	 * @param jobUid
	 * @return
	 * @throws Exception
	 */
	public static Object resume(String host, String port,  String beanName, String jobName, String jobUid) throws Exception {
		String jobBeanName = getServiceJobBeanName(beanName, jobName, jobUid);
		return executeOperation(host, port, jobBeanName, "resume");
	}
	
	/**
	 * 终止正在进行中的任务
	 * 
	 * @param host
	 * @param port
	 * @param beanName
	 * @param jobUid
	 * @return
	 * @throws Exception
	 */
	public static Object teminate(String host, String port, String beanName,
			String jobUid) throws Exception {
		return executeOperation(host, port, beanName, "deleteJob", jobUid);
	}
	
	/**
	 * @param host
	 * @param port
	 * @param newBeanName
	 * @return
	 * @throws Exception
	 */
	public static Object createInstance(String host, String port, String newBeanName) throws Exception {
		
		MBeanServerConnection connection = getConnection(host, port);
		
	    ObjectInstance obj = connection.createMBean("org.archive.crawler.Heritrix", new ObjectName(newBeanName));
	    logger.debug("\n===============createMBean("+ newBeanName + ")===============\n" + obj);
		
		return obj;
	}
	
	/**
	 * @param host
	 * @param port
	 * @param beanName
	 * @return
	 * @throws Exception
	 */
	public static Object deleteInstance(String host, String port, String beanName) throws Exception {
		return executeOperation(host, port, beanName, "destroy");
	}
	
	/**
     * Execute a command on a bean.
     *
     * @param host
	 * @param port
     * @param beanName
     *            Name of the bean.
     * @param operation
     *            Command to execute.
     * @param args
     *            Arguments to the command. Only string arguments are possible
     *            at the moment.
     * @return The return value of the executed command.
     */
	public static Object executeOperation(String host, String port, String beanName, String operation,
            String... args) throws Exception {
		
		MBeanServerConnection connection = getConnection(host, port);
		
		final String[] signature = new String[args.length];
        Arrays.fill(signature, String.class.getName());
        Object obj = connection.invoke(new ObjectName(beanName), operation, args, signature);
		logger.debug("\n===============executeOperation("+ operation + ")===============\n" + obj);
		
		return obj;
	}
	
	/**
     * Get the value of several attributes from a bean
     *
     * @param beanName
     *            Name of the bean to get an attribute for.
     * @param attributes
     *            Name of the attributes to get.
     * @return Value of the attribute.
     */
	public static List<Attribute> getAttributes(String host, String port, String beanName, String[] names) throws Exception {
		
		MBeanServerConnection connection = getConnection(host, port);
		
		List<Attribute> attrs = connection.getAttributes(new ObjectName(beanName), names).asList();
		logger.debug("\n===============getAttributes===============\n" + attrs);
		
		return attrs;
	}
	
	/**
	 * @param beanName
	 * @param jobName
	 * @param jobUid
	 * @return
	 * @throws MalformedObjectNameException 
	 */
	private static String getServiceJobBeanName(String beanName, String jobName, String jobUid) throws MalformedObjectNameException {
		ObjectName oName = new ObjectName(beanName);
		String name = oName.getKeyProperty("name");
		String guiport = oName.getKeyProperty("guiport");
		String jobBeanName = beanName.replace("type=CrawlService", "type=CrawlService.Job").replace("name="+name, "name="+jobName+"-"+jobUid+",mother="+name ).replace("guiport="+guiport+",", "").replace(",guiport="+guiport, "");
		return jobBeanName;
	}
}
