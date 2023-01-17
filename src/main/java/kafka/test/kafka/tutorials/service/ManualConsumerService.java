package kafka.test.kafka.tutorials.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class ManualConsumerService {
    //ConsumerConfig 에서 설정한 bean 주입 받는다.
    private final Consumer<String, Object> manualConsumer;

    public ManualConsumerService(Consumer<String, Object> manualConsumer) {
        this.manualConsumer = manualConsumer;
    }

    public List<Object> receiveMessages(String topicName, int partition, int offset) {

        System.out.println("ManualConsumerService.receiveMessages>>>>>");
        TopicPartition topicPartition = new TopicPartition(topicName, partition);
        manualConsumer.assign(Arrays.asList(topicPartition));
        manualConsumer.seek(topicPartition, offset);

        System.out.println("ManualConsumerService.receiveMessages222222222>>>>>");
        ConsumerRecords<String, Object> records = manualConsumer.poll(Duration.ofMillis(1000));

        System.out.println("ManualConsumerService.receiveMessages33333333333333>>>>>");
        for (ConsumerRecord<String, Object> record: records) {
            log.info("Receive Manually: {}", record);
        }

        System.out.println("ManualConsumerService.receive 4444444444>>>>>");
        manualConsumer.unsubscribe();
        return StreamSupport.stream(records.spliterator(), false)
                .map(r -> r.value())
                .collect(Collectors.toList());
    }
}
