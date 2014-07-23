
public class Solver {
	
	private MinPQ<Solver.SearchNode> minpq;
	private MinPQ<Solver.SearchNode> minpqTwin;
	private SearchNode goal;
	
	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		
		minpq = new MinPQ<Solver.SearchNode>();
		minpqTwin = new MinPQ<SearchNode>();
		
		minpq.insert(new SearchNode(initial, null));
		minpqTwin.insert(new SearchNode(initial.twin(), null));
		
		StdOut.println("Original:\n" + initial.toString());
		StdOut.println("Twin:\n" + initial.twin().toString());
		
		SearchNode minNode = minpq.delMin();
		SearchNode minNodeTwin = minpqTwin.delMin();
		
		while (true) {
			
			if (minNode.getBoard().isGoal()) {
				goal = minNode;
				break;
			}
			if (minNodeTwin.getBoard().isGoal()) {
				goal = null;
				break;
			}
			
			pushNodes(minNode, minpq, initial.toString());
			pushNodes(minNodeTwin, minpqTwin, initial.twin().toString());
			
			minNode = minpq.delMin();
			minNodeTwin = minpqTwin.delMin();
		}
	}
	
	private void pushNodes(SearchNode minNode, MinPQ<SearchNode> pq, String check) {
		Board itrBoard = minNode.getBoard();
		for (Board brd : itrBoard.neighbors() ) {
			if (brd.toString() != check)
				pq.insert(new SearchNode(brd, minNode));
		}
	}

	// is the initial board solvable?
	public boolean isSolvable() {
		if (goal == null)
			return false;
		else
			return true;
	}
	
	// min number of moves to solve initial board; -1 if no solution
	public int moves() {
		if (goal == null)
			return -1;
		else
			return goal.moves;
	}
	
	// sequence of boards in a shortest solution; null if no solution
	public Iterable<Board> solution() {
		if (goal == null)
			return null;
		else {
			Stack<Board> trace = new Stack<Board>();
			while (goal != null) {
				trace.push(goal.getBoard());
				goal = goal.getParent();
			}
			return trace;
		}
	}
	
	private class SearchNode implements Comparable<SearchNode> {
		
		private int moves;
		private Board self;
		private SearchNode parent;

		private SearchNode(Board self, SearchNode parent) {
			this.self = self;
			this.parent = parent;
			if (parent != null)
				this.moves = parent.moves + 1;
			else
				this.moves = 1;
		}
		
		@Override
		public int compareTo(SearchNode that) {
			if (this.manpriority() == that.manpriority())
				return this.hampriority() - that.hampriority();
			else
				return this.manpriority() - that.manpriority();
		}
		
		private int manpriority() {
			return moves + this.self.manhattan();
		}
		
		private int hampriority() {
			return moves + this.self.manhattan();
		}
		
		private Board getBoard() {
			return self;
		}
		
		private SearchNode getParent() {
			return parent;
		}
	}

	public static void main(String[] args) {
		 In in = new In(args[0]);
		    int N = in.readInt();
		    int[][] blocks = new int[N][N];
		    for (int i = 0; i < N; i++)
		        for (int j = 0; j < N; j++)
		            blocks[i][j] = in.readInt();
		    Board initial = new Board(blocks);

		    // solve the puzzle
		    Solver solver = new Solver(initial);

		    // print solution to standard output
		    
		    if (!solver.isSolvable())
		        StdOut.println("No solution possible");
		    else {
		        StdOut.println("Minimum number of moves = " + solver.moves());
		        for (Board board : solver.solution())
		            StdOut.println(board);
		    }
		    
	}

}
