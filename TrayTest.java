import static org.junit.Assert.*;

import java.awt.*;
import java.util.*;

import org.junit.Test;


public class TrayTest {
	

/*	@Test 
	public void testIsOK() {
		Tray t1 = new Tray(2, 2);
		Block b = new Block("0 0 0 0");
		t1.addBlock(b);
		assertFalse(testIsOKHelper(t1));
		testAddBlockHelper(t1, b);
		Tray t2 = new Tray(2, 2);
		Block b1 = new Block("0 0 0 0");
		Block b2 = new Block("0 1 0 1");
		Block b3 = new Block("1 0 1 0");
		Block b4 = new Block("1 1 1 1");
		t2.addBlock(b1);
		t2.addBlock(b2);
		t2.addBlock(b3);
		t2.addBlock(b4);
		assertFalse(testIsOKHelper(t2));
		Tray t3 = new Tray(5, 5);
		Block center = new Block("1 1 2 2");
		Block b5 = new Block("0 1 1 1");   // top overlap
		Block b6 = new Block("1 1 1 2");   // right overlap
		Block b7 = new Block("2 0 2 1");   // left overlap
		Block b8 = new Block("2 2 3 2");   // bottom overlap
		t3.addBlock(center);
		testAddBlockHelper(t3, b5);
		testAddBlockHelper(t3, b6);
		testAddBlockHelper(t3, b7);
		testAddBlockHelper(t3, b8);
		assertTrue(testIsOKHelper(t3));
	} */

	public void testAddBlockHelper (Tray t, Block b) {
		try {
			t.addBlock(b);
			fail();
		} catch (IllegalStateException e) {

		}
	}

	public boolean testIsOKHelper (Tray t) {
		try {
			t.isOK();
			return false;
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
			return true;
		}
	}

	@Test
	public void testContainsBlock() {
		Tray t1 = new Tray(4, 3);
		Block b1 = new Block("0 0 0 0");
		t1.addBlock(b1);
		assertTrue(t1.containsBlock(0, 0, 0, 0));
		assertFalse(t1.containsBlock(1, 1, 1, 1));
	}

	@Test
	public void testValidMove() {
		Tray t1 = new Tray(4, 3);
		Block b1 = new Block("1 1 1 1");
		Block b2 = new Block("0 1 0 1");
		Block b3 = new Block("1 0 1 0");
		Block b4 = new Block("2 1 2 1");
		Block b5 = new Block("1 2 1 2");
		t1.addBlock(b1);
		t1.addBlock(b2);
		t1.addBlock(b3);
		t1.addBlock(b4);
		t1.addBlock(b5);
		

		
		
		
		assertFalse(t1.validMove(b1, b2.trow(), b2.lcol()));   // can't move up when already occupied
		assertFalse(t1.validMove(b1, b3.trow(), b3.lcol()));   // can't move left when already occupied
		assertFalse(t1.validMove(b1, b4.trow(), b4.lcol()));   // can't move down when already occupied
		assertFalse(t1.validMove(b1, b5.trow(), b5.lcol()));   // can't move right when already occupied
		assertFalse(t1.validMove(b1, 0, 2));   // can't move diagonally (up-right)
		assertFalse(t1.validMove(b1, 0, 0));   // can't move diagonally (up-left)
		assertFalse(t1.validMove(b1, 2, 0));   // can't move diagonally (down-left)
		assertFalse(t1.validMove(b1, 2, 2));   // can't move diagonally (down-right)
		Tray t2 = new Tray(4, 3);
		Block b6 = new Block("0 0 1 0");
		t2.addBlock(b6);
		assertTrue(t2.validMove(b6, 0, 1));   // can move into adjacent empty space
		assertFalse(t2.validMove(b6, -1, 0));  // can't move off the board
		assertFalse(t2.validMove(b6, 0, -1));  // can't move off the board
		Block b7 = new Block("3 2 3 2");
		t2.addBlock(b7);
		assertFalse(t2.validMove(b7, 5, 3));  // can't move off the board
		assertFalse(t2.validMove(b7, 4, 4));  // can't move off the board
		Block b8 = new Block("3 0 3 0");
		t2.addBlock(b8);
		assertFalse(t2.validMove(b6, 2, 0));  // not enough room
		Tray t3 = new Tray(4, 3);
		Block b9 = new Block("0 0 1 0");
		Block b10 = new Block("1 1 2 1");
		Block b11 = new Block("0 2 0 2");
		Block b12 = null; // null block object for testing purposes.
		t3.addBlock(b9);
		t3.addBlock(b10);
		t3.addBlock(b11);
		t3.addBlock(b12); // should not add b12 to the ArrayList since b12 is null.
		assertTrue(t3.validMove(b11, 0, 1)); // should be true since there is just enough space for block b11 to move into it.
		assertTrue(t3.validMove(b10, 0, 1)); // should be true still since there is enough room for b10.
		assertFalse(t3.validMove(b9, 0, 1)); // should be False since there is not enough rookm in column 1 for b9 to move over into the specified space.
		assertFalse(t3.validMove(b12, 2, 2)); // should be false since b12 is not in the blocks arraylist because its null.

	}
	
