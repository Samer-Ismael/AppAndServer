package com.chat.Server.kafka;

import com.chat.Server.payload.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//The `MessageConsumer` class is responsible for consuming and processing messages
//received from a Kafka topic. It is annotated with `@Service`, indicating that it is a
//Spring service bean.
@Service
public class MessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);


    //receiveMessage method, the received message is processed, and it is logged
    //to the console. Exception handling is implemented to catch and show any errors
    @KafkaListener(topics = "general", groupId = "general-group")
    public void receiveMessage(Message message) {
        try {
            // Process the received message
            System.out.println(message.getSender() + ": " + message.getBody());
        } catch (Exception e) {
            // Handle errors that occur during message processing
            LOGGER.error("Error processing Kafka message: " + e.getMessage(), e);
        }
    }

}
