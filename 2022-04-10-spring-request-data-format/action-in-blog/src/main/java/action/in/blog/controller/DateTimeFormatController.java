package action.in.blog.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
public class DateTimeFormatController {

    private static final String datePattern = "yyyy-MM-dd HH:mm:ss.SSS";

    @Getter
    @Setter
    public static class ModelAttributeDto {
        @DateTimeFormat(pattern = datePattern)
        private Date date;
        @DateTimeFormat(pattern = datePattern)
        private LocalDateTime localDateTime;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class JacksonResponse {
        @JsonFormat(pattern = datePattern, timezone = "Asia/Seoul")
        private Date date;
        @JsonFormat(pattern = datePattern, timezone = "Asia/Seoul")
        private LocalDateTime localDateTime;
    }

    @GetMapping("/request-param")
    public JacksonResponse requestParam(
            @DateTimeFormat(pattern = datePattern)
            @RequestParam("date") Date date,
            @DateTimeFormat(pattern = datePattern)
            @RequestParam("localDateTime") LocalDateTime localDateTime) {
        return JacksonResponse.builder()
                .date(date)
                .localDateTime(localDateTime)
                .build();
    }

    @PostMapping("/model-attribute")
    public JacksonResponse modelAttribute(@ModelAttribute ModelAttributeDto modelAttributeDto) {
        return JacksonResponse.builder()
                .date(modelAttributeDto.getDate())
                .localDateTime(modelAttributeDto.getLocalDateTime())
                .build();
    }
}
