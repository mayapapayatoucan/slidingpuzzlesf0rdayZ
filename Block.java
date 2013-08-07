import java.awt.*;
import java.util.*;


public class Block {
	
	private int trow, brow, lcol, rcol;
	
	public Block(String s){
		String[] points = s.split(" ");
		if (points.length != 4) {
			throw new IllegalArgumentException("Must input exactly four integers");
		}
		try {

			trow = Integer.parseInt(points[0]);
			lcol = Integer.parseInt(points[1]);
			brow = Integer.parseInt(points[2]);
			rcol = Integer.parseInt(points[3]);

		}

		catch (NumberFormatException e) {
			throw new IllegalArgumentException("Arguments must be integers.");

		}

		if ((trow > brow) || (lcol > rcol)) {
			throw new IllegalArgumentException("Coordinates do not form a valid block.");
		}
	}


	public int width() {
		return rcol - lcol;
	}
	
	public int height() {
		return brow - trow;
	}

	public int trow() {
		return trow;
	}

	public int brow() {
		return brow;
	}

	public int lcol() {
		return lcol;
	}

	public int rcol() {
		return rcol;
	}
}
