package com.chat.Server.kafka;

import com.chat.Server.payload.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//The `MessageProducer` class is responsible for producing and sending messages
//to a Kafka topic within the application. It uses the KafkaTemplate to send
//messages to the specified Kafka topic.
//This class is annotated with "@Service", indicating that it is a Spring service bean.
@Service
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    public MessageProducer(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //The "sendMessage" method is used to send a message to the Kafka topic "general."
    //Exception handling is implemented to catch and handle any Kafka producer errors,
    //such as network issues.

    public Message sendMessage(Message message) {
        try {
            // Send the message to Kafka topic
            kafkaTemplate.send("general", message);
        } catch (Exception e) {
            // Handle Kafka producer errors (e.g., network issues)
            e.printStackTrace();
        }

        return message;
    }


}
