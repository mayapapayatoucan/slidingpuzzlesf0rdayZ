import java.awt.*;
import java.util.*;

/* AUTHORS:
Zach Gottesman
Brad Slaughter
Maya Kulkarni
Winston Huang

*/

public class Tray {

	//debugging flags
	public boolean debug = false;
	public boolean debugPosMoves = false;
	public boolean debugValidMove = false;

	private int trayWidth;
	private int trayHeight;
	private Move prevMove; // reference to the single move that got you to the new config.
	private Tray prevTray; // reference to the previous tray.


	private ArrayList<Block> goalBlocks = new ArrayList<Block>();
	private ArrayList<Block> blocks = new ArrayList<Block>();

	public int hashCode() {

		int hash = 0;

		for (Block b : blocks) {
			for (int i = b.trow(); i <= b.brow(); i++) {
				for (int j = b.lcol(); j <= b.rcol(); j++) {
					hash += (2 << (j + i*trayWidth)); 
				}
			}
		}

		return hash;
	}

	public class Move {

		private int row, col, rowDest, colDest;

		public Move(int row, int col, int rowDest, int colDest) {
			this.row = row;
			this.col = col;
			this.rowDest = rowDest;
			this.colDest = colDest;
		}

		public String toString ( ) {
			Integer rowInt = new Integer(row);
			Integer colInt = new Integer(col);
			Integer rowDestInt = new Integer(rowDest);
			Integer colDestInt = new Integer(colDest);
			String moveString = rowInt.toString() + " " + colInt.toString() + " " + rowDestInt.toString() + " " + colDestInt.toString();
			return moveString;
		}
		
		public int row() {
			return row;
		}
		
		public int col() {
			return col;
		}
		
		public int rowDest() {
			return rowDest;
		}
		
		public int colDest() {
			return colDest;
		}

	}

	public Tray(int numRows, int numCols) {
		trayHeight = numRows;
		trayWidth = numCols;
	}

	private void setPrevMove (Move m) {
		prevMove = m;
	}

	private void setPrevTray (Tray t) {
		prevTray = t;
	}

	public void addBlock (Block b) {
		if (b != null){
			if (blocks.contains(b)) {
				throw new IllegalStateException("Block already in tray.");
			}
			if ((b.brow() >= trayHeight) || (b.rcol() >= trayWidth)) {
				throw new IllegalStateException("Block cannot be added outside tray dimensions.");
			}
			blocks.add(b);
		}
	}

	public void addGoalBlock (Block b) {
		if (goalBlocks.contains(b)) {
			throw new IllegalStateException("Goal block already in tray.");
		}
		if (b != null) {

			if ((b.brow() >= trayHeight) || (b.rcol() >= trayWidth)) {
				throw new IllegalStateException("Goal block cannot be added outside tray dimensions.");
			}
			
			goalBlocks.add(b);
			isOK();   // make sure adding the block isOK
		}
	}
	
	// for debugging purposes
	public void removeBlock (Block b) {
		blocks.remove(b);
	}
	
	public void removeGoalBlock (Block b) {
		goalBlocks.remove(b);
	}

	//prints a bit matrix of the tray where a 1 corresponds to an occupied space.
	public void printOccupied() {

		int[] occupied = new int[(trayWidth) * (trayHeight)];

		for (Block b : blocks) {
			//populate
			for (int i = b.trow(); i <= b.brow(); i++) {
				for (int j = b.lcol(); j <= b.rcol(); j++) {
					occupied[j + i*trayWidth] = 1;
				}
			}
		}

		for (int i = 0; i < trayHeight; i++) {
			for (int j = 0; j < trayWidth; j++) {
				System.out.print(occupied[j + i*trayWidth] + " ");
			}
			System.out.println("");
		}
		System.out.println("");

	}


	public void moveBlock (Block b, int row, int col) {
		if (debugValidMove) {
			System.out.println("moving from: " + b.trow() + " " + b.lcol() + " to: " + row + " " + col);
		}
		if (validMove(b, row, col)) {
			b.move(row, col);
		}
		isOK();
	}

	public boolean containsBlock (int topRow, int leftCol, int bottomRow, int rightCol) {
		for (Block block: blocks) {
			if (block.trow() == topRow && block.lcol() == leftCol && block.brow() == bottomRow && block.rcol() == rightCol) {
				return true;
			}
		}
		return false;
	}

