import static org.junit.Assert.*;

import java.awt.*;
import java.util.*;

import org.junit.Test;


public class TrayTest {
	
	// tests addBlock and addGoalBlock
	@Test
	public void testAdd () {
		Tray t1 = new Tray(5, 5);
		Block b1 = new Block("1 1 2 2");
		t1.addBlock(b1);
		assertTrue(t1.getBlocks().contains(b1));
		assertTrue(addBlockException(t1, b1));   // can't add same block twice
		for (Block b : t1.getBlocks()) {
			System.out.println("block: " + b.trow() + " " + b.lcol() + " " + b.brow() + " " + b.rcol());
		}
		if (t1.debug) {
			Block b2 = new Block("0 1 1 1");
			System.out.println("problem is here:");
			assertTrue(addBlockException(t1, b2));   // top left corner of b1 is already occupied
			t1.removeBlock(b2);
			Block b3 = new Block("2 1 3 1");
			assertTrue(addBlockException(t1, b3));   // bottom left corner of b1 is already occupied
			t1.removeBlock(b3);
			Block b4 = new Block("1 2 1 3");
			assertTrue(addBlockException(t1, b4));   // top right corner of b1 is already occupied
			t1.removeBlock(b4);
			Block b5 = new Block("2 2 2 3");
			assertTrue(addBlockException(t1, b5));   // bottom right corner of b1 is already occupied
			t1.removeBlock(b5);
		}
		Block b6 = new Block("10 10 10 10");
		assertTrue(addBlockException(t1, b6));   // block location isn't on the tray
		t1.removeGoalBlock(b6);
		Block b7 = new Block("4 4 4 4");
		t1.addBlock(b7);
		assertTrue(t1.getBlocks().contains(b1));
		assertTrue(t1.getBlocks().contains(b7));
		Block g1 = new Block("0 0 1 1");
		t1.addGoalBlock(g1);
		assertTrue(t1.getGoalBlocks().contains(g1));
		assertTrue(addGoalBlockException(t1, g1));   // can't add same goal block twice
		Block g2 = new Block("2 2 3 3");
		t1.addGoalBlock(g2);   // goal block can be currently present on tray
		assertTrue(t1.getGoalBlocks().contains(g1));
		assertTrue(t1.getGoalBlocks().contains(g2));
		Block g3 = new Block("10 10 10 10");
		assertTrue(addGoalBlockException(t1, g3));   // block location isn't on the tray	
		t1.removeGoalBlock(g3);
	}
	
	@Test 
	public void testIsOK() {
		Tray t1 = new Tray(2, 2);
		if (t1.debug) {
			Block b = new Block("0 0 0 0");
			t1.addBlock(b);
			assertFalse(isOKException(t1));
			assertTrue(addBlockException(t1, b));
		}
		Tray t2 = new Tray(2, 2);
		if (t2.debug) {
			Block b1 = new Block("0 0 0 0");
			Block b2 = new Block("0 1 0 1");
			Block b3 = new Block("1 0 1 0");
			Block b4 = new Block("1 1 1 1");
			t2.addBlock(b1);
			t2.addBlock(b2);
			t2.addBlock(b3);
			t2.addBlock(b4);
			assertFalse(isOKException(t2));
		}
		Tray t3 = new Tray(5, 5);
		if (t3.debug) {
			Block center = new Block("1 1 2 2");
			Block b5 = new Block("0 1 1 1");   // top overlap
			Block b6 = new Block("1 1 1 2");   // right overlap
			Block b7 = new Block("2 0 2 1");   // left overlap
			Block b8 = new Block("2 2 3 2");   // bottom overlap
			t3.addBlock(center);
			assertTrue(addBlockException(t3, b5));
			assertTrue(addBlockException(t3, b6));
			assertTrue(addBlockException(t3, b7));
			assertTrue(addBlockException(t3, b8));
			assertTrue(isOKException(t3));
		}
	}

	public boolean addBlockException (Tray t, Block b) {
		try {
			t.addBlock(b);
			return false;
		} catch (IllegalStateException e) {
			return true;
		}
	}
	
