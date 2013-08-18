import static org.junit.Assert.*;

import org.junit.Test;


public class SolverTest {

	@Test
	public void testParse() {
		Solver s = new Solver("test1.txt");   // not using contents of file, just needed for constructor
		assertTrue(s.isBlock("0 0 0 0"));
		assertFalse(s.isBlock("0000"));
		assertFalse(s.isBlock(""));
		assertFalse(s.isBlock("a b c d"));
		assertFalse(s.isBlock("0 0 0"));
		assertFalse(s.isBlock("0 0 0 0 0"));
		s.makeTray("2 2");
		s.Parse("0 0 0 0");
	}
	
	@Test
	public void testInput() {
		Solver s1 = new Solver("test1.txt");   // Figure 1 in the project documentation
		assertEquals(5, s1.getTray().height());
		assertEquals(4, s1.getTray().width());
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
		Solver s2 = new Solver("easy", "easy.goal");
		assertEquals(2, s2.getTray().height());
		assertEquals(2, s2.getTray().width());
		assertTrue(s2.getTray().containsBlock(0, 0, 0, 0));
		assertTrue(s2.getTray().containsBlock(0, 1, 0, 1));
		assertTrue(s2.getTray().containsBlock(1, 0, 1, 0));
		assertTrue(s2.getTray().containsGoalBlock(1, 1, 1, 1));
		Solver s3 = new Solver("big.block.1");
		assertEquals(3, s3.getTray().height());
		assertEquals(4, s3.getTray().width());
		assertTrue(s3.getTray().containsBlock(0, 0, 0, 1));
		assertTrue(s3.getTray().containsBlock(0, 2, 0, 3));
		assertTrue(s3.getTray().containsBlock(2, 0, 2, 1));
		assertTrue(s3.getTray().containsBlock(2, 2, 2, 3));
	}
}