	public Tray copy () {
		Tray copyTray = new Tray(height(), width());
		copyTray.goalBlocks = new ArrayList<Block> (goalBlocks);
		for (Block block : blocks) {
			copyTray.addBlock(new Block(block));
		}

		//maintain debugging environment
		copyTray.debug = debug;
		copyTray.debugPosMoves = debugPosMoves;
		copyTray.debugValidMove = debugValidMove;
		isOK();

		return copyTray;
	}

	public boolean correctConfig ( ) {
		for (Block block: goalBlocks) {
			if (!this.containsBlock(block.trow(), block.lcol(), block.brow(), block.rcol())) {
				return false;

			}
		}
		return true;
	}

	public boolean containsGoalBlock (int topRow, int leftCol, int bottomRow, int rightCol) {
		for (Block block: goalBlocks) {
			if (block.trow() == topRow && block.lcol() == leftCol && block.brow() == bottomRow && block.rcol() == rightCol) {
				return true;
			}
		}
		return false; 
	}

	public boolean validMove (Block b, int trowDest, int lcolDest) {

		if (!blocks.contains(b)){
			if (debugValidMove) {
				System.out.println("Invalid move: can only move blocks on the board");
			}
			return false;
		}
		if ((trowDest < 0) || (trowDest > trayHeight) || (lcolDest < 0) || (lcolDest > trayWidth)) {
			if (debugValidMove) {
				System.out.println("Invalid move: cannot move off the board");
			}
			return false;
		}

		if ((b.trow() != trowDest) && (b.lcol() != lcolDest)) {
			if (debugValidMove) {
				System.out.println("Invalid move: cannot move diagonally");
			}
			return false;
		}

		int browDest = trowDest + b.height();
		int rcolDest = lcolDest + b.width();

		if ((Math.abs(b.trow() - trowDest) > 1) || (Math.abs(b.lcol() - lcolDest) > 1) || (Math.abs(b.brow() - browDest) > 1) || (Math.abs(b.rcol() - rcolDest) > 1)) {
			if (debugValidMove) {
				System.out.println("Invalid move: can only move one space at a time");
			}
			return false;
		}

		for (Block block : blocks) {
			if (!b.equals(block) && block.overlapping(trowDest, lcolDest, browDest, rcolDest)) {
				if (debugValidMove) {
					System.out.println("Invalid move: space is already occupied");
				}
				return false;
			}
		}
		return true;
	}

	public void isOK() {
		if (debug) {

			boolean moveExists = false;

			if (blocks.size() < goalBlocks.size()) {
				throw new IllegalStateException("More goal blocks than blocks on the board");
			}

			Block[] occupied = new Block[trayWidth*trayHeight];

			for (Block block : blocks) {

				if (block.trow() < 0 || block.brow() > trayHeight || block.lcol() < 0 || block.rcol() > trayWidth) {
					throw new IllegalStateException("Block not in board");
				}

				for (Block otherBlock : blocks) {
					if (!(otherBlock == block) && block.overlapping(otherBlock.trow(), otherBlock.lcol(), otherBlock.brow(), otherBlock.rcol())) {
						throw new IllegalStateException("Two blocks are in the same location");
					}
				}

				for (int i = block.trow(); i <= block.brow(); i++) {
					for (int j = block.lcol(); j <= block.rcol(); j++) {
						if (occupied[j + i*trayWidth] != null) {
							Block curr = occupied[j + i*trayWidth];
							System.out.println("overlapping block: " + curr.trow() + " " + curr.lcol() + " " + curr.brow() + " " + curr.rcol());
							throw new IllegalStateException("Cannot add block to occupied space.");
						}
						occupied[j + i*trayWidth] = block;
					}
				}

				for (int i = block.trow() - 1; i < block.brow() + 1; i++) {
					for (int j = block.lcol() - 1; j < block.rcol() + 1; j++) {
						if (validMove(block, i, j)) {
							moveExists = true;
						}
					}
				}
			}

			if (!moveExists) {
				throw new IllegalStateException("No valid moves");
			}
		}
	}

