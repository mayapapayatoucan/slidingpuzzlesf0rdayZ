import java.awt.*;
import java.util.*;

public class Tray {

  private ArrayList<Block> blocks;
	
	public Tray() {
		
	}
	
	/* Returns true if the current Block can be moved to Point p, and false otherwise
	 */
	public boolean validMove (Block currentBlock, Point p) {
		if (direction(currentBlock.getTopLeft(), p).equals("up")) {
			for (Block block: blocks) {
				if ((block.getTopLeft().x - block.height()) == currentBlock.getTopLeft().x) {
					return false;
				}
			}
		}
		if (direction(currentBlock.getTopLeft(), p).equals("down")) {
			for (Block block: blocks) {
				if (block.getTopLeft().x == (currentBlock.getTopLeft().x - currentBlock.height())) {
					return false;
				}
			}
		}
		if (direction(currentBlock.getTopLeft(), p).equals("right")) {
			for (Block block: blocks) {
				if (block.getTopLeft().y == (currentBlock.getTopLeft().y + currentBlock.width())) {
					return false;
				}
			}
		}
		if (direction(currentBlock.getTopLeft(), p).equals("left")) {
			for (Block block: blocks) {
				if ((block.getTopLeft().y + block.width()) == currentBlock.getTopLeft().y) {
					return false;
				}
			}
		}
		return true;
	}

	/* Returns the direction when moving from p1 to p2.
	 */
	public String direction (Point p1, Point p2) {
		if (p1.x == p2.x) {
			if (p1.y > p2.y) {
				return "up";
			}
			if (p1.y < p2.y) {
				return "down";
			}
		}
		if (p1.y == p2.y) {
			if (p1.x > p2.x) {
				return "right";
			}
			if (p1.y < p2.y) {
				return "left";
			}
		}
		return "diagonal";
	}
}
