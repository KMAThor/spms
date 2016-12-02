package nc.ukma.thor.spms.mail;

import nc.ukma.thor.spms.entity.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.stream.Collectors;

public class EmailSender {
	
		private final static String USER_NAME = "nc.ukma.spms@gmail.com";
	    private final static String PASSWORD = "spmspassword1";
	    private static Session session;
	    
	    
	    static {

	        Properties props = new Properties();
	        props.put("mail.transport.protocol", "smtp");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", EmailProperties.GMAIL.getHost());    
	        props.put("mail.smtp.port", EmailProperties.GMAIL.getPort());
	        props.put("mail.smtp.ssl.trust", EmailProperties.GMAIL.getHost());
	        session = Session.getInstance(props, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(USER_NAME, PASSWORD);
	            }
	        });

	    }
	    
	    public static void sendScheduleChangesMassage(List<User> users, String newTime) {
	        List<String> emails = users.stream().map((u) -> u.getEmail()).collect(Collectors.toList());
	        
	        String body = "<p>Meeting schedule changes</p> New time: <b>";
	        body += newTime;
	        body += "</b></p>";   

	        send(emails, "Meeting time has changed", body);
	    }
	    

	    public static void sendMessageUserAddedToProject(List<User> usersToNotify, String teamName) {
	    	
			List<String> userMails = new ArrayList<String>();
			for(int user=0; user<= usersToNotify.size() - 1; user++){
				userMails.add(usersToNotify.get(user).getEmail());
			}
						
			String body = "<p>Your NC students team name is: <b>";
			body += teamName;
			body += "</b></p>"; 
			
			send(userMails, "You Added to the team", body);
		}

    	//if (usersToNotify.getUser() == null) {EmailSender.sendNoUsersMassage("admin@admin.com");}
	    /*
	    public static void sendNoUsersMassage(String adminEmail) {
	    	List<String> userMail = new ArrayList<String>();
	    	userMail.add(adminEmail);
	    	String body = "<p>You have changed meeting schedule, </> ";
			body += "but unfortunately there is no single person in team we can send it to </p>";
	    	send(userMail, "No Users in team", body);
	    }
	    */
	
	    private static void send(List<String> emails, String subject, String body) {
	        try {
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(USER_NAME));
	            for (String email : emails) {
	                message.setRecipients(Message.RecipientType.TO,
	                        InternetAddress.parse(email));
	            }
	            message.setSubject(subject);	            
	            message.setContent(body, "text/html");
	            Transport.send(message);
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	    }
	   
}
