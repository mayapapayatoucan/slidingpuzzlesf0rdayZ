import static org.junit.Assert.*;

import org.junit.Test;


public class BlockTest {

	@Test
	public void testBlocks() {
		Block b1 = new Block("0 0 1 0");
		Block b2 = new Block("0 0 0 0");
		Block b3 = new Block("0 0 1 1");
		assertEquals(1, b1.height());
		assertEquals(0, b1.width());
		assertEquals(0, b2.height());
		assertEquals(0, b2.width());
		assertEquals(1, b3.height());
		assertEquals(1, b3.width());
		assertTrue(testExceptions(""));
		assertTrue(testExceptions("0010"));
		assertTrue(testExceptions("1 1 0 0"));
		assertTrue(testExceptions("1 2 3"));
		assertTrue(testExceptions("a b c d"));
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
