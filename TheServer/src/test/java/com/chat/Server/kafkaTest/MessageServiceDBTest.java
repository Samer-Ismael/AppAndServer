package com.chat.Server.kafkaTest;

import com.chat.Server.controller.MessageControllerDB;
import com.chat.Server.kafka.MessageServiceDB;
import com.chat.Server.payload.Message;
import com.chat.Server.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceDBTest {
    private final MessageServiceDB service = mock(MessageServiceDB.class);
    private final MessageControllerDB messageControllerDB = new MessageControllerDB(service, null);

    @Mock
    private KafkaTemplate<String, Message> kafkaTemplate;
    @Mock
    private MessageRepository messageRepository;
    @InjectMocks
    private MessageServiceDB messageServiceDB;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    //saveMessage tests the `saveMessage` method by mocking the behavior of the `kafkaTemplate`
    //for message sending and the `messageRepository` for message saving.
    @Test
    void saveMessage() {
        Message message = new Message();
        message.setId(1L);
        message.setBody("Hello, World!");
        message.setSender("John");

        when(kafkaTemplate.send(eq("general"), eq(message))).thenReturn(null);

        when(messageRepository.save(message)).thenReturn(message);

        Message savedMessage = messageServiceDB.saveMessage(message);

        // Assertions
        assertNotNull(savedMessage);
        assertEquals(1L, savedMessage.getId());
        assertEquals("Hello, World!", savedMessage.getBody());
        assertEquals("John", savedMessage.getSender());
    }


    //getBySenderName tests the `getBySenderName` method by mocking the behavior of the `messageRepository`
    //for retrieving a message by sender name.
    @Test
    void testGetMessagesBySender() {
        // Arrange
        String senderName = "testSender";
        List<Message> expectedMessages = new ArrayList<>(); // Create a list of messages here

        when(service.getMessagesBySender(senderName)).thenReturn(expectedMessages);

        // Act
        ResponseEntity<List<Message>> response = messageControllerDB.getMessagesBySender(senderName);

        // Assert
        verify(service, times(1)).getMessagesBySender(senderName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMessages, response.getBody());
    }
    @Test
    void testGetMessagesBySenderWithError() {
        // Arrange
        String senderName = "testSender";

        when(service.getMessagesBySender(senderName)).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<List<Message>> response = messageControllerDB.getMessagesBySender(senderName);

        // Assert
        verify(service, times(1)).getMessagesBySender(senderName);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }


    //deleteAllMessages tests the `deleteAllMessages` method by mocking the behavior of the `messageRepository`
    //for deleting all messages.
    @Test
    void deleteAllMessages() {
        doNothing().when(messageRepository).deleteAll();

        messageServiceDB.deleteAllMessages();

        verify(messageRepository, times(1)).deleteAll();
    }
}