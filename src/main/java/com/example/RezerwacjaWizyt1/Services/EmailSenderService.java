package com.example.RezerwacjaWizyt1.Services;


import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends a confirmation email for a doctor's visit with HTML content.
     * Includes exception handling to catch and log errors during email sending.
     *
     * @param targetMail    The recipient's email address.
     * @param subject       The subject of the email.
     * @param htmlContent   The HTML body content of the email.
     * @throws MailException If an error occurs during the email sending process.
     */
    public void SendConfirmationDoctorVisit(String targetMail, String subject, String htmlContent) throws MailException {
        // MimeMessage MUST be created inside the method, after mailSender is initialized
        MimeMessage mimeMessage = mailSender.createMimeMessage(); // CORRECT: Moved inside the method

        try {
            // 'true' enables multipart for HTML and 'UTF-8' sets the character encoding
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom("pawelek192928@gmail.com"); // Your sender email address
            helper.setTo(targetMail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // Set the text as HTML (second parameter 'true')

            mailSender.send(mimeMessage);
            logger.info("HTML email sent successfully to: {}", targetMail);

        } catch (jakarta.mail.MessagingException e) { // Catch jakarta.mail.MessagingException for MimeMessageHelper issues
            logger.error("Failed to send HTML email to {}. Subject: {}. Error: {}", targetMail, subject, e.getMessage(), e);
            // Wrap MessagingException in Spring's MailException for consistent error handling
            throw new MailException("Failed to send HTML email", e) {};
        } catch (MailException e) { // Catch Spring's MailException for general mail sender issues
            logger.error("Failed to send HTML email to {}. Subject: {}. Error: {}", targetMail, subject, e.getMessage(), e);
            throw e; // Re-throw the original MailException
        }
    }
}
