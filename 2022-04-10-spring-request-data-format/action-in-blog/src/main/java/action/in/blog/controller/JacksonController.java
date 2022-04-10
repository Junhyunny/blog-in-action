package action.in.blog.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
public class JacksonController {

    private static final String datePattern = "yyyy-MM-dd HH:mm:ss.SSS";

    @Getter
    @Setter
    @NoArgsConstructor
    public static class JacksonRequest {
        @JsonFormat(pattern = datePattern)
        private Date date;
        @JsonFormat(pattern = datePattern)
        private Timestamp timestamp;
        @JsonFormat(pattern = datePattern)
        private LocalDateTime localDateTime;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class JacksonResponse {
        @JsonFormat(pattern = datePattern)
        private Date date;
        @JsonFormat(pattern = datePattern)
        private Timestamp timestamp;
        @JsonFormat(pattern = datePattern)
        private LocalDateTime localDateTime;
    }

    @PostMapping("/jackson")
    public JacksonResponse getJacksonDto(@RequestBody JacksonRequest request) {
        return JacksonResponse.builder()
                .date(request.getDate())
                .timestamp(request.getTimestamp())
                .localDateTime(request.getLocalDateTime())
                .build();
    }
}
