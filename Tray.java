import java.awt.*;
import java.util.*;

public class Tray {
	
	public boolean debug = true;

	private int trayWidth;
	private int trayHeight;

	private ArrayList<Block> goalBlocks = new ArrayList<Block>();
	private ArrayList<Block> blocks = new ArrayList<Block>();
	private boolean[] occupied; //ROW-MAJOR
	
	public Tray() {
	}
	
	public Tray(int width, int height) {
		trayWidth = width;
		trayHeight = height;

		occupied = new boolean[trayWidth*trayHeight];
	}
	
	public void addBlock (Block b) {
		if (b != null){


			//POPULATE MATRIX OF OCCUPIED SPACES
			for (int i = b.trow(); i <= b.brow(); i++) {
				for (int j = b.lcol(); j <= b.rcol(); j++) {
				
					if (occupied[j + i*trayWidth]) {
						throw new IllegalStateException("Cannot add block to occupied space.");
					}
					occupied[j + i*trayWidth] = true;
				}
			}

			blocks.add(b);
			

			//FOR DEBUGGING
			if (debug) {
				printOccupied();
			}
		}
	}
	
	public void addGoalBlock (Block b) {
		if (b != null) {
			goalBlocks.add(b);
		}
	}

	private void printOccupied() {

		for (int i = 0; i < trayHeight; i++) {
			for (int j = 0; j < trayWidth; j++) {
				if (occupied[j + i*trayWidth]) {
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
						occupied[j + i*trayWidth] = false;
						if (debug) {
						}
					}
			}

			for (int i = row; i <= row + b.height(); i++) {
				for (int j = col; j <= col + b.width(); j++) {
					occupied[j + i*trayWidth] = true;
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
			if (block.trow == topRow && block.lcol == leftCol && block.brow == bottomRow && block.rcol == rightCol) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsGoalBlock (int topRow, int leftCol, int bottomRow, int rightCol) {
		for (Block block: goalBlocks) {
			if (block.trow == topRow && block.lcol == leftCol && block.brow == bottomRow && block.rcol == rightCol) {
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

		for (Block block : blocks) {
			if (!b.equals(block)) {
				if (trowDest <= block.brow() && browDest >= block.trow()) {
					if (rcolDest >= block.lcol() && lcolDest <= block.rcol()) {
						
							//DEBUG
							if (debug) {
								System.out.println("Cockblocked by: " + block.trow() + ", " + block.lcol());
							}
							return false;
						}
				}
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
			Integer[][] visited = new Integer[blocks.size()][4];
			int index = 0;
			boolean moveExists = false;
			for (Block block : blocks) {
				if (block.trow < 0 || block.brow > trayHeight || block.lcol < 0 || block.rcol > trayWidth) {
					throw new IllegalStateException("Block not in board");
				}
				Integer[] coordinates = {Integer.valueOf(block.trow), Integer.valueOf(block.lcol), Integer.valueOf(block.brow), Integer.valueOf(block.rcol)};
				visited[index] = coordinates;
				for (Integer[] visitedCoordinates : visited) {
					System.out.println(Arrays.deepToString(visited));
					if (coordinates.equals(visitedCoordinates)) {
						throw new IllegalStateException("Two blocks are in the same location");   // doesn't catch if they overlap but aren't in same location -- how to check?
					}
				}
				for (int i = block.trow - 1; i < block.brow + 1; i++) {
					for (int j = block.lcol - 1; j < block.rcol + 1; j++) {
						if (validMove(block, i, j)) {
							moveExists = true;
						}
					}
				}
				index++;
			}
			if (!moveExists) {
				throw new IllegalStateException("No valid moves");
			}
		}
	}
	
	public ArrayList<Block> getBlocks() {
		return blocks;
	}
}
