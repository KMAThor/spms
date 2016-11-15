package nc.ukma.thor.spms.mail;

import nc.ukma.thor.spms.entity.User;

//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;

import javax.mail.*;
import java.util.*;

public class EmailSender {
	
		private final static String USER_NAME = "nc_ukma_spms@gmail.com";
	    private final static String PASSWORD = "spmspassword1";
	    public final static String GENERIC_TEMPLATE = "/mailTemplate.ftl";
	    private static Session session;
	    
	    
	    static {

	        Properties props = new Properties();
	        props.put("mail.transport.protocol", "smtp");
	        props.put("mail.smtp.auth", "true");//If true, attempt to authenticate the user using the AUTH command. Defaults to false.
	        props.put("mail.smtp.starttls.enable", "true"); //If true, enables the use of the STARTTLS command  to switch the connection to a TLS-protected connection before issuing any login commands.
	        props.put("mail.smtp.host", EmailProperties.GMIAL.getHost());     //The SMTP server to connect to.
	        props.put("mail.smtp.port", EmailProperties.GMIAL.getPort());//The SMTP server port to connect to
	        props.put("mail.smtp.ssl.trust", EmailProperties.GMIAL.getHost());
	        //If set, and a socket factory hasn't been specified, enables use of a MailSSLSocketFactory. If set to "*", all hosts are trusted.
	        // If set to a whitespace separated list of hosts, those hosts are trusted.
	        // Otherwise, trust depends on the certificate the server presents.

	        session = Session.getInstance(props, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(USER_NAME, PASSWORD);
	            }
	        });

	    }
	    
	    public static void sendSchedulrChangesMassage(List<User> users, String newTime, String fullPath) {

	        ArrayList<String> emails = new ArrayList<String>(users.size());
	        for (User user : users) {
	            emails.add(user.getEmail());
	        }

	        StringBuilder body = new StringBuilder();
	        body.append("<p>Meeting schedule changes</p> <p style=\"text-transform:none;\">TaskName: <b>");
	        body.append(newTime);
	        body.append("</b></p>");

	      //  send(emails, body.toString(), getTemplate(GENERIC_TEMPLATE, fullPath));
	    }
	    
	    public static void sendEnvolvedMassage() {
	       
	    }
	    
	    public static void sendStatusChangesMassage() {
		       
	    }

	    public static void sendMeetingReminderMassage() {
		       
	    }
	    
	    
	    private static void send() {
	      
	    }
	    
	    //Template
	    
}
