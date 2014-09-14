import java.util.HashMap;


public class Solver {
	
	private final MinPQ<Solver.SearchNode> minpq;
	private final MinPQ<Solver.SearchNode> minpqTwin;
	private SearchNode goal;
	private final HashMap<String, Integer> check;
	private final HashMap<String, Integer> checkTwin;
	
	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		goal = null;
		minpq = new MinPQ<Solver.SearchNode>();
		minpqTwin = new MinPQ<SearchNode>();
		check = new HashMap<String, Integer>();
		checkTwin = new HashMap<String, Integer>();
		
		minpq.insert(new SearchNode(initial, null));
		minpqTwin.insert(new SearchNode(initial.twin(), null));
		
		check.put(initial.toString(), 1);
		checkTwin.put(initial.twin().toString(), 1);
		
		//StdOut.println("Hamming =" + initial.hamming());
		//StdOut.println("Manhattan =" + initial.manhattan());
		//StdOut.println("Original:\n" + initial.toString());
		//StdOut.println("Twin:\n" + initial.twin().toString());
		
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
			
			pushNodes(minNode, minpq, check);
			pushNodes(minNodeTwin, minpqTwin, checkTwin);
			
			minNode = minpq.delMin();
			minNodeTwin = minpqTwin.delMin();
		}
	}
	
	private void pushNodes(SearchNode minNode, MinPQ<SearchNode> pq, HashMap<String, Integer> ck) {
		Board itrBoard = minNode.getBoard();
		for (Board brd : itrBoard.neighbors() ) {
			if (!ck.containsKey(brd.toString())) {
				pq.insert(new SearchNode(brd, minNode));
				ck.put(brd.toString(), 1);
			}
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
			final Stack<Board> trace = new Stack<Board>();
			SearchNode iter = goal;
			while (iter != null) {
				trace.push(iter.getBoard());
				iter = iter.getParent();
			}
			return trace;
		}
	}
	
	private final class SearchNode implements Comparable<SearchNode> {
		
		private final int moves;
		private final Board self;
		private final SearchNode parent;
		private final int manpr;
		private final int hampr;

		private SearchNode(Board self, SearchNode parent) {
			this.self = self;
			this.parent = parent;
			if (parent != null)
				this.moves = parent.moves + 1;
			else
				this.moves = 0;
			
			manpr = manpriority();
			hampr = hampriority();
			
			assert parent == null || this.manpr >= this.parent.manpr;
		}
		
		@Override
		public int compareTo(SearchNode that) {
			if (this.manpr == that.manpr) {
				if (this.moves == that.moves)
					return this.hampr-that.hampr;
				else
					return this.moves-that.moves;
				/*
				if (this.hampr == that.hampr)
					return (this.moves - that.moves);
				else
					return this.hampr - that.hampr;
				*/
			}
			else
				return this.manpr - that.manpr;
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
