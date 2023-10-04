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

    //testFindByReceiver tests the `findByReceiver` method by mocking the behavior of the `service`
    // to return an expected message.
    @Test
    void testFindByReceiver() {
        // Arrange
        String receiver = "testReceiver";
        Message expectedMessage = new Message();

        when(service.getByReceiverName(receiver)).thenReturn(expectedMessage);

        // Act
        ResponseEntity<Message> response = messageControllerDB.findByReceiver(receiver);

        // Assert
        verify(service, times(1)).getByReceiverName(receiver);
        assertSame(HttpStatus.OK, response.getStatusCode());
        assertSame(expectedMessage, response.getBody());
    }

    //testFindByReceiverNotFound
    // it will test `findByReceiver` method when the requested receiver is not found.

    @Test
    void testFindByReceiverNotFound() {
        // Arrange
        String receiver = "nonExistentReceiver";

        when(service.getByReceiverName(receiver)).thenReturn(null);

        // Act
        ResponseEntity<Message> response = messageControllerDB.findByReceiver(receiver);

        // Assert
        verify(service, times(1)).getByReceiverName(receiver);
        assertSame(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    //testFindByReceiverWithError
    // it will test `findByReceiver` method when an error occurs during the database operation.

    @Test
    void testFindByReceiverWithError() {
        // Arrange
        String receiver = "testReceiver";

        when(service.getByReceiverName(receiver)).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<Message> response = messageControllerDB.findByReceiver(receiver);

        // Assert
        verify(service, times(1)).getByReceiverName(receiver);
        assertSame(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    //testFindBySender
    // it will test `findBySender` method by mocking the behavior of the `service` to return an expected message.

    @Test
    void testFindBySender() {
        // Arrange
        String sender = "testSender";
        Message expectedMessage = new Message();

        when(service.getBySenderName(sender)).thenReturn(expectedMessage);

        // Act
        ResponseEntity<List<Message>> response = messageControllerDB.findBySender(sender);

        // Assert
        verify(service, times(1)).getBySenderName(sender);
        assertSame(HttpStatus.OK, response.getStatusCode());
        assertSame(expectedMessage, response.getBody());
    }

    //testFindBySenderNotFound
    // it will test `findBySender` method when the requested sender is not found.
    @Test
    void testFindBySenderNotFound() {
        // Arrange
        String sender = "nonExistentSender";

        when(service.getBySenderName(sender)).thenReturn(null);

        // Act
        ResponseEntity<List<Message>> response = messageControllerDB.findBySender(sender);

        // Assert
        verify(service, times(1)).getBySenderName(sender);
        assertSame(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    //testFindBySenderWithError
    // it will test the `findBySender` method when an error occurs during the database operation.
    @Test
    void testFindBySenderWithError() {
        // Arrange
        String sender = "testSender";

        when(service.getBySenderName(sender)).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<List<Message>> response = messageControllerDB.findBySender(sender);

        // Assert
        verify(service, times(1)).getBySenderName(sender);
        assertSame(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}