package gradleProject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AssertTest {

	@Test
	void test() {
		int actual = Assert.sum(10);
		int expect = 20;
		assertEquals(actual , expect);
	}
}
