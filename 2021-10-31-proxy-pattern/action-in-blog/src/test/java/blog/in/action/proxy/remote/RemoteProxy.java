package blog.in.action.proxy.remote;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class RemoteProxy implements RemoteSubject {

    private RestTemplate restTemplate;

    public RemoteProxy() {
        restTemplate = new RestTemplate();
    }

    @Override
    public void printGoogleMainPage() {
        log.info(restTemplate.getForEntity("https://www.google.com", String.class));
    }
}
