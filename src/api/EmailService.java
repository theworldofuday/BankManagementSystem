package api;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {
    
    // ⚠️ IMPORTANT: Replace with your Gmail credentials
    // Use App Password, not your regular password
    // Get App Password:  https://myaccount.google.com/apppasswords
    
    private static final String SENDER_EMAIL = "your. email@gmail.com";      // Change this
    private static final String SENDER_PASSWORD = "xxxx xxxx xxxx xxxx";    // 16-char app password
    private static final String BANK_NAME = "Secure Bank";
    
    private static boolean emailEnabled = true;
    
    // Configure email properties
    private static Properties getMailProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls. enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail. smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp. ssl.trust", "smtp.gmail.com");
        return props;
    }
    
    // Send plain text email
    public static boolean sendEmail(String toEmail, String subject, String body) {
        if (!emailEnabled) {
            System.out.println("📧 Email disabled. Would send to: " + toEmail);
            return true;
        }
        
        try {
            Properties props = getMailProperties();
            
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
                }
            });
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL, BANK_NAME));
            message.setRecipients(Message. RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
            
            Transport. send(message);
            System. out.println("✓ Email sent successfully to " + toEmail);
            return true;
            
        } catch (Exception e) {
            System. out.println("⚠ Failed to send email: " + e. getMessage());
            return false;
        }
    }
    
    // Send HTML formatted email (looks better)
    public static boolean sendHtmlEmail(String toEmail, String subject, String htmlBody) {
        if (!emailEnabled) {
            System.out.println("📧 Email disabled. Would send to: " + toEmail);
            return true;
        }
        
        try {
            Properties props = getMailProperties();
            
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
                }
            });
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL, BANK_NAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(htmlBody, "text/html; charset=utf-8");
            
            Transport.send(message);
            System.out.println("✓ Email sent successfully to " + toEmail);
            return true;
            
        } catch (Exception e) {
            System.out.println("⚠ Failed to send email: " + e.getMessage());
            return false;
        }
    }
    
    // Send email with attachment (for statements)
    public static boolean sendEmailWithAttachment(String toEmail, String subject, 
                                                   String body, String attachmentPath) {
        if (!emailEnabled) {
            System.out.println("📧 Email disabled. Would send to: " + toEmail);
            return true;
        }
        
        try {
            Properties props = getMailProperties();
            
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
                }
            });
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL, BANK_NAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            
            // Create multipart message
            Multipart multipart = new MimeMultipart();
            
            // Text part
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(body);
            multipart.addBodyPart(textPart);
            
            // Attachment part
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new java.io.File(attachmentPath));
            multipart.addBodyPart(attachmentPart);
            
            message.setContent(multipart);
            
            Transport.send(message);
            System.out. println("✓ Email with attachment sent to " + toEmail);
            return true;
            
        } catch (Exception e) {
            System.out.println("⚠ Failed to send email: " + e.getMessage());
            return false;
        }
    }
    
    // Send email asynchronously (non-blocking)
    public static void sendEmailAsync(String toEmail, String subject, String body) {
        new Thread(() -> {
            sendEmail(toEmail, subject, body);
        }).start();
    }
    
    // Enable/disable email service
    public static void setEmailEnabled(boolean enabled) {
        emailEnabled = enabled;
        System.out.println("📧 Email service " + (enabled ? "enabled" : "disabled"));
    }
    
    public static boolean isEmailEnabled() {
        return emailEnabled;
    }
}
