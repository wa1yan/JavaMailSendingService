package com.waiyanhtet.mail;

public class MailTest {

	public static void main(String[] args) {
		MailSendingService mailService = new MailSendingService();

		mailService.prepareForSendingMail();
		mailService.sendMailPlainMessage("Greetings", "receiver@gmail.com");
		mailService.sendMailEmbedded("Greetings", "receiver@gmail.com");
	}
}
