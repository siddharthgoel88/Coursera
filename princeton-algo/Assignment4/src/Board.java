
public class Board {
	
	private final int[][] board;
	private final int[][] goal;
	private final int N;
	private int zeroX;
	private int zeroY;
	
	// Construct a board from an N-by-N array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {          
		N = blocks.length;
		board = new int[N][N];
		goal = new int[N][N];
		
		for (int i =0; i < N; i++)
			for (int j = 0; j < N; j++) {
				board[i][j] = blocks[i][j];
				goal[i][j] = (i * N) + j + 1;
				
				if (board[i][j] == 0) {
					zeroX = i;
					zeroY = j;
				}
			}
	}
	
	// board dimension N
	public int dimension() {
		return N;
	}
	
	//Checks if correct element is at (i,j) 
	private boolean isCorrect (int i, int j) {
		if (board[i][j] == 0)
			return true;
		
		if (board[i][j] != ((i * N)+j+1) )
			return false;
		return true;
	}
	
	// number of blocks out of place
	public int hamming() {
		int hamcount = 0;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (isCorrect(i, j))
					hamcount++;
		return hamcount;
	}
	
	// sum of Manhattan distances between blocks and goal
	// Logic : ( (x-1)/N , (x-1)%N ) should is the correct position,
	// now subtract co-ordinates to get the distance b/w block and goal. 
	public int manhattan() {
		int manhcount = 0;
		int goalX,goalY;
		int val,dist;
		
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				val = board[i][j];
				goalX = (int)(val - 1)/N;
				goalY = (val - 1) % N;
				dist = Math.abs(goalX - i) + Math.abs(goalY - j);
				manhcount += dist;
			}
		
		return manhcount;
	}
	
	// is this board the goal board?
	public boolean isGoal() {
		for (int i = 0; i < N; i++)
			for (int j = 0; j< N; j++)
				if (!isCorrect(i, j))
					return false;
		return true;
	}
	
	//make copy of array
	private int[][] copyArray(int[][] array) {
		int[][] copy = new int[array.length][array.length];
		
		for (int i = 0; i < N; i++)
			System.arraycopy(array[i], 0, copy[i], 0, array.length);
		
		return copy;
	}
	
	// a board obtained by exchanging two adjacent blocks in the same row
	public Board twin() {
		int i = 0;
		int[][] array;
		
		if (zeroX == 0)
			i++;
		
		array = copyArray(board);
		swapRight(array, i, 0);
		return new Board(array);
	}
	
	// does this board equal y?
	public boolean equals(Object y) {
		if (y == this)
			return true;
		
		if (y == null)
			return false;
		
		if (y.getClass() != this.getClass())
			return false;
		
		Board that = (Board) y;
		if (this.dimension() != that.dimension())
			return false;
		
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (this.board[i][j] != that.board[i][j])
					return false;
		
		return true;
	}
	
	// all neighboring boards
	public Iterable<Board> neighbors() {
		Stack<Board> neigh = new Stack<Board>();
		int[][] temp = new int[N][N];
		
		temp = copyArray(board);

		if (zeroX != 0) {
			swapUp(temp, zeroX, zeroY);
			neigh.push(new Board(temp));
			swapDown(temp, zeroX-1, zeroY);
		}
		
		if (zeroX != N-1) {
			swapDown(temp, zeroX, zeroY);
			neigh.push(new Board(temp));
			swapUp(temp, zeroX+1, zeroY);
		}
		
		if (zeroY != 0) {
			swapLeft(temp, zeroX, zeroY);
			neigh.push(new Board(temp));
			swapRight(temp, zeroX, zeroY-1);
		}
		
		if (zeroY != N-1) {
			swapRight(temp, zeroX, zeroY);
			neigh.push(new Board(temp));
			swapLeft(temp, zeroX, zeroY+1);
		}
		
		return neigh;
	}
	
    private void swapRight(int[][] array, int x, int y) {
    	int temp;
    	temp = array[x][y];
    	array[x][y] = array[x][y+1];
    	array[x][y+1] = temp;
	}

	private void swapLeft(int[][] array, int x, int y) {
		int temp;
		temp = array[x][y];
		array[x][y] = array[x][y-1];
		array[x][y-1] = temp;
	}

	private void swapDown(int[][] array, int x, int y) {
		int temp;
		temp = array[x][y];
		array[x][y] = array[x+1][y];
		array[x+1][y] = temp;		
	}

	private void swapUp(int[][] array, int x, int y) {
		int temp;
		temp = array[x][y];
		array[x][y] = array[x-1][y];
		array[x-1][y] = temp;
	}

	// string representation of the board (in the output format specified below)
	public String toString() {
		String result = "";
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				result += String.format("%2d ", board[i][j]);
			}
			result += "\n";
		}
		
		return result;
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
