import java.util.*;

/* AUTHORS:
Zach Gottesman
Brad Slaughter
Maya Kulkarni
Winston Huang

*/

public class Solver {

	//tray to solve
	private Tray initTray;

	//debugging flags
	public boolean debug = false;
	public boolean debugStack = false;

	//Create a new solver. Solver takes two string arguments: an initial configuration file name,
	//and a goal configuration file name.
	public Solver (String fileName, String goalName) {

		//Read initial configuration file.
		InputSource initFile = new InputSource(fileName);
		String s = initFile.readLine();

		makeTray(s);   // first line sets up the tray dimensions

		while (true) {
			s = initFile.readLine();   // making blocks

			if (s == null) {
				break;
			}		

			if (isBlock(s)) {
				initTray.addBlock(new Block(s));
			} 
		}
		
		//Read goal configuration file.
		InputSource goalFile = new InputSource(goalName);
		while (true) {
			s = goalFile.readLine();   // making blocks
			if (s == null) {
				break;
			}
			if (isBlock(s)) {
				initTray.addGoalBlock(new Block(s));
			}
		}
	}

	//Make a tray. String consists of row and column dimensions, separated by a space.
	public void makeTray (String s) {
		String[] dimensions = s.split(" ");

		try {

			int rows = Integer.parseInt(dimensions[0]);
			int cols = Integer.parseInt(dimensions[1]);

			initTray = new Tray(rows, cols);

		}

		catch (NumberFormatException e) {}

	}

	//Returns if string corresponds to a valid block.
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

	//Solve the puzzle.
	public void solve() {

		if (initTray.correctConfig()) {
			System.exit(0);   //puzzle is solved
		}

		HashSet<Tray> visited = new HashSet<Tray>();   //set of trays that have been seen
		LinkedList<Tray> process = new LinkedList<Tray>();   //stack of trays to process

		process.add(initTray);
		visited.add(initTray);

		while (!process.isEmpty()) {

			//DEBUG - print stack size of trays to process on each iteration
			if (debugStack) {
				System.out.println("Solving...stack size: " + process.size());
			}

			Tray curr = process.removeFirst();
			ArrayList<Tray> moves = curr.posMoves();

			if (moves.isEmpty()) {
					System.exit(1);
			}

			for  (Tray m : curr.posMoves()) {   //process possible moves from current tray

				if (m.correctConfig()) {   //if the puzzle is solved:
					m.printMoves(); 
					System.exit(0);
				}

				if (!visited.contains(m)) {   //if this configuration has not been seen:
					visited.add(m);   // add the tray to the visited hashset.
					process.addFirst(m);   // add to stack
				}

			}
		}

		System.exit(1);   //this happens if there is no solution 
	}

	public static void main(String[] args) {

		//debugging flags
		if (args[0].substring(0, 2).equals("-o")) {

			String debugCommand = args[0].substring(2);
			
			if ("options".equals(debugCommand)) {
				System.out.println("Debugging options:");
				System.out.println("-otray: prints debugging output for trays");
				System.out.println("-osolver: prints debugging output for the solver");
				System.out.println("-oposmoves: prints matrix representations of possible moves");
				System.out.println("-ovalidmove: if call to validMove returns false, prints reason");	
				System.out.println("-ostack: monitors size of tray stack - prints size on each iteration");		
				System.exit(0);
			}

			Solver run = new Solver(args[1], args[2]);

			if ("tray".equals(debugCommand)) {
				run.getTray().debug = true;
			}

			else if ("posmoves".equals(debugCommand)) {
				run.getTray().debugPosMoves = true;
			}

			else if ("validmove".equals(debugCommand)) {
				run.getTray().debugValidMove = true;
			}

			else if ("solver".equals(debugCommand)) {
				run.debug = true;
			}

			else if ("stack".equals(debugCommand)) {
				run.debugStack = true;
			}

			run.solve();

		}

		else {

			Solver run = new Solver(args[0], args[1]);
			run.solve();

		}

	}
}
