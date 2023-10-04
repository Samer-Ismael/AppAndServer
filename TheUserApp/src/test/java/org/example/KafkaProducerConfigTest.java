package org.example;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.OutputStream;
import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
            // For example, check if the response contains a success message
            assertTrue(response.contains("Sent"));
        });
    }
}