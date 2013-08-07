import java.awt.*;
import java.util.*;

public class Tray {

  private ArrayList<Block> blocks;
	
	public Tray() {

		blocks = new ArrayList<Block>();
		
	}
	
	//Add a block to the tray.
	public void addBlock(Block b) {

		if (validMove(b, b.trow(), b.lcol())) {
			blocks.add(b);
		}
	}

	//Move a block.
	public void moveBlock(Block b, int trowDest, lcolDest) {

		if (validMove(b, trowDest, lcolDest)) {

		}
	} 

	//Returns true if the current Block can be moved to Point p, and false otherwise
	public boolean validMove (Block b, int trowDest, int lcolDest) {


		int browDest = trowDest + b.height();
		int rcolDest = lcolDest + b.width();

		for (Block block : blocks) {

			if (trowDest < block.brow() && browDest > block.trow()) {
				if (rcolDest >= block.lcol() && lcolDest <= block.rcol()) {
						return false;
					}
			}

		}

		return true;
	}

}
