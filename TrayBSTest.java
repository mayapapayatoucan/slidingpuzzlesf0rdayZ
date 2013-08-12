import java.util.*;

public class TrayBSTest {

//used to test the prevTray, prevMove pointers and the printMoves() method.
public static void main(String[] args) {
  Tray t2 = new Tray(5, 4);
	Block b6 = new Block("0 0 1 0");
	Block b7 = new Block("2 1 3 2");
	Block b8 = new Block("4 0 4 1");
	t2.addBlock(b6);
	t2.addBlock(b7);
	t2.addBlock(b8);
	Block g1 = new Block("1 0 2 0");
	Block g2 = new Block("1 2 2 2");
	Block g3 = new Block("4 1 4 2");
	t2.addGoalBlock(g1);
	t2.addGoalBlock(g2);
	t2.addGoalBlock(g3);

	System.out.println("Pointer for starting Tray: ");
	// System.out.println(t2);
	ArrayList<Tray> babies = t2.posMoves();
	for (int k = 0; k < babies.size(); k ++){
		Tray curr = babies.get(k);
		System.out.println("Next baby is: ");
		//curr.printOccupied();
		System.out.println("prevMove is: " + curr.prevMove);
		System.out.println("prevTray hashcode is: " + curr.prevTray);
		System.out.println("curr.prevTray.printOccupied is: ");
		curr.prevTray.printOccupied();
		if (curr.prevTray == null) {
			System.out.println("You done goofed");
			}
		}


		System.out.println("GRANDCHILD TRAY *****************************************: ");
		babies.get(0).printOccupied();
	ArrayList<Tray> grandbabies = babies.get(0).posMoves();
		for (int k = 0; k < grandbabies.size(); k ++){
		Tray curr = grandbabies.get(k);
		System.out.println("Next move is: ");
		curr.printOccupied();
		System.out.println("Move is: " + curr.prevMove);
		//System.out.println("prevTray hashcode is: " + curr.prevTray);
		//System.out.println(curr.prevTray == babies.get(0));
		System.out.println("Print All Moves output: ");
		curr.printMoves();
		System.out.println("Previous Tray is: ");
		curr.prevTray.printOccupied();
		if (curr.prevTray != babies.get(0)) {
			System.out.println("You done goofed");
			}
		}






	}
}
