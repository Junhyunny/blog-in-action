package action.in.blog.component;

import io.jsonwebtoken.Jwts;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class AppleSecreteGenerator {

    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;

    private static final String AUDIENCE = "https://appleid.apple.com";
    private static final String ALGORITHM = "ES256";

    private final String keyId;
    private final String teamId;
    private final String clientId;

    public AppleSecreteGenerator(
            @Value("${apple.key-id}") String keyId,
            @Value("${apple.team-id}") String teamId,
            @Value("${spring.security.oauth2.client.registration.apple.client-id}") String clientId
    ) {
        this.keyId = keyId;
        this.teamId = teamId;
        this.clientId = clientId;
    }

    private Map<String, Object> tokenHeader() {
        return Map.of(
                "alg", ALGORITHM,
                "kid", keyId
        );
    }

    public String createClientSecret() {
        var resource = new ClassPathResource("static/private_key.p8");
        try (
                InputStream inputStream = resource.getInputStream();
                StringReader reader = new StringReader(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8))
        ) {
            var pemParser = new PEMParser(reader);
            var pemObject = pemParser.readObject();
            var converter = new JcaPEMKeyConverter();
            var privateKey = converter.getPrivateKey(
                    PrivateKeyInfo.getInstance(pemObject)
            );
            var now = System.currentTimeMillis();
            var builder = Jwts.builder();
            builder.header().add(tokenHeader())
                    .and();
            builder.signWith(privateKey)
                    .issuer(teamId)
                    .issuedAt(new Date(now))
                    .expiration(new Date(now + 10 * MINUTE))
                    .subject(clientId)
                    .audience().add(AUDIENCE)
                    .and();
            return builder.compact();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
