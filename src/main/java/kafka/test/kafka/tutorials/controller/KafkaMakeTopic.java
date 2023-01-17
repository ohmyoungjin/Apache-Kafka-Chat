package kafka.test.kafka.tutorials.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/kafka")
public class KafkaMakeTopic {

    private KafkaAdmin kafkaAdmin;

    @Autowired
    public KafkaMakeTopic(KafkaAdmin kafkaAdmin) {
        //topic 생성에 필요한 kafkaAdmin 주입
        this.kafkaAdmin = kafkaAdmin;
        System.out.println("KafkaMakeTopic.KafkaMakeTopic>>>>");
    }

    /**
     * topic 생성 api
     * @param topicName
     */
    @PostMapping("/makeTopic")
    private void makeTopic (@RequestParam("topicName") String topicName) {
        System.out.println("makeTopic >>> ");
        NewTopic newTopic = TopicBuilder.name(topicName)
                .build();
        kafkaAdmin.createOrModifyTopics(newTopic);
        log.info("create topic complete");
    }
}
