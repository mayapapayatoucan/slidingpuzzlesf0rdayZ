import java.awt.*;
import java.util.*;

public class Tray {

	public boolean debug = false;

	private int trayWidth;
	private int trayHeight;
	public Move prevMove; // pointer to the single move that got you to the new config.
	public Tray prevTray; // pointer to the previous tray.


	private ArrayList<Block> goalBlocks = new ArrayList<Block>();
	private ArrayList<Block> blocks = new ArrayList<Block>();
	private Block[] occupied; //ROW-MAJOR

	public class Move {

		int row;
		int col;
		int rowDest;
		int colDest;

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
			String moveString;
			moveString = rowInt.toString() + " " + colInt.toString() + " " + rowDestInt.toString() + " " + colDestInt.toString();
			return moveString;
		}

	}

	public Tray() {
	}

	public Tray(int numRows, int numCols) {

		trayHeight = numRows;
		trayWidth = numCols;

		occupied = new Block[(trayWidth) * (trayHeight)];
	}

	// public Tray(int numRows, int numCols, Move m, Tray last) { //use as constructor for children trays.
	// 	trayHeight = numRows;
	// 	trayWidth = numCols;
	// 	prevMove = m;
	// 	prevTray = last;
	// 	last.goalBlocks = goalBlocks;
	// 	for (Block block : last.blocks) {
	// 		addBlock(new Block(block));
	// 	}
	//}

	private void setPrevMove (Move m) {
		prevMove = m;
	}

	private void setPrevTray (Tray t) {
		prevTray = t;
		//System.out.println("SetPrevTray method: ");
		//System.out.println(prevTray);
	}


	public void addBlock (Block b) {
		if (b != null){


			//POPULATE MATRIX OF OCCUPIED SPACES
			for (int i = b.trow(); i <= b.brow(); i++) {
				for (int j = b.lcol(); j <= b.rcol(); j++) {
					//System.out.println("i: " + i);
					//System.out.println("j: " + j);
					//System.out.println("index: " + (j + i*trayWidth));
					//System.out.println("occupied.length: " + occupied.length);
					if (occupied[j + i*trayWidth] != null) {
						throw new IllegalStateException("Cannot add block to occupied space.");
					}
					occupied[j + i*trayWidth] = b;
				}
			}

			blocks.add(b);


			//FOR DEBUGGING
			if (debug) {
				printOccupied();
			}
		}
	}

	public Block[] getOccupied ( ) {
		return occupied;
	}

	public void addGoalBlock (Block b) {
		if (b != null) {
			goalBlocks.add(b);
		}
	}

	public void printOccupied() {

		for (int i = 0; i < trayHeight; i++) {
			for (int j = 0; j < trayWidth; j++) {
				if (occupied[j + i*trayWidth] != null) {
					System.out.print("1 ");
				}
				else {
					System.out.print("0 ");
				}
			}
			System.out.println("");
		}
		System.out.println("");

	}

	public void moveBlock (Block b, int row, int col) {
		if (validMove(b, row, col)) {

			for (int i = b.trow(); i <= b.brow(); i++) {
					for (int j = b.lcol(); j <= b.rcol(); j++) {
						occupied[j + i*trayWidth] = null;
						if (debug) {
						}
					}
			}

			for (int i = row; i <= row + b.height(); i++) {
				for (int j = col; j <= col + b.width(); j++) {
					occupied[j + i*trayWidth] = b;
						if (debug) {
						}
				}
			}

			b.move(row, col);

			if (debug) {
				printOccupied();
			}
		}
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
		//copyTray.setPrevTray(this); // sets the previous tray
		copyTray.goalBlocks = new ArrayList<Block> (goalBlocks);
		for (Block block : blocks) {
			copyTray.addBlock(new Block(block));
		}
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
			return false;   // can only move blocks on the board
		}
		if ((trowDest < 0) || (trowDest > trayHeight) || (lcolDest < 0) || (lcolDest > trayWidth)) {
			return false;   // cannot move off the board
		}

		if ((b.trow() != trowDest) && (b.lcol() != lcolDest)) {
			return false; //cannot move diagonally
		}

		int browDest = trowDest + b.height();
		int rcolDest = lcolDest + b.width();

		if ((Math.abs(b.trow() - trowDest) > 1) || (Math.abs(b.lcol() - lcolDest) > 1) || (Math.abs(b.brow() - browDest) > 1) || (Math.abs(b.rcol() - rcolDest) > 1)) {
			return false; // can only move one space at a time
		}

		for (Block block : blocks) {
			if (!b.equals(block) && block.overlapping(trowDest, lcolDest, browDest, rcolDest)) {
				return false;
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

	public void isOK() {
		if (debug) {
			//Integer[][] visited = new Integer[blocks.size()][4];
			//int index = 0;
			boolean moveExists = false;
			if (blocks.size() < goalBlocks.size()) {
				throw new IllegalStateException("More goal blocks than blocks on the board");
			}
			for (Block block : blocks) {
				if (block.trow() < 0 || block.brow() > trayHeight || block.lcol() < 0 || block.rcol() > trayWidth) {
					throw new IllegalStateException("Block not in board");
				}
				if (!Arrays.asList(occupied).contains(block)) {
					throw new IllegalStateException("Block incorrectly added");
				}
				//Integer[] coordinates = {Integer.valueOf(block.trow()), Integer.valueOf(block.lcol()), Integer.valueOf(block.brow()), Integer.valueOf(block.rcol())};
				//visited[index] = coordinates;
				for (Block otherBlock : blocks) {
					//System.out.println("block: " + block);
					//System.out.println("otherBlock: " + otherBlock);
					if (!(otherBlock == block) && block.overlapping(otherBlock.trow(), otherBlock.lcol(), otherBlock.brow(), otherBlock.rcol())) {
						throw new IllegalStateException("Two blocks are in the same location");
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
			for (Block block : occupied) {
				//System.out.println(blocks);
				if (block != null) {
					if (!blocks.contains(block)) {
						throw new IllegalStateException("Block incorrectly added");
					}
				}
			}
			if (!moveExists) {
				throw new IllegalStateException("No valid moves");
			}
		}
	}

	public ArrayList<Block> getBlocks() {
		return blocks;
	}



	public ArrayList<Tray> posMoves() {
		ArrayList<Tray> babies = new ArrayList<Tray>();


		for (int i = 0; i < occupied.length; i++) {
			if (occupied[i] == null) {

				//System.out.println("OCCUPIED AT INDEX " + i + " IS NULL");

				int row = i/trayWidth;
				int col = i%trayWidth;

				//CHECK ABOVE
				if (i - trayWidth >= 0) {
					Block b = occupied[i - trayWidth];
					if (b != null && validMove(b, row - b.height(), col)){
						Tray t = this.copy();
						
						t.setPrevTray(this);

						Move pMove = new Move(b.trow(), b.lcol(), row - b.height(), col);
						t.setPrevMove(pMove);
						
						//System.out.println("Previous Tray: ");
						//this.printOccupied();

						//System.out.println("BEFORE MOVING COPY TRAY: ");
						//t.printOccupied();
						t.moveBlock(t.blocks.get(blocks.indexOf(occupied[i - trayWidth])),
									row - b.height(), col);
						//System.out.println("THE MORNING AFTER");
						//t.printOccupied();	
						babies.add(t);	

						//System.out.println("Blocks index of " + blocks.indexOf(occupied[i - trayWidth]));

						//System.out.println("MOVE DOWN TO ROW " + row + " COL " + col);

					}
				}

				//Check below
				if (i + trayWidth < occupied.length) {
					if (validMove(occupied[i + trayWidth], row, col)){
						
						Tray t = this.copy();
						
						t.setPrevTray(this);
						Move pMove = new Move(occupied[i + trayWidth].trow(), occupied[i + trayWidth].lcol(), row, col);
						t.setPrevMove(pMove);
						
						//System.out.println("Previous Tray: ");
						//this.printOccupied();

						t.moveBlock(t.blocks.get(blocks.indexOf(occupied[i + trayWidth])), row, col);	
						babies.add(t);	

					//System.out.println("MOVE UP TO ROW " + row + " COL " + col);
					}
				}		

				//Check if left
				if (i%trayWidth != 0)	{
					Block b = occupied[i-1];
					if (b != null && validMove(b, row, col - b.width())){
						
						Tray t = this.copy();
						
						t.setPrevTray(this);
						Move pMove = new Move(b.trow(), b.lcol(), row, col - b.width());
						t.setPrevMove(pMove);
						
						//System.out.println("Previous Tray: ");
						//this.printOccupied();

						t.moveBlock(t.blocks.get(blocks.indexOf(occupied[i - 1])), row, col - b.width());	
						babies.add(t);	

						//System.out.println("MOVE RIGHT TO ROW" + row + " COL " + col);
					}


				}

				//Check if right
				if (i%trayWidth != trayWidth -1)	{
					if (validMove(occupied[i + 1], row, col)) {

						Tray t = this.copy();
						
						t.setPrevTray(this);
						Move pMove = new Move(occupied[i + 1].trow(), occupied[i + 1].lcol(), row, col);
						t.setPrevMove(pMove);
						
						//System.out.println("Previous Tray: ");
						//this.printOccupied();

						t.moveBlock(t.blocks.get(blocks.indexOf(occupied[i + 1])), row, col);	
						babies.add(t);	

						//System.out.println("MOVE LEFT TO ROW" + row + " COL " + col);
					}

				}	
			}
		}

		//maybe remove duplicates ??
			return babies;
	}

	// used to give answer for solved puzzle
	public void printMoves ( ) {
		Stack<Move> moveStack = new Stack<Move>();
		Tray curr = this;
		while (curr != null){
			moveStack.push(curr.prevMove);
			curr = curr.prevTray;
		}
		while (!moveStack.empty()) {
			Move m;
			m = moveStack.pop();
			System.out.println(m);
		}
	}

	public Move getPrevMove ( ) {
		return prevMove;
	}

	public Tray getPrevTray ( ) {
		return prevTray;
	}

	public boolean equals (Tray t) {
	if ((height() != t.height()) || (width() != t.width())) {
		return false;
	}
	boolean inOther;
	for (Block block : blocks) {
		inOther = false;
		for (Block otherBlock : t.getBlocks()) {
			if (block.equals(otherBlock)) {
				inOther = true;
			}
		}
		if (!inOther) {
			return false;
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
			return false;
		}
	}
	return true;
}


}
