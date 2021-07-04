package blog.in.action.jackson;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.NestedServletException;

@Log4j2
@SpringBootTest
public class JsonIgnorePropertiesTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(ErrorController.class).build();
    }

    @Test
    public void test_withoutJsonIgnoreProperties_throwNestedServletException() {
        assertThrows(NestedServletException.class, () -> {
            try {
                mockMvc.perform(get("/error"));
            } catch (Exception e) {
                log.error(e);
                throw e;
            }
        });
    }

    @Test
    public void test_withJsonIgnoreProperties_isOk() throws Exception {
        mockMvc.perform(get("/ok"))
            .andExpect(status().isOk())
            .andDo(print());
    }
}

@RestController
class ErrorController {

    @GetMapping("/error")
    public ADto error() {
        ADto aDto = new ADto(new BDto());
        aDto.getBdto().setAdto(aDto);
        return aDto;
    }

    @GetMapping("/ok")
    public ADto ok() {
        ADto aDto = new ADto(new CDto());
        aDto.getCdto().setAdto(aDto);
        return aDto;
    }
}

@Getter
@Setter
@NoArgsConstructor
class ADto {

    public ADto(BDto bdto) {
        this.bdto = bdto;
    }

    public ADto(CDto cdto) {
        this.cdto = cdto;
    }

    private BDto bdto;

    @JsonIgnoreProperties(value = {"adto"})
    private CDto cdto;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class BDto {

    private ADto adto;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class CDto {

    private String name = "CDto";

    private ADto adto;
}