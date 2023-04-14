package kafka.test.kafka.tutorials.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultResponse {

    private String resultCode; //응답 코드
    private String resultMsg; //응답 메세지
}
