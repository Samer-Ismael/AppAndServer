package org.example;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import javax.swing.*;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaMessageConsumer {
    Consumer<String, String> consumer;
    private final JTextPane textPane;

    public KafkaMessageConsumer(String bootstrapServers, String groupId, String topic, JTextPane textPane) {
        // Store the reference to the JTextPane provided as a parameter.
        this.textPane = textPane;

        // Create a Properties object to configure the Kafka consumer.
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

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
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    String message = record.value();
                    Message message1 = Message.fromJsonString(message);

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