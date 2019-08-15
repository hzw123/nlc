package com.nlc.nraas;

import java.util.*;

import javax.management.*;
import javax.management.remote.*;

public class JmxClientTest
{
	
   /**
    * @param args
    */
   public static void main(String[] args) throws Exception
   {
	   
       String hostName = "127.0.0.1"; //发布JMX服务的地址
       int portNum = 8849; //发布服务的端口

       JMXServiceURL u = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"
               + hostName + ":" + portNum + "/jmxrmi");

       Map<String, Object> auth = new HashMap<String, Object>();
       //认证所需要的用户信息
       auth.put(JMXConnector.CREDENTIALS, new String[]{ "controlRole", "123" });

//       JMXConnector jmxConnector = JMXConnectorFactory.connect(u, auth);
       JMXConnector jmxConnector = JMXConnectorFactory.connect(u, null);
       MBeanServerConnection connection = jmxConnector.getMBeanServerConnection();

       Set<ObjectInstance> beans = connection.queryMBeans(null, null);
       for(ObjectInstance objInstance : beans)
       {
           ObjectName mBeanName = objInstance.getObjectName();
           System.out.println(mBeanName);
           System.out.println(mBeanName.getDomain());
           MBeanInfo mbeanInfo = connection.getMBeanInfo(mBeanName);

//           System.out.println(Arrays.toString(mbeanInfo.getAttributes()));
//           System.out.println(Arrays.toString(mbeanInfo.getOperations()));
           
           if (mBeanName.getDomain().equals("org.archive.crawler") && mBeanName.getKeyProperty("name").equals("Heritrix")) {

        	   System.out.println("================haha====================");
        	   System.out.println(Arrays.toString(mbeanInfo.getAttributes()));
        	   System.out.println(connection.getAttribute(mBeanName, "Status"));

//        	   connection.invoke(mBeanName, "completedJobs", null, null);
        	   
//        	   Object o = connection.invoke(mBeanName, "addJobBasedon", new String[]{"default","two","two desc", "http://mauth.cn:5000/"}, null);
//        	   System.out.println("====================================" + o.toString());
        	   
//        	   Object o = connection.invoke(mBeanName, "startCrawling", null, null);
//        	   System.out.println("====================================" + o.toString());
        	   
//        	   Object o = connection.invoke(mBeanName, "stopCrawling", null, null);
        	   
//        	   Object o = connection.invoke(mBeanName, "pendingJobs", null, null);
//        	   System.out.println("====================================" + o.toString());
           }
       }
       
       Hashtable<String,String> ht = new Hashtable<String,String>();
       ht.put("name", "Heritrix");
       ht.put("type", "CrawlService");
       ht.put("guiport", "6789");
       ht.put("host", "lxl-PC");
       ht.put("jmxport", "8849");
       Set<ObjectInstance> beans2 = connection.queryMBeans(new ObjectName("org.archive.crawler", ht), null);
       System.out.println("==========2: " + beans2.size());
       
       
       //QueryExp 针对mbean的attribitue
       QueryExp queryExp = Query.eq(Query.attr("IsCrawling"),Query.value(false));
       Set<ObjectInstance> beans3 = connection.queryMBeans(null, queryExp);
       System.out.println("==========3: " + beans3.size());
       
       Set<ObjectInstance> beans4 = connection.queryMBeans(new ObjectName("*:*,name=Heritrix"), null);
       System.out.println("==========4: " + beans4.size());
       
       Set<ObjectInstance> beans5 = connection.queryMBeans(new ObjectName("*:*,type=CrawlService"), null);
       System.out.println("==========5: " + beans5.size());
       
       Set<ObjectInstance> beans6 = connection.queryMBeans(new ObjectName("org.archive.crawler:*"), null);
       System.out.println("==========6: " + beans6.size());

       ObjectInstance i = connection.createMBean("org.archive.crawler.Heritrix", new ObjectName("org.archive.crawler:guiport=6789,host=lxl-PC,jmxport=8849,name=yzshan11,type=CrawlService"));
       System.out.println("==========7: " + i);

   }
}