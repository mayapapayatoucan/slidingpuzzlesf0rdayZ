import static org.junit.Assert.*;

import java.awt.*;

import org.junit.Test;


public class TrayTest {
	
	@Test
	public void testIsOK() {
		Tray t1 = new Tray(1, 1);
		Block b = new Block("0 0 0 0");
		t1.addBlock(b);
		t1.addBlock(b);
		try {
			t1.isOK();   // two overlapping blocks
			fail();
		} catch (IllegalStateException e) {
			
		}
		Tray t2 = new Tray(1, 1);
		Block b1 = new Block("0 0 0 0");
		Block b2 = new Block("0 1 0 1");
		Block b3 = new Block("1 0 1 0");
		Block b4 = new Block("1 1 1 1");
		t1.addBlock(b1);
		t1.addBlock(b2);
		t1.addBlock(b3);
		t1.addBlock(b4);
		try {
			t1.isOK();   // nowhere to move
			fail();
		} catch (IllegalStateException e) {
			
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
		assertFalse(t1.validMove(b1, b2.trow, b2.lcol));   // can't move up when already occupied
		assertFalse(t1.validMove(b1, b3.trow, b3.lcol));   // can't move left when already occupied
		assertFalse(t1.validMove(b1, b4.trow, b4.lcol));   // can't move down when already occupied
		assertFalse(t1.validMove(b1, b5.trow, b5.lcol));   // can't move right when already occupied
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
		Block b7 = new Block("4 3 4 3");
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

}
