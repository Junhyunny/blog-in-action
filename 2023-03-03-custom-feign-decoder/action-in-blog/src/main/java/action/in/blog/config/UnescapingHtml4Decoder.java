package action.in.blog.config;

import feign.FeignException;
import feign.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class UnescapingHtml4Decoder extends SpringDecoder {

    public UnescapingHtml4Decoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        super(messageConverters);
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        Response.Body body = response.body();
        String responseValue = IOUtils.toString(body.asReader(StandardCharsets.UTF_8));
        return super.decode(
                Response.builder()
                        .status(response.status())
                        .request(response.request())
                        .headers(response.headers())
                        .body(StringEscapeUtils.unescapeHtml4(responseValue), StandardCharsets.UTF_8)
                        .build(),
                type);
    }
}