package action.in.blog.component;

import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Component
public class AppleOAuth2AuthorizationCodeGrantRequestEntityConverter extends OAuth2AuthorizationCodeGrantRequestEntityConverter {

    private static final String APPLE_REGISTRATION_ID = "apple";
    private static final String CLIENT_SECRET_KEY = "client_secret";

    private final AppleSecreteGenerator appleSecreteGenerator;

    public AppleOAuth2AuthorizationCodeGrantRequestEntityConverter(
            AppleSecreteGenerator appleSecreteGenerator
    ) {
        this.appleSecreteGenerator = appleSecreteGenerator;
    }

    @Override
    protected MultiValueMap<String, String> createParameters(
            OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest
    ) {
        var clientRegistrationId = authorizationCodeGrantRequest.getClientRegistration().getRegistrationId();
        if (APPLE_REGISTRATION_ID.equalsIgnoreCase(clientRegistrationId)) { // 1
            var encryptedPrivateKey = appleSecreteGenerator.createClientSecret(); // 2
            var parameter = super.createParameters(authorizationCodeGrantRequest); // 3
            parameter.put(CLIENT_SECRET_KEY, List.of(encryptedPrivateKey)); // 4
            return parameter;
        }
        return super.createParameters(authorizationCodeGrantRequest); // 5
    }
}