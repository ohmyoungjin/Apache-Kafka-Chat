package kafka.test.kafka.tutorials.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageListener {

    //kafkaConsumerConfig 에서 설정 한 defaultKafkaListenerContainerFactory 통해 이벤트 감지
    //@KafkaListener(topics = "hello", containerFactory = "defaultKafkaListenerContainerFactory")
    @KafkaListener(topics = "hello", containerFactory = "defaultKafkaListenerContainerFactory")
    public void listenDefaultTopic(Object record) {
        log.info("Receive Message from {}, values {}", "hello", record);

    }
}
