package action.in.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.buf.HexUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@TestPropertySource(
        properties = {"hmac.secret=hmac-secret"}
)
class ActionInBlogApplicationTests {

    @Autowired
    MockMvc mockMvc;

    private String getHmac(String message, long unixTimestamp) {
        SecretKey secretKey = new SecretKeySpec("hmac-secret".getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac hashFunction = null;
        try {
            hashFunction = Mac.getInstance("HmacSHA256");
            hashFunction.init(secretKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        byte[] digestBytes = hashFunction.doFinal(String.format("%s%s", message, unixTimestamp).getBytes(StandardCharsets.UTF_8));
        return HexUtils.toHexString(digestBytes);
    }

    @Test
    void pass_hmac_filter_when_get_method() throws Exception {

        mockMvc.perform(get("/todos"))
                .andExpect(jsonPath("$[0].content").value("Hello"))
                .andExpect(jsonPath("$[1].content").value("World"))
                .andExpect(jsonPath("$[2].content").value("Study"))
                .andExpect(jsonPath("$[3].content").value("Java"))
        ;
    }

    @Test
    void pass_hmac_filter_when_delete_method() throws Exception {

        mockMvc.perform(delete("/todos/100001"))
                .andExpect(jsonPath("$").value(100001));
    }

    @Test
    void pass_hmac_filter_when_post_method() throws Exception {

        var objectMapper = new ObjectMapper();
        var currentTimestamp = Instant.now().getEpochSecond();
        var message = objectMapper.writeValueAsString(
                Todo.builder()
                        .id(0)
                        .content("This is new todo")
                        .build()
        );


        mockMvc.perform(
                        post("/todos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-REQUEST-TIMESTAMP", currentTimestamp)
                                .header("X-REQUEST-HMAC", getHmac(message, currentTimestamp))
                                .content(message)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2000))
                .andExpect(jsonPath("$.content").value("This is new todo"))
        ;
    }

    @Test
    void bad_request_when_changed_message() throws Exception {

        var objectMapper = new ObjectMapper();
        var currentTimestamp = Instant.now().getEpochSecond();
        var message = objectMapper.writeValueAsString(
                Todo.builder()
                        .id(0)
                        .content("This is new todo")
                        .build()
        );
        var changeMessage = objectMapper.writeValueAsString(
                Todo.builder()
                        .id(1)
                        .content("This is new todo")
                        .build()
        );


        mockMvc.perform(
                        post("/todos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-REQUEST-TIMESTAMP", currentTimestamp)
                                .header("X-REQUEST-HMAC", getHmac(message, currentTimestamp))
                                .content(changeMessage)
                )
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void pass_hmac_filter_when_put_method() throws Exception {

        var currentTimestamp = Instant.now().getEpochSecond();
        var message = "id=2000&content=This is update todo";


        mockMvc.perform(
                        put("/todos")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .header("X-REQUEST-TIMESTAMP", currentTimestamp)
                                .header("X-REQUEST-HMAC", getHmac(message, currentTimestamp))
                                .content(message)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2000))
                .andExpect(jsonPath("$.content").value("This is update todo"))
        ;
    }

    @Test
    void bad_request_when_changed_message_form_url_encoded() throws Exception {

        var currentTimestamp = Instant.now().getEpochSecond();
        var message = "id=2000&content=This is update todo";
        var changedMessage = "id=2001&content=This is update todo";


        mockMvc.perform(
                        put("/todos")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .header("X-REQUEST-TIMESTAMP", currentTimestamp)
                                .header("X-REQUEST-HMAC", getHmac(message, currentTimestamp))
                                .content(changedMessage)
                )
                .andExpect(status().isBadRequest())
        ;
    }
}