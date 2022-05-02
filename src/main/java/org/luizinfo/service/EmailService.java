package org.luizinfo.service;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	private String user = "e-mail do GMAIL"; //Seu e-mail do GMAIL
	private String pass = "Senha do GMAIL";  //Sua Senha do e-mail do GMAIL
	
	public void enviarEmail (String assunto, String emailDestino, String mensagem) throws MessagingException {

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");              //Exige Autenticação
		properties.put("mail.smtp.starttls.enable", "true");   //Tipo de Autenticação
		properties.put("mail.smtp.ssl.trust", "*");            //Certificado de Validade de acesso
		properties.put("mail.smtp.host", "smtp.gmail.com");    //Servidor SMTP
		properties.put("mail.smtp.port", "465");               //Porta SMTP
		properties.put("mail.smtp.socketFactory.port", "465"); //Porta para o Socket
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //Classe de Conexão Socket

		properties.put("mail.smtp.socketFactory.fallback", "false"); 
		properties.put("mail.debug", "true"); 

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, pass);
			}
		});
		session.setDebug(true);
		
		Address[] toUsers = InternetAddress.parse(emailDestino);
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(user));               //Quem está enviando
		message.setRecipients(Message.RecipientType.TO, toUsers); //Para quem vai o email
		message.setSubject(assunto);                              //Assunto do email
		message.setText(mensagem);
		
		Transport.send(message); //Enviando o e-mail
	}

}
