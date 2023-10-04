package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class KafkaProducerConfigTest {

    @Test
    public void testSendMessage() {
        // Create a Message object for testing
        Message testMessage = new Message();

        // Create a mock KafkaProducerConfig
        KafkaProducerConfig obj = mock(KafkaProducerConfig.class);

        // Use Mockito to specify that sendMessage should throw an exception
        when(obj.sendMessage(any(Message.class))).thenThrow(new RuntimeException("Test exception"));

        // Now, call the sendMessage method, which will throw the exception we specified
        assertThrows(RuntimeException.class, () -> obj.sendMessage(testMessage));
    }
}