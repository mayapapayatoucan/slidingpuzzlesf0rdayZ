import java.awt.*;
import java.util.*;

public class Tray {
	
	private boolean debug = true;
	

	private ArrayList<Block> blocks = new ArrayList<Block>();
	private int trayWidth;
	private int trayHeight;
	
	public Tray(int width, int height) {
		trayWidth = width;
		trayHeight = height;
	}
	
	public void addBlock (Block b) {
		if (b != null){
			blocks.add(b);
		}
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
}
