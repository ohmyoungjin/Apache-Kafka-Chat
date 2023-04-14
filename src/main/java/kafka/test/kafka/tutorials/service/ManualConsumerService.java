package kafka.test.kafka.tutorials.service;

import kafka.test.kafka.tutorials.entity.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class ManualConsumerService {
    //ConsumerConfig 에서 설정한 bean 주입 받는다.
    private final Consumer<String, MessageEntity> manualConsumer;

    public ManualConsumerService(Consumer<String, MessageEntity> manualConsumer) {
        this.manualConsumer = manualConsumer;
        System.out.println("ManualConsumerService.ManualConsumerService >>>>>> ");
    }

    public List<Object> receiveMessages(String topicName, int partition, int offset, String keyWord) {

        System.out.println("ManualConsumerService.receiveMessages>>>>>");
        TopicPartition topicPartition = new TopicPartition(topicName, partition);
        manualConsumer.assign(Arrays.asList(topicPartition));
        manualConsumer.seek(topicPartition, offset);

        ConsumerRecords<String, MessageEntity> records = manualConsumer.poll(Duration.ofMillis(1000));

        if(!(keyWord == null)) {

        }
        for (ConsumerRecord<String, MessageEntity> record: records) {
            MessageEntity messageEntity = record.value();
            String contents = messageEntity.getContents();
            log.info("contents={}", contents);
            if (contents != null && contents.indexOf(keyWord) >=0) {
                // 필터링된 데이터 처리
                System.out.println("Filtered message: " + messageEntity);
            }
            log.info("Receive Manually: {}", record);
        }

        System.out.println("ManualConsumerService.receive 4444444444>>>>>");
        manualConsumer.unsubscribe();
        return StreamSupport.stream(records.spliterator(), false)
                .map(r -> r.value())
                .collect(Collectors.toList());
    }

    public List<MessageEntity> receiveMessages_keyword(String topicName, int partition, int offset, String keyWord) {

        TopicPartition topicPartition = new TopicPartition(topicName, partition);
        manualConsumer.assign(Arrays.asList(topicPartition));
        manualConsumer.seek(topicPartition, offset);

        ConsumerRecords<String, MessageEntity> records = manualConsumer.poll(Duration.ofMillis(1000));

        if(!(keyWord == null)) {

        }
        List<MessageEntity> filteredMessages = new ArrayList<>();
        for (ConsumerRecord<String, MessageEntity> record: records) {
            MessageEntity messageEntity = record.value();
            String contents = messageEntity.getContents();
            log.info("contents={}", contents);
            if (contents != null && contents.indexOf(keyWord) >=0) {
                // 필터링된 데이터 처리
                System.out.println("Filtered message: " + messageEntity);
                filteredMessages.add(messageEntity);
            }
            log.info("Receive Manually: {}", record);
        }

        System.out.println("ManualConsumerService.receive 4444444444>>>>>");
        manualConsumer.unsubscribe();
        return filteredMessages;
    }


}
