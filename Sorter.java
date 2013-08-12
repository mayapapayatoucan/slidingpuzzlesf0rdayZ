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

	//The solve method assumes the tray argument passed in is NOT in the goal config.
	public void solve (Tray t) {
		HashSet<Tray> visited = new HashSet<Tray>();
		visited.add(t);
		/* 
		The end of the LinkedList is the front of the Queue and beginning of Ll is back of queue
		built-in linkedlist method removeLast() dequeus tray at front of queue and 
		the built-in ll method addFirst(Tray) enqueues to the end of the trayQueue.
		*/
		LinkedList<Tray> trayQueue = new LinkedList<Tray>();
		trayQueue.add(t);
		while (!trayQueue.isEmpty()) {
			Tray dQ;
			dQ = trayQueue.removeLast(); //dequeue
			//iterate through all the possible moves for dQ
			ArrayList<Tray> moves = dQ.posMoves();
			for  (int k = 0; k < dQ.posMoves().size(); k++) {
				if (moves.get(k).correctConfig()) {
					moves.get(k).printMoves(); // not a method yet. want to print out all moves to this point
					break;
				}
				if (!visited.contains(moves.get(k))) {
					visited.add(moves.get(k)); // add the tray to the visited hashset.
					trayQueue.addFirst(moves.get(k)); // enqueue
				}
			}
		}
		System.out.println("NO SOLUTION!");
		System.exit(1); //this happens if there is no solution (as specified in piazza post 683).
	}

	public static void main(String[] args) {

	}
}
