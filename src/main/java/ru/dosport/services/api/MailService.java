package ru.dosport.services.api;

/**
 * Сервис Почты
 */
public interface MailService {

    /**
     * Отправляет почтовое сообщение по адресу почты
     *
     * @param emailTo адрес почты
     * @param subject тема письма
     * @param message тело письма
     */
    void sendEmail(String emailTo, String subject, String message);
}
