import static org.junit.Assert.*;

import org.junit.Test;


public class BlockTest {

	@Test
	public void testBlocks() {
		Block b1 = new Block("0 0 0 0");
		Block b2 = new Block("0 0 1 1");
		Block b3 = new Block("0 0 1 0");
		Block b4 = new Block("0 0 0 1");
		Block b5 = new Block("10 100 1000 10000");   // coordinates can be more than one digit
		assertEquals(0, b1.height());
		assertEquals(0, b1.width());
		assertEquals(1, b2.height());
		assertEquals(1, b2.width());
		assertEquals(1, b3.height());
		assertEquals(0, b3.width());
		assertEquals(0, b4.height());
		assertEquals(1, b4.width());
		
		assertTrue(testExceptions(""));
		assertTrue(testExceptions("0010"));
		assertTrue(testExceptions("1 1 0 0"));
		assertTrue(testExceptions("1 2 3"));
		assertTrue(testExceptions("a b c d"));
		assertTrue(testExceptions("A B C D"));
		assertTrue(testExceptions("+ - * /"));
		assertTrue(testExceptions("-1 -1 -1 -1"));
		assertTrue(testExceptions("0 0      0   0"));   // only one space between integers allowed
	}
	
	public boolean testExceptions (String s) {
		try {
			new Block(s);
			return false;
		} catch (IllegalArgumentException e) {
			return true;
		}
	}

}
