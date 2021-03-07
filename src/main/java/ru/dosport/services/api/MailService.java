package ru.dosport.services.api;

/**
 * Сервис Почты
 */
public interface MailService {

    /**
     * Отправляет почтовое сообщение по адресу почты
     *
     * @param email адрес почты
     */
    void sendEmail(String email);
}
