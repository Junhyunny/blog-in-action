package action.in.blog;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class HmacFilter extends OncePerRequestFilter {

    private static final String ALGORITHM = "HmacSHA256";
    private final String hmacSecret;

    public HmacFilter(@Value("${hmac.secret}") String hmacSecret) {
        this.hmacSecret = hmacSecret;
    }

    // JSON 메세지를 추출합니다.
    // application/json 타입의 메세지는 입력 스트림(stream)에 저장되어 있습니다.
    private String getJsonMessage(HttpServletRequest request) {
        try (
                OutputStream outputStream = new ByteArrayOutputStream();
                InputStream inputStream = request.getInputStream()
        ) {
            inputStream.transferTo(outputStream);
            return outputStream.toString();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    // 요청 객체에서 Form 메세지를 추출합니다.
    // application/x-www-form-urlencoded 타입의 메세지는 파라미터 프로퍼티를 통해 전달받습니다.
    private String getFormMessage(HttpServletRequest request) {
        var stringBuilder = new StringBuilder();
        var parameterMap = request.getParameterMap();
        for (String key : parameterMap.keySet()) {
            var values = parameterMap.get(key);
            stringBuilder
                    .append(
                            Arrays.stream(values)
                                    .map(value -> String.format("%s=%s", key, value))
                                    .collect(Collectors.joining("&"))
                    ).append("&");
        }
        if (stringBuilder.length() <= 0) {
            return "";
        }
        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
    }

    // Content-Type 헤더 값에 따라 적절한 메세지를 반환합니다.
    private String getMessage(HttpServletRequest request) {
        if (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equalsIgnoreCase(request.getContentType())) {
            return getFormMessage(request);
        }
        return getJsonMessage(request);
    }

    // HMAC을 생성합니다.
    // 주입 받은 비밀 키와 미리 지정한 알고리즘을 사용합니다.
    private String getHmac(String message, String requestTimestamp) {
        SecretKey secretKey = new SecretKeySpec(hmacSecret.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Mac hashFunction = null;
        try {
            hashFunction = Mac.getInstance(ALGORITHM);
            hashFunction.init(secretKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        byte[] digestBytes = hashFunction.doFinal(
                String.format("%s%s", message, requestTimestamp).getBytes(StandardCharsets.UTF_8)
        );
        return HexUtils.toHexString(digestBytes);
    }

    // 필터링을 수행합니다.
    // 1. GET, DELETE 요청은 다음 필터로 진행합니다.
    // 2. 전달 받은 HMAC과 타임스탬프를 헤더에서 꺼냅니다.
    // 3. 요청 객체로부터 전달 받은 메세지를 꺼냅니다.
    // 4. 타임스탬프와 메세지를 조합한 값으로 HMAC을 구합니다.
    // 5. 생성한 HMAC과 전달받은 HMAC을 비교합니다.
    // 6. 정상적인 경우 다음 필터로 진행합니다.
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var wrappedRequest = new RepeatableReadRequest(request);
        var method = request.getMethod();
        if (HttpMethod.GET.name().equalsIgnoreCase(method) || HttpMethod.DELETE.name().equalsIgnoreCase(method)) {
            filterChain.doFilter(wrappedRequest, response);
            return;
        }

        var receivedHmac = request.getHeader("X-REQUEST-HMAC");
        var requestTimestamp = request.getHeader("X-REQUEST-TIMESTAMP");
        var message = getMessage(wrappedRequest);

        String hmac = getHmac(message, requestTimestamp);

        if (!hmac.equals(receivedHmac)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        filterChain.doFilter(wrappedRequest, response);
    }
}
