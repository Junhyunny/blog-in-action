package blog.in.action;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class MutableTest {

	class MutableObject {

		private int value;

		public MutableObject(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}

	@Test
	public void test() {
		MutableObject mutableObject = new MutableObject(0);
		log.info("값 변경 전 객체 주소: " + System.identityHashCode(mutableObject) + ", 객체 value 값: " + mutableObject.getValue());
		mutableObject.setValue(1);
		log.info("값 변경 후 객체 주소: " + System.identityHashCode(mutableObject) + ", 객체 value 값: " + mutableObject.getValue());
	}
}
