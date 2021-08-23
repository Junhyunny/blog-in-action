package cloud.in.action.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "b-service")
public interface BServiceFeinClient {

	@GetMapping(path = "/information")
	String requestInformation();
}
