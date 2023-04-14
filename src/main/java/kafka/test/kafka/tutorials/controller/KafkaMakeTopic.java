package kafka.test.kafka.tutorials.controller;

import kafka.test.kafka.tutorials.entity.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
     * @param topicName 토픽 이름
     * @param partition 파티션 수
     */
    @PostMapping("/makeTopic/{topicName}")
    private ResultResponse makeTopic (@PathVariable("topicName") String topicName,
                                         @RequestParam(value = "partition", required = false, defaultValue = "2") Integer partition) {
        try {
            log.info("makeTopic={}", topicName);
            NewTopic newTopic = TopicBuilder.name(topicName)
                    .partitions(partition) //파티션 수 설정
//                .replicas(2) 파티션 복제 계수 설정
                    .build();
            kafkaAdmin.createOrModifyTopics(newTopic);
            log.info("create topic complete={}", topicName);
            return ResultResponse.builder()
                    .resultCode("200")
                    .resultMsg("성공적으로 처리됐습니다!")
                    .build();
        } catch(Exception e) {
            return ResultResponse.builder()
                    .resultCode("901")
                    .resultMsg("토픽 생성이 정상적으로 처리되지 못했습니다.")
                    .build();
        }
    }

    @GetMapping("/hello/{id}")
    @ResponseBody
    public List<String> helloController(@PathVariable String id) {
        log.info("넘어오긴 합니다!");
        List<String> array = new ArrayList<>();
        if (id == null) {
            log.info("id값이 없습니다!");
            array.add("error");
            return array;
        } else {
            log.info("있긴 합니다={}", id);
            int a = Integer.parseInt(id);
            log.info("a={}", a);
            for(int i=0; i<10; i++) {
                array.add(id);
            }
            return array;
        }
    }
}
