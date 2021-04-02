package ru.dosport.chat.entities;

import lombok.Data;
import ru.dosport.chat.enums.MessageType;

/**
 * Сообщение чата
 */
@Data
public class ChatMessage {

    private MessageType type;
    private String content;
    private String sender;
}
