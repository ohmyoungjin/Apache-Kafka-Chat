package kafka.test.kafka.tutorials.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import kafka.test.kafka.tutorials.entity.MessageEntity;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

//    private ConsumerFactory<String, Object> consumerFactory(String groupId) {
//        Map<String, Object> props = new HashMap<>();
//
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//
//        JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
//        // Deserialize에 대해서 신뢰하는 패키지를 지정한다. "*"를 지정하면 모두 신뢰하게 된다.
//        jsonDeserializer.addTrustedPackages("*");
//
//        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
//    }
private ConsumerFactory<String, MessageEntity> consumerFactory(String groupId) {
    Map<String, Object> props = new HashMap<>();

    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

    JsonDeserializer<MessageEntity> jsonDeserializer = new JsonDeserializer<>();
    // Deserialize에 대해서 신뢰하는 패키지를 지정한다. "*"를 지정하면 모두 신뢰하게 된다.
    jsonDeserializer.addTrustedPackages("*");

    return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
}



//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> defaultKafkaListenerContainerFactory() {
//        //ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>(); //kafka cluster로 부터 메세지를 읽을 수 있도록 지정
//        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>(); //kafka cluster로 부터 메세지를 읽을 수 있도록 지정
//        factory.setConsumerFactory(consumerFactory("defaultGroup"));
//        factory.setConcurrency(1); // 동시에 읽을 컨슈머의 개수 지정
//        factory.setAutoStartup(true); //메세지 리스너 자동 실행 여부
//        return factory;
//    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MessageEntity> defaultKafkaListenerContainerFactory() {
        //ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>(); //kafka cluster로 부터 메세지를 읽을 수 있도록 지정
        ConcurrentKafkaListenerContainerFactory<String, MessageEntity> factory = new ConcurrentKafkaListenerContainerFactory<>(); //kafka cluster로 부터 메세지를 읽을 수 있도록 지정
        factory.setConsumerFactory(consumerFactory("defaultGroup"));
        factory.setConcurrency(1); // 동시에 읽을 컨슈머의 개수 지정
        factory.setAutoStartup(true); //메세지 리스너 자동 실행 여부
        return factory;
    }

    /**
     * 수동 컨슈머를 작성한다.
     * 기존과 다른것은 Consumer 객체를 반환하는 것이다.
     * @return 컨슈머를 반환합니다.
     */
//    @Bean
//    public Consumer<String, Object> manualConsumer() {
//        System.out.println("KafkaConsumerConfig.manualConsumer>>>>>>>>");
//        return consumerFactory("defaultGroup").createConsumer();
//    }
    @Bean
    public Consumer<String, MessageEntity> manualConsumer() {
        System.out.println("KafkaConsumerConfig.manualConsumer>>>>>>>>");
        return consumerFactory("defaultGroup").createConsumer();
    }
}