	@Test
	public void testCopy() {

		Tray t1 = new Tray(4, 3);
		Block b1 = new Block("1 1 1 1");
		Block b2 = new Block("0 1 0 1");
		Block b3 = new Block("1 0 1 0");
		Block b4 = new Block("2 1 2 1");
		Block b5 = new Block("1 2 1 2");
		t1.addBlock(b1);
		t1.addBlock(b2);
		t1.addBlock(b3);
		t1.addBlock(b4);
		t1.addBlock(b5);
		
		Tray t2 = t1.copy();
		assertEquals(t1.width(),t2.width());
		assertEquals(t1.height(),t2.height());
		
		for(Block block: t1.getBlocks() ) {
			assertTrue(t2.containsBlock(block.trow(), block.lcol(), block.brow(), block.rcol()));
			
		}
		
		System.out.println("Printing matrices.");
		t1.printOccupied();
		t2.printOccupied();
		
	
	}

@Test
	public void testBabies() {

		Tray t1 = new Tray(4, 3);
		Block b1 = new Block("1 1 1 1");
		Block b2 = new Block("0 1 0 1");
		Block b3 = new Block("1 0 1 0");
		Block b4 = new Block("2 1 2 1");
		Block b5 = new Block("1 2 1 2");
		t1.addBlock(b1);
		t1.addBlock(b2);
		t1.addBlock(b3);
		t1.addBlock(b4);
		t1.addBlock(b5);

		t1.printOccupied();
		ArrayList<Tray> babies = t1.posMoves();
		for(Tray b : babies) {
			b.printOccupied();
		}
		
		Tray t2 = new Tray(5, 4);
		Block b6 = new Block("0 0 1 0");
		Block b7 = new Block("2 1 3 2");
		Block b8 = new Block("4 0 4 1");
		t2.addBlock(b6);
		t2.addBlock(b7);
		t2.addBlock(b8);
		t2.printOccupied();
		ArrayList<Tray> moreBabies = t2.posMoves();
		System.out.println("Moves are:");
		for (Tray von: moreBabies) {
			von.printOccupied();
		}

	}

@Test 
	public void testGoalConfig() {
	
	Tray t1 = new Tray(4, 3);
	Block b1 = new Block("1 1 1 1");
	Block b2 = new Block("0 1 0 1");
	Block b3 = new Block("1 0 1 0");
	Block b4 = new Block("2 1 2 1");
	Block b5 = new Block("1 2 1 2");
	t1.addBlock(b1);
	t1.addBlock(b2);
	t1.addBlock(b3);
	t1.addBlock(b4);
	t1.addBlock(b5);
	
	Block g1 = new Block("1 1 1 1");
	Block g2 = new Block("2 1 2 1");
	
	t1.addGoalBlock(g1);
	t1.addGoalBlock(g2);
	
	assertTrue(t1.correctConfig());
	
	Tray t2 = new Tray(5, 4);
	Block b6 = new Block("0 0 1 0");
	Block b7 = new Block("2 1 3 2");
	Block b8 = new Block("4 0 4 1");
	t2.addBlock(b6);
	t2.addBlock(b7);
	t2.addBlock(b8);
	
	Block g3 = new Block("1 1 1 1");
	t2.addGoalBlock(g3);
	
	assertFalse(t2.correctConfig());
	
	Tray t3 = new Tray(5, 4);
	Block b9 = new Block("0 0 1 0");
	Block b10 = new Block("2 1 3 2");
	Block b11 = new Block("4 0 4 1");
	t3.addBlock(b9);
	t3.addBlock(b10);
	t3.addBlock(b11);
	
	Block g5 = new Block("1 1 1 1");
	Block g6 = new Block("0 0 1 0");
	Block g7 = new Block("4 0 4 1");
	
	t3.addGoalBlock(g5);
	t3.addGoalBlock(g6);
	t3.addGoalBlock(g7);
	
	assertFalse(t3.correctConfig());
	
	
	
	
	
}

}
