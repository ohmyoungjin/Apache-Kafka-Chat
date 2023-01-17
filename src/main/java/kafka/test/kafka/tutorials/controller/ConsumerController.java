package kafka.test.kafka.tutorials.controller;

import kafka.test.kafka.tutorials.service.ManualConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/consumer")
public class ConsumerController {

    private final ManualConsumerService manualConsumerService;


    public ConsumerController(ManualConsumerService manualConsumerService) {
        //수동으로 등록한 consumer 정보
        this.manualConsumerService = manualConsumerService;
    }

    //구독하고자 하는 consumer 설정
    @PostMapping("/consume")
    public ResponseEntity<?> getMessage(
            @RequestParam(value = "partition", required = false, defaultValue = "0") Integer partition,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam("topicName") String topicName) {
        log.info("/consume param partition={}, offset={}, topicName={}", partition, offset, topicName);
        return ResponseEntity.ok(manualConsumerService.receiveMessages(topicName, partition, offset));

    }
}