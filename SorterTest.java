import static org.junit.Assert.*;

import org.junit.Test;


public class SorterTest {

  @Test
	public void testParse() {
		Sorter s = new Sorter();
		assertTrue(s.isBlock("0 0 0 0"));
		assertFalse(s.isBlock("0000"));
		assertFalse(s.isBlock(""));
		assertFalse(s.isBlock("a b c d"));
		assertFalse(s.isBlock("0 0 0"));
		assertFalse(s.isBlock("0 0 0 0 0"));
		// add debugging tests
	}
	
	@Test
	public void testInput() {
		Sorter s1 = new Sorter("test1.txt");   // Figure 1 in the project documentation
		assertEquals(5, s1.getTray().width());
		assertEquals(4, s1.getTray().height());
		assertTrue(s1.getTray().containsBlock(0, 0, 1, 0));
		assertTrue(s1.getTray().containsBlock(0, 3, 1, 3));
		assertTrue(s1.getTray().containsBlock(2, 0, 3, 0));
		assertTrue(s1.getTray().containsBlock(2, 3, 3, 3));
		assertTrue(s1.getTray().containsBlock(1, 1, 2, 2));
		assertTrue(s1.getTray().containsBlock(3, 1, 3, 2));
		assertTrue(s1.getTray().containsBlock(4, 0, 4, 0));
		assertTrue(s1.getTray().containsBlock(4, 1, 4, 1));
		assertTrue(s1.getTray().containsBlock(4, 2, 4, 2));
		assertTrue(s1.getTray().containsBlock(4, 3, 4, 3));
		// testing other file formats
		Sorter s2 = new Sorter("easy");
		assertEquals(2, s2.getTray().width());
		assertEquals(2, s2.getTray().height());
		assertTrue(s2.getTray().containsBlock(0, 0, 0, 0));
		assertTrue(s2.getTray().containsBlock(0, 1, 0, 1));
		assertTrue(s2.getTray().containsBlock(1, 0, 1, 0));
		Sorter s3 = new Sorter("easy.goal");	
		assertTrue(s3.getTray().containsBlock(1, 1, 1, 1));
		Sorter s4 = new Sorter("big.block.1");
		assertEquals(3, s4.getTray().width());
		assertEquals(4, s4.getTray().height());
		assertTrue(s4.getTray().containsBlock(0, 0, 0, 1));
		assertTrue(s4.getTray().containsBlock(0, 2, 0, 3));
		assertTrue(s4.getTray().containsBlock(2, 0, 2, 1));
		assertTrue(s4.getTray().containsBlock(2, 2, 2, 3));
	}
}
