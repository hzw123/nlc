package com.nlc.nraas.tools;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class HeritrixXmlUtils {
	
	private final static Logger logger = LoggerFactory.getLogger(HeritrixXmlUtils.class);
	
	/**
	 * 特殊节点的名称集合，遍历时，哪些节点需要忽略，仅仅是读取其name属性
	 */
	private static Map<String, String> ignoreNames = new HashMap<String, String>();
	
	/**
	 * List类型的节点名称集合
	 */
	private static Map<String, String> listNames = new HashMap<String, String>();
	
	/**
	 * 特殊节点的名称集合，读取xml内容时，哪些节点的内容可以返回
	 */
	private static Map<String, String> valuesNames = new HashMap<String, String>();
	
	public static final String PROFILE_NAME_PATH = "/crawl-order/meta/name";
	
	public static final String PROFILE_DESC_PATH = "/crawl-order/meta/description";
	
	//初始化
	static {
//		String[] ignores = new String[]{"controller","object","newObject","boolean","integer","long","float","string","date","integerList","longList","stringList","text","map"};
		String[] ignores = new String[]{"controller","object","newObject","boolean","integer","long","float","string",/*"date",*/"integerList","longList","stringList","text","map"};
		for(String n : ignores) {
			ignoreNames.put(n, "");
		}
		
		String[] listNs = new String[]{"integerList","longList","stringList"};
		for(String n : listNs) {
			listNames.put(n, "");
		}
		
		String[] values = new String[]{"name", "description", "operator", "organization", "audience", "boolean","integer","long","float","string"};
		for(String n : values) {
			valuesNames.put(n, "");
		}
	}
	
	public static void writeProfile(HttpServletRequest request) throws Exception {
		
		Map<String, String> requestParams = new HashMap<String, String>();

		for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {  
			
			if (entry.getValue() != null) {
//				logger.debug("Key = " + entry.getKey() + ", Value = " + entry.getValue()[0]);
				requestParams.put(entry.getKey(), entry.getValue()[0]);
			} else {
				logger.debug("Key = " + entry.getKey() + ", Empty Value");
				requestParams.put(entry.getKey(), "");
			}
		}  
		requestParams.put("/crawl-order/meta/date", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		
		String profileName = request.getParameter(PROFILE_NAME_PATH);
		Assert.hasLength(profileName, "模版名称不能为空");
		
		String profilesPath = HeritrixXmlUtils.class.getClassLoader().getResource("").getPath() + "/public/profiles";
		SAXReader reader = new SAXReader();
	    Document  document = reader.read(new File(profilesPath + "/default/order.xml"));
	    writeNodes(document.getRootElement(), "", requestParams);

		File f = new File(profilesPath + "/" + profileName);
		f.mkdir();
		
		OutputFormat format = OutputFormat.createPrettyPrint(); // 创建文件输出的时候，自动缩进的格式                    
	    format.setEncoding("UTF-8");//设置编码 
		XMLWriter writer = new XMLWriter(new FileWriter(f.getAbsolutePath() + "/order.xml"), format);
		writer.write(document);
		writer.close();
		
//		XMLWriter writer = new XMLWriter(new FileWriter("D:/work/NLC/workspace/NetworkResourceArchiveAndService/target/"+  (new Random()).nextLong() + ".xml"));
//		writer.write(document);
//		writer.close();
		
		writeJar(f.getAbsolutePath());
	}

	public static Map<String, String> getProfile(String profileName) throws Exception {
		
		String profilesPath = HeritrixXmlUtils.class.getClassLoader().getResource("").getPath() + "/public/profiles";
		SAXReader reader = new SAXReader();
	    Document   document = reader.read(new File(profilesPath + "/" + profileName +"/order.xml"));

		Map<String, String> xmlParams = new HashMap<String, String>();
		
		getValues(document.getRootElement(), "", xmlParams);
		
		return xmlParams;
	}
	
	public static void removeProfile(String profileName) throws Exception {
		
		String profilesPath = HeritrixXmlUtils.class.getClassLoader().getResource("").getPath() + "/public/profiles";
		File f = new File(profilesPath + "/" + profileName);
        if (f.isDirectory()) {
            for (File temp: f.listFiles()) {
            	temp.delete();
            }
        }
        // 目录此时为空，可以删除
        f.delete();
	}
	
	private static void writeNodes(Element node, String path, Map<String, String> requestParams) {

        String newPath = path + "";
        if (ignoreNames.containsKey(node.getName())) {
        	if (node.attributeValue("name") != null) {
        		newPath = newPath + "/" + node.attributeValue("name");
        	} else {
//        		System.out.println("Found strange node ====================: " + node.getPath() + ", my path : " + newPath);
        	}
        } else {
        	newPath = newPath + "/" + node.getName();
        }
        
        if (listNames.containsKey(node.getName())) {
//        	System.out.println("List element found : " + node.getName() + ", new path : " + newPath);
        	
        	String strValues = getListValues(node);
        	if (strValues.equals(requestParams.get(newPath)) || (StringUtils.isBlank(strValues) && StringUtils.isBlank(requestParams.get(newPath)))) {
//        		System.out.println("List value equal : " + requestParams.get(newPath));
        	} else {
        		updateListValues(node, requestParams.get(newPath));
        		logger.debug("Set " +newPath+" list value from " + strValues  + " to new value : " + requestParams.get(newPath));
        	}
        	// 直接返回
        	return;
        }
        
        if (node.getTextTrim().equals(requestParams.get(newPath)) || (StringUtils.isBlank(node.getTextTrim()) && StringUtils.isBlank(requestParams.get(newPath)))) {

        } else {
        	if (requestParams.get(newPath) != null) {
        		logger.debug("Set " +newPath+" value from " +node.getTextTrim() + " to new value : " + requestParams.get(newPath));
        		node.setText(requestParams.get(newPath));
        	} else {
        		logger.debug("Found strange path : " + newPath + ", text : " + node.getTextTrim());
        	}
        }
        
        // 当前节点下面子节点迭代器  
        Iterator<Element> it = node.elementIterator();  
        // 遍历  
        while (it.hasNext()) {  
            // 获取某个子节点对象  
            Element e = it.next();  
            // 对子节点进行遍历  
            writeNodes(e, newPath, requestParams);  
        }  
    }
	
	private static String getListValues(Element node) {
		
		StringBuffer sb = new StringBuffer();
		Iterator<Element> it = node.elementIterator();  
        // 遍历  
        while (it.hasNext()) {  
            // 获取某个子节点对象  
            Element e = it.next();  
            if (sb.length() == 0) {
            	sb.append(e.getTextTrim());
            } else {
            	sb.append("," + e.getTextTrim());
            }
        }
        
        return sb.toString();
	}
	
	private static void updateListValues(Element node, String values) {
		
		if (StringUtils.isBlank(values)) {
			node.clearContent();
			return;
		}
		node.clearContent();
		for(String v : values.split(",")) {
			if (node.getName().equals("stringList")) {
				Element elm = node.addElement("string");
				elm.setText(v);
			} else if (node.getName().equals("integerList")) {
				Element elm = node.addElement("integer");
				elm.setText(v);
			} else if (node.getName().equals("longList")) {
				Element elm = node.addElement("long");
				elm.setText(v);
			} else {
				
			}
		}
	}
	
	private static void writeJar(String path) throws Exception {
		
		FileOutputStream outputStream = new FileOutputStream(path + "/order.jar");
		JarOutputStream  mOutputJar = new JarOutputStream(new BufferedOutputStream(outputStream));
		FileInputStream inputStream = new FileInputStream(path + "/order.xml"); 
		// create the zip entry 
		JarEntry entry = new JarEntry("order.xml"); 
		mOutputJar.putNextEntry(entry); 

		// read the content of the entry from the input stream, and write it into the archive. 
		int count; 
		while ((count = inputStream.read()) != -1) { 
			mOutputJar.write(count); 
		} 

		// close the entry for this file 
		mOutputJar.closeEntry();
		mOutputJar.close();
		
		// close the file stream used to read the file 
		inputStream.close(); 
		outputStream.close();
	}
	
	private static void getValues(Element node, String path, Map<String, String> returnParams) {
		
        String newPath = path + "";
        if (ignoreNames.containsKey(node.getName())) {
        	if (node.attributeValue("name") != null) {
        		newPath = newPath + "/" + node.attributeValue("name");
        	} else {
//        		System.out.println("Found strange node ====================: " + node.getPath() + ", my path : " + newPath);
        	}
        } else {
        	newPath = newPath + "/" + node.getName();
        }
        
        if (listNames.containsKey(node.getName())) {
//        	System.out.println("List element found : " + node.getName() + ", new path : " + newPath);
        	
        	String strValues = getListValues(node);
        	
        	returnParams.put(newPath, strValues);
        	// 直接返回
        	return;
        }
        
        if (valuesNames.containsKey(node.getName())) {
        	returnParams.put(newPath, node.getTextTrim());
        }
        
        // 当前节点下面子节点迭代器  
        Iterator<Element> it = node.elementIterator();  
        // 遍历  
        while (it.hasNext()) {  
            // 获取某个子节点对象  
            Element e = it.next();  
            // 对子节点进行遍历  
            getValues(e, newPath, returnParams);  
        }
	}
}