package com.example.weathermonitoring.alerts;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class EmailNotification implements AlertNotification {

    @Value("${email.host}")
    private String host;

    @Value("${email.port}")
    private String port;

    @Value("${email.username}")
    private String username;

    @Value("${email.password}")
    private String password;

    private final ExecutorService executorService;

    public EmailNotification(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void sendAlert(String message) {
        executorService.submit(() -> sendEmail(message));
    }

    private void sendEmail(String message) {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(username));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse("vivektiwaricod@gmail.com")); // Sending to self for demo
            emailMessage.setSubject("Weather Alert");
            emailMessage.setText(message);


            Transport.send(emailMessage);
            System.out.println("Email sent successfully: " + message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
