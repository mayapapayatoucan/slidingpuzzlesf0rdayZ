import java.util.*;

public class Sorter {
	
	private InputSource input;
	private Tray tray;
	public boolean debug;
		
	public Sorter () {
		// not implemented yet
	}
	
	public Sorter (String fileName) {
		// Based on stuff from InputSource class -- is there an easier way to do this?
		input = new InputSource(fileName);
		String s = input.readLine ( );
		makeTray(s);   // first line sets up the tray dimensions
		while (true) {
			s = input.readLine ( );   // making blocks
			if (s == null) {
				break;
			}
			Parse(s);
		}
	}
	
	public Sorter (String fileName, String goalName) {
		// Based on stuff from InputSource class -- is there an easier way to do this?
		input = new InputSource(fileName);
		String s = input.readLine ( );
		makeTray(s);   // first line sets up the tray dimensions
		while (true) {
			s = input.readLine ( );   // making blocks
			if (s == null) {
				break;
			}
			Parse(s);
		}
		input = new InputSource(goalName);
		while (true) {
			s = input.readLine ( );   // making blocks
			if (s == null) {
				break;
			}
			if (isBlock(s)) {
				tray.addGoalBlock(new Block(s));
			}
		}
	}
	
	public void makeTray (String s) {
		tray = new Tray(Integer.valueOf(s.substring(0, 1)), Integer.valueOf(s.substring(2, 3)));
	}
	
	public void Parse (String line) {
		if (isBlock(line)) {
			tray.addBlock(new Block(line));
		} else if (line.substring(0, 2).equals("-o")) {
			String debugCommand = line.substring(2, line.length());
			if ("options".equals(debugCommand)) {
				System.out.println("Debugging options:");
				System.out.println("-oblock: " + "prints debugging output for blocks");
				System.out.println("-otray: " + "prints debugging output for trays");
				System.out.println("-osorter: " + "prints debugging output for the sorter");
			}
			if ("block".equals(debugCommand)) {
				for (Block block : tray.getBlocks()) {
					block.debug = true;
				}
			}
			if ("tray".equals(debugCommand)) {
				tray.debug = true;
			}
			if ("solver".equals(debugCommand)) {
				debug = true;
			}
			System.out.println("Not a valid debugging option. Type -ooptions to see all debugging options.");
			System.exit(1);
		} else {
			
		}
	}
	
	public boolean isBlock (String line) {
		String[] vals = line.split(" ");
		if (vals.length != 4) {
			return false;
		}
		for (int i = 0; i < 4; i++) {
			try {
				Integer.parseInt(vals[i]);	// see if it's an int
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}
	
	public Tray getTray() {
		return tray;
	}
	
	public void solve () {
		
	}
	
	public static void main(String[] args) {
		
	}
}
