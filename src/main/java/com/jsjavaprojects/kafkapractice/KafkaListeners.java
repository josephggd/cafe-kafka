package com.jsjavaprojects.kafkapractice;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(topics = "jsjavaprojects", groupId = "default")
    void listener(String data){
        System.out.println("Data :"+data);
    }
}
