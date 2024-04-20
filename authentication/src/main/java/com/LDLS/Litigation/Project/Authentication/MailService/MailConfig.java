package com.LDLS.Litigation.Project.Authentication.MailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("mail.emtechhouse.co.ke");
        mailSender.setPort(465);
        mailSender.setUsername("no-reply@emtechhouse.co.ke");
        mailSender.setPassword("Pass123");

        return mailSender;
    }
}
