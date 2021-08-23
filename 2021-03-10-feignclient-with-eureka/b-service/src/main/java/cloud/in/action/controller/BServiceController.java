package cloud.in.action.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class BServiceController {

	@Autowired
	private Environment environment;

	@GetMapping(value = "/information")
	public String requestInformation() {
		String host = null;
		try {
			Thread.sleep(50);
			host = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
		return "host: " + host + ", port: " + environment.getProperty("local.server.port");
	}
}
