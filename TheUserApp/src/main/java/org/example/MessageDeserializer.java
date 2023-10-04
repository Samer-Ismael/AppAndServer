package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;


public class MessageDeserializer implements Deserializer<Message> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    //method reads the binary data, attempts to parse it as JSON, and then
    //converts it into a 'Message' object. If the deserialization fails for any reason, such as
    //invalid data or a parsing error, it logs the exception and returns null.
    @Override
    public Message deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, Message.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null to indicate deserialization failure
        }
    }

}