	public ArrayList<Tray> posMoves() {
		ArrayList<Tray> babies = new ArrayList<Tray>();
		Block[] occupied = new Block[(trayWidth) * (trayHeight)];

		for (Block b : blocks) {
			//populate
			for (int i = b.trow(); i <= b.brow(); i++) {
				for (int j = b.lcol(); j <= b.rcol(); j++) {
					occupied[j + i*trayWidth] = b;
				}
			}
		}

		for (int i = 0; i < occupied.length; i++) {

			if (occupied[i] == null) {

				int row = i/trayWidth;
				int col = i%trayWidth;

				if (debugPosMoves) {
					System.out.println("EMPTY SPACE AT " + row + ", " + col);
				}

				//Check above
				if (i - trayWidth >= 0) {
					Block b = occupied[i - trayWidth];
					if (b != null && validMove(b, row - b.height(), col)) {
						Tray t = this.copy();
						t.setPrevTray(this);
						Move pMove = new Move(b.trow(), b.lcol(), row - b.height(), col);
						t.setPrevMove(pMove);
						t.moveBlock(t.blocks.get(blocks.indexOf(occupied[i - trayWidth])),
									row - b.height(), col);
						babies.add(t);	
						if (debugPosMoves) {
							System.out.println("MOVE DOWN TO ROW " + row + " COL " + col);
						}

					}
				}

				//Check below
				if (i + trayWidth < occupied.length) {
					if (validMove(occupied[i + trayWidth], row, col)) {
						Tray t = this.copy();
						t.setPrevTray(this);
						Move pMove = new Move(occupied[i + trayWidth].trow(), occupied[i + trayWidth].lcol(), row, col);
						t.setPrevMove(pMove);
						t.moveBlock(t.blocks.get(blocks.indexOf(occupied[i + trayWidth])), row, col);	
						babies.add(t);	
						if (debugPosMoves){
							System.out.println("MOVE UP TO ROW " + row + " COL " + col);
						}
					}
				}		

				//Check if left
				if (i%trayWidth != 0)	{
					Block b = occupied[i-1];
					if (b != null && validMove(b, row, col - b.width())) {
						Tray t = this.copy();
						t.setPrevTray(this);
						Move pMove = new Move(b.trow(), b.lcol(), row, col - b.width());
						t.setPrevMove(pMove);
						t.moveBlock(t.blocks.get(blocks.indexOf(occupied[i - 1])), row, col - b.width());	
						babies.add(t);	

						if (debugPosMoves) {
							System.out.println("MOVE RIGHT TO ROW" + row + " COL " + col);
						}
					}


				}

				//Check if right
				if (i%trayWidth != trayWidth -1) {
					if (validMove(occupied[i + 1], row, col)) {
						Tray t = this.copy();
						t.setPrevTray(this);
						Move pMove = new Move(occupied[i + 1].trow(), occupied[i + 1].lcol(), row, col);
						t.setPrevMove(pMove);
						t.moveBlock(t.blocks.get(blocks.indexOf(occupied[i + 1])), row, col);	
						babies.add(t);	
						if (debugPosMoves) {
							System.out.println("MOVE LEFT TO ROW" + row + " COL " + col);
						}	
					}

				}	
			}
		}

		if (debugPosMoves) {
			System.out.println("All of the possible moves are listed below: ");
			for (Tray t : babies) { 
				t.printOccupied();
			}
		}

		return babies;
	}

	// used when giving answer for solved puzzle
	public void printMoves ( ) {
		Stack<Move> moveStack = new Stack<Move>();
		Tray curr = this;
		while (curr != null){
			moveStack.push(curr.prevMove);
			curr = curr.prevTray;
		}
		moveStack.pop();
		while (!moveStack.empty()) {
			Move m;
			m = moveStack.pop();
			System.out.println(m);
		}
	}
	
	//Returns true if this and tray to compare have the same configuration.
	public boolean equals (Object o) {

		Tray t = (Tray) o;

		if (debug) {
			System.out.println("Current tray:");
			this.printOccupied();
			System.out.println("Compared to:");
			t.printOccupied();
		}

		if ((height() != t.height()) || (width() != t.width())) {   // trays are different sizes
			return false;
		}

		boolean inOther;   // true if a block in the current tray is in the tray to be compared

		for (Block block : blocks) {
			inOther = false;
			for (Block otherBlock : t.getBlocks()) {
				if (block.equals(otherBlock)) {
					inOther = true;
				}
			}
			if (!inOther) {
				return false;   // block in current tray isn't in tray to be compared
			}
		}
		for (Block block : t.getBlocks()) {
			inOther = false;
			for (Block otherBlock : blocks) {
				if (block.equals(otherBlock)) {
					inOther = true;
				}
			}
			if (!inOther) {
				return false;   // block in tray to be compared isn't in current tray
			}
		}
		return true;
	}

	public int width() {
		return trayWidth;
	}

	public int height() {
		return trayHeight;
	}

	public ArrayList<Block> getBlocks() {
		return blocks;
	}
	
	public ArrayList<Block> getGoalBlocks() {
		return goalBlocks;
	}

	public Move getPrevMove() {
		return prevMove;
	}

	public Tray getPrevTray() {
		return prevTray;
	}
}
