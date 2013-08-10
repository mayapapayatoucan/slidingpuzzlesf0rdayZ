import java.awt.*;
import java.util.*;


public class Block {
	
	public boolean debug;
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
	
	public Block (Block b) {
		trow = b.trow();
		brow = b.brow();
		lcol = b.lcol();
		rcol = b.rcol();

		
		
	}

	public void move (int newTopRow, int newLeftCol) {
		int rowShift = trow - newTopRow;
		int colShift = lcol - newLeftCol;
		trow = newTopRow;
		lcol = newLeftCol;
		brow = brow - rowShift;
		rcol = rcol - colShift;
	}
	
	public boolean overlapping (int trowDest, int lcolDest, int browDest, int rcolDest) {
		int rowDest = trowDest + height();
		int colDest = lcolDest + width();
		return (trowDest <= brow()) && (browDest >= trow()) && (rcolDest >= lcol()) && (lcolDest <= rcol());
	}
	
	public String toString() {
		return "trow: " + trow + " lcol: " + lcol + " brow: " + brow + " rcol: " + rcol;
	}
	
	public boolean equals (Block b) {
		return (trow == b.trow) && (lcol == b.lcol) && (brow == b.brow) && (rcol == b.rcol);
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
