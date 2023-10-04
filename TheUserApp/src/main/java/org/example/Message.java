package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
//lombok dose this cool things
public class Message {
    private long id;
    private String sender;
    private String receiver;
    private String body;

    // normal Class that has more variables than the app needs, maybe for future updates.

    @JsonCreator
    public Message(@JsonProperty("id") int id,
                   @JsonProperty("sender") String sender,
                   @JsonProperty("receiver") String receiver,
                   @JsonProperty("body") String body) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
    }

    public Message(String sender, String receiver, String body) {
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
    }

    public Message(String sender, String body) {
        this.sender = sender;
        this.body = body;
        this.receiver = "general";
    }

    public Message() {
    }

    public String showMessage (){
        return String.format(getSender() + ": " + getBody());
    }

    //this will make a json String from the object
    public String toJsonString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            // Handle JSON processing exceptions
            e.printStackTrace();
            return null;
        }
    }

    // This will make an object out off a json String.
    public static Message fromJsonString(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, Message.class);
        } catch (JsonProcessingException e) {
            // Handle JSON processing exceptions
            e.printStackTrace();
            return null;
        }
    }
}