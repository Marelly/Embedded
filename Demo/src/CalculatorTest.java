import static org.junit.Assert.*;

import org.junit.Test;

public class CalculatorTest {

	@Test
	public void testAdd() {
		Calculator calc = new Calculator();
		assert(calc.add(3, 5) == 8);
	}

	@Test
	public void testSub() {
		Calculator calc = new Calculator();
		assert(calc.sub(8, 5) == 3);
	}
	
	@Test
	public void testMul() {
		Calculator calc = new Calculator();
		assert(calc.mul(3, 5) == 15);
	}
	
	@Test
	public void testDiv() {
//		Calculator calc = new Calculator();
//		assert(calc.div(12, 5) == 2);
		fail("Not yet implemented");
	}
}
