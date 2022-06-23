package com.waiyanhtet.mail;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MailSendingService {

	private static Logger debugLog = Logger.getLogger("debug.logger");
	private static Logger errorLog = Logger.getLogger("error.logger");

	private static Transport myTransport = null;
	private static Message msg = null;
	private static String from = "waiyanhtet.cs345@gmail.com";
	private static String codeWord = "udmckiwgcmowuguv";
	Session session;
	Session session2;

	public void prepareForSendingMail() {

		PropertyConfigurator.configure("./Log4j.properties");
		debugLog.debug("Prepare to send email........");

		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", true);
			props.put("mail.smtp.starttls.enable", true);
			props.put("mail.smtp.ssl.protocols", "TLSv1.2");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

			session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, codeWord);
				}
			});

			session.setDebug(false);
			msg = new MimeMessage(session2);
			msg.setFrom(new InternetAddress(from));

			debugLog.debug("From Mail Address : " + from);

		} catch (MessagingException e) {
			errorLog.error(e.getMessage());
		} catch (Exception e) {
			errorLog.error(e.getMessage());
		}
	}

	public void sendMailPlainMessage(String mailSubject, String to) {

		debugLog.debug("To Mail Address : " + to);
		String finalMessage = """
				Hello Dear!
					I'm testing email sending api with Java programming language.This is program generated email so no need to reply.


				Thank You.
				""";

		try {

			if (msg != null) {

				msg.setSubject(mailSubject);
				msg.setContent(finalMessage, "text/plain");

				if (myTransport == null) {
					myTransport = session.getTransport("smtp");
				}

				if (!myTransport.isConnected()) {
					myTransport.connect();
				}

				msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to, true));

				myTransport.sendMessage(msg, msg.getAllRecipients());

				debugLog.debug("Successful to send email from %s to %s".formatted(from, to));
			}
		} catch (MessagingException e) {
			errorLog.error("Fail to send email because " + e.getMessage());
		} catch (Exception e) {
			errorLog.error("Fail to send email because " + e.getMessage());
		} finally {
			closeConnection();
		}
	}

	public void sendMailEmbedded(String mailSubject, String to) {

		debugLog.debug("To Mail Address : " + to);

		try {

			if (msg != null) {

				msg.setSubject(mailSubject);

				MimeMultipart mulPart = new MimeMultipart();

				String htmlText = """
						<h1>Ben White</h1>
						<p>Benjamin William White is an English professional footballer who plays as a centre-back for Premier League club Arsenal and the England national team. White began his senior club career at age 18 in 2016 with Brighton & Hove Albion</p>
						""";

				// HTML text part
				BodyPart bodyPart = new MimeBodyPart();
				bodyPart.setContent(htmlText, "text/html");
				mulPart.addBodyPart(bodyPart);

				// Image part
				bodyPart = new MimeBodyPart();
				DataSource fds = new FileDataSource("C:\\Users\\waiyanhtet\\Pictures\\download1.png");
				bodyPart.setDataHandler(new DataHandler(fds));
				bodyPart.setHeader("Content-ID", "<image>");
				bodyPart.setFileName("benwhite.png");
				mulPart.addBodyPart(bodyPart);

				// PDF part
				bodyPart = new MimeBodyPart();
				fds = new FileDataSource("C:\\Users\\waiyanhtet\\Pictures\\benwhite.pdf");
				bodyPart.setDataHandler(new DataHandler(fds));
				bodyPart.setFileName("benwhite.pdf");
				mulPart.addBodyPart(bodyPart);

				msg.setContent(mulPart);

				if (myTransport == null) {
					myTransport = session.getTransport("smtp");
				}

				if (!myTransport.isConnected()) {
					myTransport.connect();
				}

				msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to, true));

				myTransport.sendMessage(msg, msg.getAllRecipients());

				debugLog.debug("Successful to send email from %s to %s".formatted(from, to));
			}
		} catch (MessagingException e) {
			errorLog.error("Fail to send email because " + e.getMessage());
		} catch (Exception e) {
			errorLog.error("Fail to send email because " + e.getMessage());
		} finally {
			closeConnection();
		}
	}

	public void closeConnection() {

		try {
			if (myTransport.isConnected()) {
				myTransport.close();
				msg = null;
				session = null;
			}
		} catch (MessagingException e) {
			errorLog.error("Can't close the transport because " + e.getMessage() + "\n");
		}
	}
}
