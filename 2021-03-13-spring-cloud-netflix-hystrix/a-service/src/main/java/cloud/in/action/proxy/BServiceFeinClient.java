package cloud.in.action.proxy;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@FeignClient(name = "b-service", fallbackFactory = BServiceFallbackFactory.class)
public interface BServiceFeinClient {

	@GetMapping(path = "/timeout")
	String requestWithTimeout();

	@GetMapping(path = "/exception")
	String requestWithException();
}

@Log4j2
@Component
class BServiceFallbackFactory implements FallbackFactory<BServiceFeinClient> {

	@Override
	public BServiceFeinClient create(Throwable cause) {
		log.error(cause.getMessage(), cause);
		return new BServiceFallback();
	}

	class BServiceFallback implements BServiceFeinClient {

		@Override
		public String requestWithTimeout() {
			return "time out fallback";
		}

		@Override
		public String requestWithException() {
			return "exception fallback";
		}
	};
}