package kafka.test.kafka.tutorials.controller;


import kafka.test.kafka.tutorials.entity.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProducerController {

    // kafka producer를 위한 KafkaTemplate를 지정한다.
    private final KafkaTemplate<String, Object> kafkaProducerTemplate;

    public ProducerController(KafkaTemplate<String, Object> kafkaProducerTemplate) {
        this.kafkaProducerTemplate = kafkaProducerTemplate;
    }

    @PostMapping("/produce")
    public ResponseEntity<?> produceMessage(@RequestBody MessageEntity messageEntity) {
        log.info("messageEntity={}", messageEntity);
        messageEntity.setTime(LocalDateTime.now());

        // kafkaProducerTemplate.send를 이용하여 메시지를 전송한다.
        // 이때 토픽을 지정하고, 메시지를 전송하면 된다.
        // ListenableFuture 를 이용하여 전송 결과를 확인할 수 있다.
        ListenableFuture<SendResult<String, Object>> future = kafkaProducerTemplate.send(messageEntity.getTopicName(), messageEntity);

        // 메시지 처리는 비동기로 처리한다. 그러므로 callback을 지정했다.
//        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                log.error("Fail to send message to broker: {}", ex.getMessage());
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, Object> result) {
//                log.info("Send message with offset: {}, partition: {}", result.getRecordMetadata().offset(), result.getRecordMetadata().partition());
//            }
//        });

        return ResponseEntity.ok(messageEntity);

    }

    @PostMapping("produce-with-key/{key}")
    public ResponseEntity<?> produceMessageWithKey(@PathVariable("key") String key, @RequestBody MessageEntity messageEntity){

        log.info("key = {}, testEntity = {}", key, messageEntity);
        // 키를 함께 전달하여 키에 의한 파티셔닝을 수행하도록 전달.
        ListenableFuture<SendResult<String, Object>> future = kafkaProducerTemplate.send(messageEntity.getTopicName(), key, messageEntity);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("메세지를 보낼 수 없습니다: {}", ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("키와 함께 보낸 메세지: {}, offset: {}, partition: {}", key, result.getRecordMetadata().offset(), result.getRecordMetadata().partition());
            }
        });

        return ResponseEntity.ok(messageEntity);
    }
}
