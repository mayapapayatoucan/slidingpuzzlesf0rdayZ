import java.awt.*;
import java.util.*;


public class Block {
	private Point topLeft;
	private Point bottomRight;
	
	public Block(String s){
		String[] points = s.split(" ");
		if (points.length != 4) {
			throw new IllegalArgumentException("Must input exactly four integers");
		}
		topLeft = new Point(new Integer(points[0]), new Integer(points[1]));
		bottomRight = new Point(new Integer(points[2]), new Integer(points[3]));
		if ((topLeft.x > bottomRight.x) || (topLeft.y > bottomRight.y)) {
			throw new IllegalArgumentException("Invalid points");
		}
	}

	public int width() {
		return bottomRight.x - topLeft.x + 1;
	}
	
	public int height() {
		return bottomRight.y - topLeft.y + 1;
	}
	
	public int size() {
		return width()*height();
	}
}
