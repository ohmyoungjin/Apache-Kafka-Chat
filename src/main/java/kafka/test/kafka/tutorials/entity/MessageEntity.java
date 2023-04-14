package kafka.test.kafka.tutorials.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity {

    private String roomId; // 방 번호
    private String contents; // 메세지 내용
    private String time; // 채팅 발송 시간
    private String topicName; // 토픽 이름
    private String sender; //메세지 발송자
    private String messageAreaId; // 메세지 영역
    private String userNick; // 메세지 발송자 NickName

}
