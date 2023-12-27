package action.in.blog.proxy;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.util.UriBuilderFactory;

public interface ExternalServiceClient {

    @GetExchange("/service-name")
    String getServiceName(UriBuilderFactory uriBuilderFactory);
}
