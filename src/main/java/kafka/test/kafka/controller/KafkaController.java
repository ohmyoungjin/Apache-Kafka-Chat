package kafka.test.kafka.controller;

import kafka.test.kafka.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("*")
@RestController

@RequestMapping(value = "/kafka/test")
public class KafkaController {
    private final KafkaProducer producer;

    @Autowired
    KafkaController(KafkaProducer producer) {
        this.producer = producer;
    }


    @PostMapping(value = "/message")
    public String sendMessage(@RequestParam("message") String message) {
        System.out.println("KafkaController!!!!!!!!!!!");
        this.producer.sendMessage(message);
        return "success";
    }
}