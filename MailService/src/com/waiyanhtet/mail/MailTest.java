package com.waiyanhtet.mail;

public class MailTest {

	public static void main(String[] args) {
		MailSendingService mailService = new MailSendingService();

		mailService.prepareForSendingMail();
		mailService.sendMailPlainMessage("Greetings", "waiyanucsy1@gmail.com");
		mailService.sendMailEmbedded("Greetings", "waiyanucsy1@gmail.com");
	}
}
