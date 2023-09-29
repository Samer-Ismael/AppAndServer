package com.chat.Server.kafka;

import com.chat.Server.payload.Message;
import com.chat.Server.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
//The `MessageServiceDB` class is a service component responsible for managing
//message data in the application. It interacts with the database through
//the `MessageRepository` to perform operations such as saving, getting, and deleting messages.
@Service
public class MessageServiceDB {

    private final KafkaTemplate<String, Message> kafkaTemplate;
    public final MessageRepository repository;

    //It includes a constructor that injects a KafkaTemplate for message publishing and a
    //MessageRepository for database access.
    @Autowired
    public MessageServiceDB(KafkaTemplate<String, Message> kafkaTemplate, KafkaTemplate<String, Message> kafkaTemplate1, MessageRepository repository) {
        this.kafkaTemplate = kafkaTemplate1;
        this.repository = repository;
    }


    public Message saveMessage(Message message) {
        try {
            // Save the message to the database
            return repository.save(message);
        } catch (Exception e) {
            // Handle database errors
            e.printStackTrace();
            return null;
        }
    }

    public Message getBySenderName(String sender) {
        try {
            return repository.findBySender(sender);
        } catch (Exception e) {
            // Handle database errors
            e.printStackTrace();
            return null;
        }
    }

    public Message getByReceiverName(String receiver) {
        try {
            return repository.findBySender(receiver);
        } catch (Exception e) {
            // Handle database errors
            e.printStackTrace();
            return null;
        }
    }
    public void deleteAllMessages() {
        try {
            repository.deleteAll();
        } catch (Exception e) {
            // Handle database errors
            e.printStackTrace();
        }
    }

}
