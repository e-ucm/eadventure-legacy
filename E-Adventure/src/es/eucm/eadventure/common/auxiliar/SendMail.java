package es.eucm.eadventure.common.auxiliar;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.swing.JOptionPane;

import es.eucm.eadventure.common.gui.TextConstants;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

/**
 * Class used to send an email.<p>
 * 
 * This class allows the sending of messages with a smtp server. It is
 * prepared for authentication, SSL stacks, different ports, etc.
 * 
 * If problem (javax.mail.AuthenticationFailedException) connecting to gmail visit
 * http://mail.google.com/support/bin/answer.py?answer=14257
 * 
 * 
 * @author Eugenio Marchiori
 *
 */
public class SendMail {

	/**
	 * The default hostname
	 */
	private static final String SMTP_HOST_NAME = "smtp.gmail.com";//;"72.14.221.109"
	
	/**
	 * The default user
	 */
	private static final String SMTP_AUTH_USER = "emarchiori@gmail.com";
	
	/**
	 * The default password
	 */
	private static final String SMTP_AUTH_PWD = "pwd";
	
	/**
	 * The default "from" address
	 */
	private static final String DEFAULT_FROM = "emarchiori@gmail.com";
	
	/**
	 * The address where to send error reports
	 */
	private static final String ERROR_TO = "emarchiori@gmail.com";
	
	/**
	 * Default port for the SMTP server
	 */
	private static final int DEFAULT_PORT = 465;//587;25;465
	
	/**
	 * Default value for the requiresSSL attribute
	 */
	private static final boolean DEFAULT_SSL = true;
	
	/**
	 * The name of the SMTP host
	 */
	private String smtpHostName;
	
	/**
	 * The user for authentication in the STMP host
	 */
	private String smtpAuthUser;
	
	/**
	 * The password for the user in the STMP host
	 */
	private String smtpAuthPwd;

	/**
	 * Indicates whether the server needs SSL data transfers
	 */
	private boolean requiersSSL;
	
	/**
	 * The port through which to connect to the host
	 */
	private int port;

	private boolean debug = false;
	
	/**
	 * Default constructor, uses the default configuration
	 */
	public SendMail() {
		smtpHostName = SMTP_HOST_NAME;
		smtpAuthUser = SMTP_AUTH_USER;
		smtpAuthPwd = SMTP_AUTH_PWD;
		requiersSSL = DEFAULT_SSL;
		port = DEFAULT_PORT;
	}

	/**
	 * Constructor with a new host name. Doesn't use authentication,
	 * SSL and tries to connect though the protocols default port.
	 * 
	 * @param smtpHostName The SMTP host name
	 */
	public SendMail(String smtpHostName) {
		this.smtpHostName = smtpHostName;
		requiersSSL = false;
	}

	/**
	 * Constructor with a host name, user and password. The port
	 * and the value of requiersSSL must be set if other than the default
	 * values of the protocol must be used.
	 * 
	 * @param smtpHostName The SMTP host name
	 * @param smtpAuthUser The user in the SMTP host
	 * @param smtpAuthPwd The password for the user in the SMTP host
	 */
	public SendMail(String smtpHostName, String smtpAuthUser, String smtpAuthPwd) {
		this.smtpHostName = smtpHostName;
		this.smtpAuthUser = smtpAuthUser;
		this.smtpAuthPwd = smtpAuthPwd;
		requiersSSL = false;
	}
	
	/**
	 * Create a session with the configuration set for the class.
	 * 
	 * @return A new session to send an email
	 */
	private Session getSession() {
		Session session;

		// Set the host smtp address
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", smtpHostName);
		
		if (debug)
			props.setProperty("mail.debug", "true");

		if (port != 0)
			props.put("mail.smtp.port", "" + port);
		props.setProperty("mail.smtp.quitwait", "false");

		if (smtpAuthUser != null) {
			props.put("mail.smtp.auth", "true");

			if (requiersSSL) {
				Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
				if (port != 0)
					props.put("mail.smtp.socketFactory.port", "" + port);
				props.put("mail.smtp.socketFactory.class",
						"javax.net.ssl.SSLSocketFactory");
			}

			Authenticator auth = new SMTPAuthenticator();
			props.put("mail.smtp.socketFactory.fallback", "false");
			session = Session.getDefaultInstance(props, auth);
		} else {
			props.put("mail.smtp.socketFactory.fallback", "false");
			session = Session.getDefaultInstance(props);
		}
		return session;
	}

