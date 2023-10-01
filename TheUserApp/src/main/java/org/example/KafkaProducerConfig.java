package org.example;

import lombok.Getter;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class KafkaProducerConfig {

    // This was needed for saving the name and the message from the GUI Class.
    @Getter
    String userName;
    String messageBody;

    // Methods for testing the app before making GUI
    public void startAsking (){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your name: ");
            userName = scanner.nextLine();
            System.out.println("Welcome her :) ");
            while (true) {
                System.out.println("Enter your message: ");
                messageBody = scanner.nextLine();
                Message message = new Message(userName, messageBody);
                sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public String getUserNameFromUser (){
        //Scanner means that something can go wrong. try catch better be there.
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your name: ");
            return scanner.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // It should send a post request to the URL with a JSON payload containing a
    // `Message` object. and return if it was ok or not
    public void sendMessage(Message message) {
        try {
            String url = "http://localhost:8080/chat/room/send";
            URL obj = new URL(url);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            // Convert Message object to JSON string
            String jsonPayload = message.toJsonString();

            // Send the JSON payload
            try (OutputStream os = con.getOutputStream()) {
                os.write(jsonPayload.getBytes(StandardCharsets.UTF_8));
            }
            int responseCode = con.getResponseCode();

            // fancy if else
            String respons = (responseCode == 200 ? ("Sent " + userName) : "Somthing went wrogn " + responseCode);
            System.out.println(respons);


            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //Accepts the message body and userName as parameters, constructs a `Message` object, and sends
    //it to the chat room.
    public void sendFromGUI (String body, String userName){
        Message message = new Message(userName, body);
        sendMessage(message);

    }

    //Setter method to set the user's name.
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
