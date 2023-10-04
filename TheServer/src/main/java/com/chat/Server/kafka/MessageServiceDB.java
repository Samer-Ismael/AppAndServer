package com.chat.Server.kafka;

import com.chat.Server.payload.Message;
import com.chat.Server.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Message> getMessagesBySender(String senderName) {
        try {
            List<Message> messages = repository.findBySender(senderName);
            return messages;
        } catch (Exception e) {
            // Log or print the exception for debugging
            e.printStackTrace();
            throw e; // Rethrow the exception if necessary
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