	/**
	 * Post a simple mail with no attachements.
	 * 
	 * @param recipients A array of e-mail addresses for the recipients
	 * @param subject The subject of the e-mail
	 * @param message The message to be included in the e-mail
	 * @param from The from address for the e-mail (if null, the default is used)
	 */
	public void postMail(String recipients[], String subject, String message, String from) {
		try {
			Session session = getSession();
			if (from == null)
				from = DEFAULT_FROM;
			
			// create a message
			Message msg = new MimeMessage(session);

			// set the from and to address
			InternetAddress addressFrom = new InternetAddress(from);
			msg.setFrom(addressFrom);

			InternetAddress[] addressTo = new InternetAddress[recipients.length];
			for (int i = 0; i < recipients.length; i++) {
				addressTo[i] = new InternetAddress(recipients[i]);
			}
			msg.setRecipients(Message.RecipientType.TO, addressTo);

			// Setting the Subject and Content Type
			msg.setSubject(subject);
			msg.setContent(message, "text/plain");
			
			msg.setSentDate(new Date());
			
			Transport.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Post a e-mail with text and one file attachement
	 * 
	 * @param recipients An array with the recipients of the e-mail
	 * @param subject The subject of the e-mail
	 * @param message The text message of the e-mail
	 * @param from The from address of the e-mail (if null, the default value is used)
	 * @param filename The full path and filename of the attachement
	 */
	public void postMailAttachements(String recipients[], String subject, String message, String from, String filename) {
		try {
			Session session = getSession();
			if (from == null)
				from = DEFAULT_FROM;
			
			// create a message
			Message msg = new MimeMessage(session);
			InternetAddress addressFrom = new InternetAddress(from);
			msg.setFrom(addressFrom);
			InternetAddress[] addressTo = new InternetAddress[recipients.length];
			for (int i = 0; i < recipients.length; i++) {
				addressTo[i] = new InternetAddress(recipients[i]);
				//addressTo[i] = new InternetAddress("jtorrente84@gmail.com");
			}
			msg.setRecipients(Message.RecipientType.TO, addressTo);
			msg.setSubject(subject);

			
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(message);
			
			MimeBodyPart mbp2 = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(filename);
			mbp2.setDataHandler(new DataHandler(fds));
			mbp2.setFileName(fds.getName());
			
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(mbp2);
			
			msg.setContent(mp);
			
			msg.setSentDate(new Date());
			
			Transport.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, TextConstants.getText("SendMail.UnableToSendEmail"), TextConstants.getText("SendMail.ErrorSendingEmail"), JOptionPane.ERROR_MESSAGE);
		}
		
	}

	/**
	 * An Authenticator, used to provide the user and password.
	 */
	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = smtpAuthUser;
			String password = smtpAuthPwd;
			return new PasswordAuthentication(username, password);
		}
	}

	/**
	 * Test main.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		SendMail test = new SendMail();
		test.setDebug(true);
		String[] recp = new String[1];
		recp[0] = SendMail.DEFAULT_FROM;
		test.postMail(recp, "SendMail test", "SendMail test", DEFAULT_FROM);
	}
	
	/**
	 * Set the value of requiersSSL
	 * @param requiersSSL 
	 */
	public void setRequiersSSL(boolean requiersSSL) {
		this.requiersSSL = requiersSSL;
	}

	/**
	 * Set the value of port
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Post an error report
	 * 
	 * @param mail The contents of the error report
	 */
	public void postErrorReport(String mail) {		
		String[] recp = new String[1];
		recp[0] = ERROR_TO;
		postMail(recp, "Error Report", mail, DEFAULT_FROM);
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}
