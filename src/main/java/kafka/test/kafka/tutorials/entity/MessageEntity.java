package kafka.test.kafka.tutorials.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity {

    private String title;
    private String contents;
    private LocalDateTime time;
    private String topicName;

}
