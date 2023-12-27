package action.in.blog.controller;

import action.in.blog.proxy.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.DefaultUriBuilderFactory;

@RestController
public class CommunicationController {

    private final static Logger logger = LoggerFactory.getLogger(CommunicationController.class);

    private final ExternalServiceClient externalServiceClient;

    @Value("${service.name}")
    private String serviceName;

    public CommunicationController(ExternalServiceClient externalServiceClient) {
        this.externalServiceClient = externalServiceClient;
    }

    @GetMapping("/service-name")
    public String serviceName() {
        return serviceName;
    }

    @GetMapping("/external/{serviceName}")
    public String externalServiceName(@PathVariable String serviceName) {
        logger.info("by pass to {}", serviceName);
        return externalServiceClient.getServiceName(new DefaultUriBuilderFactory(serviceName));
    }
}
