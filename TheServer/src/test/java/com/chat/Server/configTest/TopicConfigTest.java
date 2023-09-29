package com.chat.Server.configTest;

import com.chat.Server.config.TopicConfing;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@EmbeddedKafka
@ActiveProfiles("test")
public class TopicConfigTest {

    private static AdminClient adminClient;


    //`setUp` method initializes an `AdminClient` instance with the bootstrap server
    //configuration.
    @BeforeAll
    public static void setUp() {
        Properties adminClientProperties = new Properties();
        adminClientProperties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        adminClient = AdminClient.create(adminClientProperties);
    }

    //`tearDown` method closes the `AdminClient` after tests are completed.
    @AfterAll
    public static void tearDown() {
        adminClient.close();
    }

    //`testCreateGeneralTopic` method tests the `CreateGeneralTopic` bean method in the
    //`TopicConfig` class by creating an instance of `TopicConfig` and asserting that the
    //created topic's name is "general."
    @Test
    public void testCreateGeneralTopic() throws Exception {
        TopicConfing topicConfing = new TopicConfing();
        NewTopic topic = topicConfing.CreateGeneralTopic();

        assertEquals("general", topic.name());

    }
}