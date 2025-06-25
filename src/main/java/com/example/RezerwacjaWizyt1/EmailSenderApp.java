//package com.example.RezerwacjaWizyt1;
//
//import com.example.RezerwacjaWizyt1.Services.EmailSenderService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class EmailSenderApp implements CommandLineRunner {
//
//    @Autowired
//    private EmailSenderService emailService;
//
//    public static void main(String[] args) {
//        SpringApplication.run(EmailSenderApp.class, args);
//    }
//
//
//    public  void run(String... args) throws Exception {
//        String to = "pawelek1929@wp.pl";
//        String subject = "Test Email from Spring Boot";
//        String body = "This is a test email sent from Spring Boot mail service.";
//
//
//        emailService.SendConfirmationDoctorVisit(to, subject, body);
//        System.out.println("Email sent successfully to: " + to);
//    }
//}
