package com.chat.Server.controllerTest;

import com.chat.Server.controller.MessageControllerDB;
import com.chat.Server.kafka.MessageProducer;
import com.chat.Server.kafka.MessageServiceDB;
import com.chat.Server.payload.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageControllerDBTest {

    @Mock
    private MessageServiceDB service;

    @Mock
    private MessageProducer producer;

    @InjectMocks
    private MessageControllerDB messageControllerDB;

    //(service and producer) are mocked using Mockito annotations.
    // The `setUp` method initializes the mocks using `MockitoAnnotations.openMocks(this)`.
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //testSaveMessage will test `saveMessage` method of the controller by mocking the behavior
    // of the `producer` and `service` to simulate successful message sending and saving.
    @Test
    void testSaveMessage() {
        // Arrange
        Message message = new Message();

        when(producer.sendMessage(message)).thenReturn(message);

        when(service.saveMessage(message)).thenReturn(message);

        // Act
        ResponseEntity<Message> response = messageControllerDB.saveMessage(message);

        // Assert
        verify(producer, times(1)).sendMessage(message);
        verify(service, times(1)).saveMessage(message);
        assertSame(HttpStatus.OK, response.getStatusCode());
        assertSame(message, response.getBody());
    }

    //testSaveMessageWithError
    // it will test `saveMessage` method when an error occurs during message sending.

    @Test
    void testSaveMessageWithError() {
        // Arrange
        Message message = new Message();

        when(producer.sendMessage(message)).thenThrow(new RuntimeException("Message sending error"));

        // Act
        ResponseEntity<Message> response = messageControllerDB.saveMessage(message);

        // Assert
        verify(producer, times(1)).sendMessage(message);
        verify(service, never()).saveMessage(message);
        assertSame(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    //testFindBySender
    // it will test `findBySender` method by mocking the behavior of the `service` to return an expected message.

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
}