	public boolean addGoalBlockException (Tray t, Block b) {
		try {
			t.addGoalBlock(b);
			return false;
		} catch (IllegalStateException e) {
			return true;
		}
	}

	public boolean isOKException (Tray t) {
		try {
			t.isOK();
			return false;
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
			return true;
		}
	}
	
	// tests containsBlock and containsGoalBlock
	@Test
	public void testContains() {
		Tray t1 = new Tray(4, 3);
		Block b1 = new Block("0 0 0 0");
		t1.addBlock(b1);
		assertTrue(t1.containsBlock(0, 0, 0, 0));
		assertFalse(t1.containsBlock(1, 1, 1, 1));
		assertFalse(t1.containsBlock(10, 10, 10, 10));   // not on the tray
		Block b2 = new Block("1 0 1 0");
		t1.addGoalBlock(b2);
		assertTrue(t1.containsGoalBlock(1, 0, 1, 0));
		assertFalse(t1.containsGoalBlock(0, 0, 0, 0));
		assertFalse(t1.containsGoalBlock(10, 10, 10, 10));
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
		assertTrue(t1.correctConfig());   // no goal blocks, so any configuration is correct
		
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

		// test moveBlock method to see if it does the valid moves
		t2.moveBlock(b6, 0, 1);
		assertTrue(t2.containsBlock(0, 1, 1, 1));
		assertFalse(t2.containsBlock(0, 0, 1, 0));
		t3.moveBlock(b10, 0, 1);
		assertTrue(t3.containsBlock(0, 1, 1, 1));
		assertFalse(t3.containsBlock(1, 1, 2, 1));
		t3.moveBlock(b10, 1, 1);   // move back to original location
		assertTrue(t3.containsBlock(1, 1, 2, 1));
		assertFalse(t3.containsBlock(0, 1, 1, 1));
		t3.moveBlock(b11, 0, 1);   // test next block
		assertTrue(t3.containsBlock(0, 1, 0, 1));
		assertFalse(t3.containsBlock(0, 2, 0, 2));
	}
	
/*	@Test
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
		if (t1.debug || t2.debug) {
			System.out.println("Printing matrices.");
			t1.printOccupied();
			t2.printOccupied();
		}
	} */

	// no assertions, see print output
	@Test
	public void testPosMoves() {
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

		//t1.printOccupied();
		ArrayList<Tray> babies = t1.posMoves();
		for(Tray b : babies) {
			//b.printOccupied();
		}
		
		Tray t2 = new Tray(5, 4);
		Block b6 = new Block("0 0 1 0");
		Block b7 = new Block("2 1 3 2");
		Block b8 = new Block("4 0 4 1");
		t2.addBlock(b6);
		t2.addBlock(b7);
		t2.addBlock(b8);
		//t2.printOccupied();
		ArrayList<Tray> moreBabies = t2.posMoves();
		System.out.println("Moves are:");
		for (Tray t3: moreBabies) {
			//t3.printOccupied();
		}

	}
	
	
	// test methods affecting prevMove and prevTray
	@Test
	public void testPrev () {
		Tray t1 = new Tray(5, 5);
		Block b1 = new Block("0 0 0 0");
		t1.addBlock(b1);
		assertTrue(t1.getPrevMove() == null);
		assertTrue(t1.getPrevTray() == null);
		ArrayList<Tray> babies = t1.posMoves();
		Tray t1a = babies.get(0);
		assertTrue(t1a.getPrevTray().equals(t1));
		assertEquals(0, t1a.getPrevMove().rowDest());
		assertEquals(1, t1a.getPrevMove().colDest());
		Tray t1b = babies.get(1);
		assertTrue(t1b.getPrevTray().equals(t1));
		assertEquals(1, t1b.getPrevMove().rowDest());
		assertEquals(0, t1b.getPrevMove().colDest());
		try {
			babies.get(2);
			fail();   // only two possible moves
		} catch (IndexOutOfBoundsException e) {
			
		}
	}

}
