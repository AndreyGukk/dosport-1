package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.dosport.services.api.MailService;

/**
 * Реализация сервиса Почты
 */
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    // Необходимые сервисы, мапперы и репозитории и конфиги
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String userName;

    @Override
    public void sendEmail(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(userName);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
