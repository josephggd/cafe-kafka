package com.jsjavaprojects.kafkapractice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import utils.LoggingState;

import static utils.CommonStrings.LOG;
import static utils.CommonStrings.customerTopic;

@Component
public class KafkaListeners {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @KafkaListener(topics = customerTopic, groupId = "default")
    void customerListener(String data){
        logger.info(LOG, LoggingState.ATTEMPT, "customerListener");
        logger.info("DATA :{}", data);
    }
//    @KafkaListener(topics = orderTopic, groupId = "default")
//    void orderListener(String data){
//        logger.info(LOG, LoggingState.ATTEMPT, "orderListener");
//        logger.info("DATA :{}", data);
//    }
}
