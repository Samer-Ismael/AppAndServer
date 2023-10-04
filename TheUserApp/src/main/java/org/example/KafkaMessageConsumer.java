package org.example;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import javax.swing.*;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaMessageConsumer {
    Consumer<String, Message> consumer;
    private final JTextPane textPane;

    public KafkaMessageConsumer(String bootstrapServers, String groupId, String topic, JTextPane textPane) {
        // Store the reference to the JTextPane provided as a parameter.
        this.textPane = textPane;

        // Create a Properties object to configure the Kafka consumer.
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", MessageDeserializer.class.getName());
        //The only wa to make it read from the topic as object is to create a custom deserializer.
        // I markt the Message Entity class as the object im trying to deserialize and not it works.

        // Create a KafkaConsumer instance with the properties.
        consumer = new KafkaConsumer<>(properties);
        // Subscribe the consumer to the specified Kafka topic.
        consumer.subscribe(Collections.singleton(topic));
    }

    public void consumeMessages() {
        try {
            while (true) {
                // Poll for Kafka records with a timeout of 100 milliseconds.
                //This part specifies the duration for which the poll method will wait for new records to arrive.
                // In this case, it's set to 100 milliseconds.
                // This means that the poll method will block for up to 100 milliseconds to wait for new messages.
                // If no messages arrive during this time, it will return an empty set of records.
                ConsumerRecords<String, Message> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, Message> record : records) {

                    Message message1 = record.value();
                    System.out.println("Sender: " + message1.getSender());
                    System.out.println("Receiver: " + message1.getReceiver());
                    System.out.println("body: " + message1.getBody());
                    SwingUtilities.invokeLater(() -> {
                        textPane.setText(textPane.getText() + "\n" + message1.showMessage());
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Ensure that the Kafka consumer is always closed even in case of exceptions.
            consumer.close();
        }
    }

    //Close the thing :)
    public void close() {
        consumer.close();
    }
}