package kafka.test.kafka.tutorials.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

//topic 생성에 필요한 KafkaAdmin 설정해주기 위한 file
@Configuration
public class KafkaAdminConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        System.out.println("KafkaAdminConfig.kafkaAdmin >>>>");

        return new KafkaAdmin(configs);
    }
}
