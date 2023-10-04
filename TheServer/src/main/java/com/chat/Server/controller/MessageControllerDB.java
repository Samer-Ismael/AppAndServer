package com.chat.Server.controller;

import com.chat.Server.kafka.MessageProducer;
import com.chat.Server.kafka.MessageServiceDB;
import com.chat.Server.payload.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//The MessageControllerDB class is a RESTful API controller for handling chat room messages
//with database storage. It has HTTP endpoints for sending, retrieving messages by sender,
//and retrieving messages by receiver.


@RestController
@RequestMapping("/chat/room")
public class MessageControllerDB {

    //http://localhost:8080/chat/room

    private final MessageServiceDB service;
    private final MessageProducer producer;


    public MessageControllerDB(MessageServiceDB service, MessageProducer producer) {
        this.service = service;
        this.producer = producer;
    }

    //saveMessage receives POST requests to send messages, produces Kafka messages,
    //and saves messages to the database.
    @PostMapping("/send")
    public ResponseEntity<Message> saveMessage(@RequestBody Message message) {
        try {
            producer.sendMessage(message);
            Message savedMessage = service.saveMessage(message);
            return ResponseEntity.ok(savedMessage);
        } catch (Exception e) {
            // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //http://localhost:8080/chat/room/messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessagesBySender(@RequestParam("sender") String senderName) {
        try {
            List<Message> messages = service.getMessagesBySender(senderName);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
