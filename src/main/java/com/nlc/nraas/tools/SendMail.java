package com.nlc.nraas.tools;
/*import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;*/

public class SendMail {

//	private static final String from="1127835288@qq.com";
//	private static final String  qq="1127835288";
//	private static final int port=587;
//	private static final String pwd="";
//	private static final String  smtp="smtp.qq.com";
//	private static final String mimeType="text/html;charset=gb2312";
//	public static void sendmail(String title,String[] to,String text,String[] filenames) throws Exception {  
//        // 可以从配置文件读取相应的参数  
//        Properties props = new Properties();  
//        Session session; // 邮件会话对象  
//        //javax.mail.internet.MimeMessage message; // MIME邮件对象 
//        props = System.getProperties(); // 获得系统属性对象  
//        props.put("mail.smtp.host", smtp); // 设置SMTP主机  
//        props.put("mail.smtp.auth", "true");// 是否到服务器用户名和密码验证  
//        props.put("mail.smtp.port", String.valueOf(port));
//         
//        // 设置邮件会话  
//        session=Session.getInstance(props,new Authenticator() {
//        	@Override
//        	protected PasswordAuthentication getPasswordAuthentication() {
//        		return new PasswordAuthentication(from, "knowqabwcuwujbad");
//        	}
//		});
//        // 设置传输协议  
//        Transport transport = session.getTransport("smtp");  
//        transport.connect(smtp, qq, pwd);//举个例子如果用qq邮箱这里面放置qq号和qq密码
//        // 设置from、to等信息  
//        Message message = new MimeMessage(session);//  
// 
//        if (from != null && from.length() > 0) {  
//            InternetAddress sentFrom = new InternetAddress(from);  
// 
//            message.setFrom(sentFrom); // 设置发送人地址  
//        }  
//        InternetAddress[] sendTo = new InternetAddress[to.length];  
//        for (int i = 0; i < to.length; i++) {  
//            sendTo[i] = new InternetAddress(to[i]);  
//        }  
//        message.setRecipients(MimeMessage.RecipientType.TO,sendTo);  
//        message.setSubject(title);
//        MimeBodyPart messageBodyPart1 = new MimeBodyPart();  
//        messageBodyPart1.setContent(text, mimeType);  
//        Multipart multipart = new MimeMultipart();// 附件传输格式  
//        multipart.addBodyPart(messageBodyPart1);  
//        for (int i = 0; i < filenames.length; i++) {  
//            MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
//            // 选择出每一个附件名  
//            String filename = filenames[i].split(",")[0];  
//            String displayname = filenames[i].split(",")[0];  
//            // 得到数据源  
//            FileDataSource fds = new FileDataSource(filename);  
//            // 得到附件本身并至入BodyPart  
//            messageBodyPart2.setDataHandler(new DataHandler(fds));  
//            // 得到文件名同样至入BodyPart  
//            messageBodyPart2.setFileName(displayname);  
//            messageBodyPart2.setFileName(fds.getName());  
//            messageBodyPart2.setFileName(MimeUtility.encodeText(displayname));  
//            multipart.addBodyPart(messageBodyPart2);  
//        }  
//        message.setContent(multipart);  
//        // 设置信件头的发送日期  
//        message.setSentDate(new Date());  
//        message.saveChanges();  
//        // 发送邮件  
//        Transport.send(message);
//        transport.close();  
//    }
//	public static long useTime(String title,String[] to,String text,String[] filenames){
//        long time=0;
//		try {
//        	long start=System.currentTimeMillis();
//			SendMail.sendmail(title,to, text,filenames);
//			time=(System.currentTimeMillis()-start)/1000;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//       return time; 
//	}
}
