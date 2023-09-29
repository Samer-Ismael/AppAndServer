package com.chat.Server.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfing {

    //CreateGeneralTopic, that creates a Kafka topic
    //named "general" using `TopicBuilder`.
    @Bean
    public NewTopic CreateGeneralTopic (){
        return TopicBuilder.name("general").build();
    }


}
