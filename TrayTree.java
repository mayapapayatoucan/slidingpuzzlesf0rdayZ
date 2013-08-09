public class TrayTree {
  
	private TrayNode root;
	private HashSet visited;

	public TrayTree (Tray start) {
		root = TrayNode(start);
		
	}

	private class TrayNode {
		private ArrayList<TrayNode> children;
		private Tray entry;

		public TrayNode (Tray t) {
			entry = t;
		}
	} 
}
