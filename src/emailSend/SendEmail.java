package emailSend;

/**
 *
 * @author kreshan
 */

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.activation.*;
import com.sun.mail.util.MailSSLSocketFactory;

public class SendEmail {

	public static void main(String arg[]) {
		String returnMsg;
		try {

			String host= "96.125.180.6";  //mail.epiclanka.net
			String user= "kreshan_r@epiclanka.net";
			String password= "";
			String from= "kreshan_r@epiclanka.net";
			int port = 25;
			String to = "kreshan882@gmail.com";
                        String messageText = "headding...";
                        boolean sessionDebug = true;
                        
                        
	        Properties props = System.getProperties();	        
                    props.put("mail.host", host);
                    props.put("mail.transport.protocol.", "smtp");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
	        
                    MailSSLSocketFactory sf = new MailSSLSocketFactory();
                    sf.setTrustAllHosts(true);
                    props.put("mail.smtp.ssl.socketFactory", sf);
	        
	        Session mailSession = Session.getDefaultInstance(props, null);
	        mailSession.setDebug(sessionDebug);
	        Message msg = new MimeMessage(mailSession);
	        msg.setFrom(new InternetAddress(from));
	        InternetAddress[] address = {new InternetAddress(to)};
	        msg.setRecipients(Message.RecipientType.TO, address);
	        msg.setSubject("boddy message");
	       // msg.setContent(messageText, "text/html"); // use setText if you want to send text
	        
                //add 2 parts
                MimeMultipart multipart = new MimeMultipart("related");
                    // 1: the html
                    BodyPart messageBodyPart = new MimeBodyPart();
                    String htmlText = messageText;
                    messageBodyPart.setContent(htmlText, "text/html");
                    multipart.addBodyPart(messageBodyPart);

                    // 2: image 
                    messageBodyPart = new MimeBodyPart();
                    DataSource fds = new FileDataSource("C:\\Users\\kreshan\\Desktop\\444.jpg");
                    messageBodyPart.setDataHandler(new DataHandler(fds));
                    messageBodyPart.setHeader("Content-ID", "<image>");
                    multipart.addBodyPart(messageBodyPart);
        
                msg.setContent(multipart);

	        
	        Transport transport = mailSession.getTransport("smtp");
                transport.connect(host, port, user, password);
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close();

		
            } catch (Exception e) {
		e.printStackTrace();
            } 
	}
}
