import java.util.*;

public class Solver {

	private InputSource input;
	private Tray initTray;
	public boolean debug;


	public Solver (String fileName) {
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

	public Solver (String fileName, String goalName) {
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
				initTray.addGoalBlock(new Block(s));
			}
		}
	}

	public void makeTray (String s) {
		String[] dimensions = s.split(" ");
		initTray = new Tray(Integer.valueOf(dimensions[0]), Integer.valueOf(dimensions[1]));
	}

	public void Parse (String line) {
		if (isBlock(line)) {
			initTray.addBlock(new Block(line));
		} else if (line.substring(0, 2).equals("-o")) {
			String debugCommand = line.substring(2, line.length());
			if ("options".equals(debugCommand)) {
				System.out.println("Debugging options:");
				System.out.println("-oblock: " + "prints debugging output for blocks");
				System.out.println("-otray: " + "prints debugging output for trays");
				System.out.println("-osorter: " + "prints debugging output for the sorter");
			}
			if ("block".equals(debugCommand)) {
				for (Block block : initTray.getBlocks()) {
					block.debug = true;
				}
			}
			if ("tray".equals(debugCommand)) {
				initTray.debug = true;
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
		return initTray;
	}

	public void solve () {

		if (initTray.correctConfig()) {
			System.exit(0);//puzzle is solved
		}

		HashSet<Tray> visited = new HashSet<Tray>();//set of trays that have been seen
		LinkedList<Tray> process = new LinkedList<Tray>();//stack of trays to process

		process.add(initTray);
		visited.add(initTray);

		while (!process.isEmpty()) {

			Tray curr = process.removeFirst();
			ArrayList<Tray> moves = curr.posMoves();

			if (moves.isEmpty()) {
					System.exit(1);
			}

			for  (Tray m : curr.posMoves()) {//process possible moves from current tray

				if (m.correctConfig()) {//if the puzzle is solved:
					m.printMoves(); 
					System.exit(0);
				}

				if (!visited.contains(m)) {//if this configuration has not been seen:
					visited.add(m); // add the tray to the visited hashset.
					process.addFirst(m); // add to stack
				}

			}
		}

		System.exit(1); //this happens if there is no solution 
	}

	public static void main(String[] args) {

		Solver run = new Solver(args[0], args[1]);
		run.solve();

	}
}
