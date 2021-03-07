package ru.dosport.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dosport.services.api.MailService;

/**
 * Реализация сервиса Почты
 */
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    // Необходимые сервисы, мапперы и репозитории и конфиги


    @Override
    public void sendEmail(String email) {
    }
}
