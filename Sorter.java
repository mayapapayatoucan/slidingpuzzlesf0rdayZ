
public class Sorter {
  
	private InputSource input;
	private static Tray tray;
		
	public Sorter () {
		// not implemented yet
	}
	
	public Sorter (String fileName) {
		// Based on stuff from InputSource class -- is there an easier way to do this?
		input = new InputSource(fileName);
		String s = input.readLine ( );
		if (fileName.substring(fileName.length() - 4, fileName.length()).equals("goal")) {	// this contains one block, not a whole tray
			Parse(s);
		} else {
			makeTray(s);   // first line sets up the tray dimensions
			while (true) {
				s = input.readLine ( );   // making blocks
				if (s == null) {
					break;
				}
				Parse(s);
			}
		}
	}
	
	public static void makeTray (String s) {
		tray = new Tray(Integer.valueOf(s.substring(0, 1)), Integer.valueOf(s.substring(2, 3)));
	}
	
	public static void Parse (String line) {
		if (isBlock(line)) {
			tray.addBlock(new Block(line));
		} else if (line.equals("-ooptions")) {
			System.out.println();
		} else if (line.substring(0, 2).equals("-o")) {
			debug(line.substring(2, line.length()));
		} else {
			
		}
	}
	
	public static void debug (String s) {
		// Put debugging options here -- what should we include?
	}
	
	public static boolean isBlock (String line) {
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
	
	public static void main(String[] args) {
		
	}
}
