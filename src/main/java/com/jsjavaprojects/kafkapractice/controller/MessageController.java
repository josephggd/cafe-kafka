package com.jsjavaprojects.kafkapractice.controller;

import com.jsjavaprojects.kafkapractice.dto.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}")
public class MessageController {

    public MessageController(KafkaTemplate<String, String> template){
        this.template = template;
    }
    private final KafkaTemplate<String, String> template;

    @PostMapping("messages")
    public void publish(@RequestBody Message message){
        template.send("jsjavaprojects", message.getMsg());
    }
}
