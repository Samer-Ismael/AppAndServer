package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class KafkaProducerConfigTest {

    @Test
    public void testSendMessage() {
        // Create a Message object for testing
        Message testMessage = new Message();

        KafkaProducerConfig obj = new KafkaProducerConfig();

        // Wrap the code that might throw an exception in an assertThrows block
        // This ensures that the exception is expected and handled in the test
        assertDoesNotThrow(() -> {
            // Call the sendMessage method and capture its response
            String response = obj.sendMessage(testMessage);

            // Add assertions to verify the response
            // For example, check if the response contains "sent",
            // because I have an if statement that will say sent if the respons code is 200
            assertTrue(response.contains("Sent"));
        });
    }
}