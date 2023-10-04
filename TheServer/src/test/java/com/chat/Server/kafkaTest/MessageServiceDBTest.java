package com.chat.Server.kafkaTest;

import com.chat.Server.kafka.MessageServiceDB;
import com.chat.Server.payload.Message;
import com.chat.Server.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceDBTest {
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
    void getBySenderName() {
        String senderName = "Alice";

        Message expectedMessage = new Message();
        expectedMessage.setId(2L);
        expectedMessage.setBody("Hi there!");
        expectedMessage.setSender(senderName);
        when(messageRepository.findBySender(senderName)).thenReturn((List<Message>) expectedMessage);

        Message retrievedMessage = messageServiceDB.getBySenderName(senderName);

        // Assertions
        assertNotNull(retrievedMessage);
        assertEquals(2L, retrievedMessage.getId());
        assertEquals("Hi there!", retrievedMessage.getBody());
        assertEquals(senderName, retrievedMessage.getSender());
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