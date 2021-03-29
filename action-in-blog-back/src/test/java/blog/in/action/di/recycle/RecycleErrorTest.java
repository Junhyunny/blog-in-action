package blog.in.action.di.recycle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@SpringBootTest
public class RecycleErrorTest {

	@Autowired
	AComponent aComponent;

	@Test
	public void test() {
		aComponent.doThing();
	}
}

@Component
class AComponent {

	@Autowired
	BComponent bComponent;

	void doThing() {
		bComponent.doThing();
	}
}

@Component
class BComponent {

	@Autowired
	AComponent aComponent;

	void doThing() {
		aComponent.doThing();
	}
}