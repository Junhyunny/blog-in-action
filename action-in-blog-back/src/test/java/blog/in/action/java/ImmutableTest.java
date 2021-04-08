package blog.in.action.java;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class ImmutableTest {

	class ImmutableObject {

		private final int value;

		public ImmutableObject(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	@Test
	public void test() {
		String str = "A";
		log.info("값 변경 전 객체 주소: " + System.identityHashCode(str) + ", 객체 value 값: " + str);
		// str = str + "B";
		str = str.concat("B");
		log.info("값 변경 후 객체 주소: " + System.identityHashCode(str) + ", 객체 value 값: " + str);
	}

	@Test
	public void integerTest() {
		Integer integer = Integer.valueOf(0);
		log.info("값 변경 전 객체 주소: " + System.identityHashCode(integer) + ", 객체 value 값: " + integer);
		integer = integer + 2;
		log.info("값 변경 후 객체 주소: " + System.identityHashCode(integer) + ", 객체 value 값: " + integer);
	}
}
