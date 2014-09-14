
public class Board {
	
	private final byte[][] board;
	private final int N;
	private int zeroX;
	private int zeroY;
	
	// Construct a board from an N-by-N array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {
		this(integerToByte(blocks));
	}
	
	private static byte[][] integerToByte(int[][] blocks) {
		int size = blocks.length;
		byte[][] byteArray = new byte[size][size];
		
		for (int i=0; i<size; i++)
			for (int j=0; j<size; j++)
				byteArray[i][j] = (byte)blocks[i][j];
		return byteArray;
	}
	
	private Board(byte[][] blocks) {
		N = (byte)blocks.length;
		board = new byte[N][N];
		
		for (int i =0; i < N; i++)
			for (int j = 0; j < N; j++) {
				board[i][j] = (byte)blocks[i][j];
				
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
				if (!isCorrect(i, j))
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
				if (val == 0)
					continue;
				
				goalX = (int)(val - 1)/N;
				goalY = (val - 1) % N;
				dist = Math.abs(goalX - i) + Math.abs(goalY - j);
				//StdOut.println("val="+val+", ("+i+","+j+") => ("+goalX+","+goalY+") distance is "+ dist);
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
	private byte[][] copyArray(byte[][] array) {
		byte[][] copy = new byte[array.length][array.length];
		
		for (int i = 0; i < N; i++)
			System.arraycopy(array[i], 0, copy[i], 0, array.length);
		
		return copy;
	}
	
	// a board obtained by exchanging two adjacent blocks in the same row
	public Board twin() {
		int i = 0;
		byte[][] array;
		
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
		byte[][] temp;
		
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
	
    private void swapRight(byte[][] array, int x, int y) {
    	byte temp;
    	temp = array[x][y];
    	array[x][y] = array[x][y+1];
    	array[x][y+1] = temp;
	}

	private void swapLeft(byte[][] array, int x, int y) {
		byte temp;
		temp = array[x][y];
		array[x][y] = array[x][y-1];
		array[x][y-1] = temp;
	}

	private void swapDown(byte[][] array, int x, int y) {
		byte temp;
		temp = array[x][y];
		array[x][y] = array[x+1][y];
		array[x+1][y] = temp;		
	}

	private void swapUp(byte[][] array, int x, int y) {
		byte temp;
		temp = array[x][y];
		array[x][y] = array[x-1][y];
		array[x-1][y] = temp;
	}

	// string representation of the board (in the output format specified below)
	public String toString() {
		String result = N + "\n";
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
