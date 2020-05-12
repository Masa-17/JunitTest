package gradleProject;


import org.junit.jupiter.api.Test;

class SampleTest {

	Sample sam = new Sample();
	@Test
	void test() {
		
        int actual = sam.sum(5, 2);
        System.out.println(actual);
	}
	@Test
	void test2() {
		
        int actual = sam.ave(6, 2);
        System.out.println(actual);
	}
	@Test
	void test3() {
		
        int actual = sam.kake(6, 2);
        System.out.println(actual);
	}

}
