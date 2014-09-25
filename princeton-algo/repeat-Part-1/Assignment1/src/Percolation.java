public class Percolation {
	
	private byte array[][];
	private int size;
	private WeightedQuickUnionUF ufDup;
	private WeightedQuickUnionUF uf;
	
	public Percolation(int N) {                // create N-by-N grid, with all sites blocked	
		if (N <= 0)
			throw new IllegalArgumentException("N should be greater than zero.");
		
		size = N;
		array = new byte[size+1][size+1];
		
		ufDup = new WeightedQuickUnionUF((size * size) + 2);
		uf = new WeightedQuickUnionUF((size * size) + 2);
		
		for (int i = 1; i <= size; i++) {
			ufDup.union(0, i);
			uf.union(0, i);
		}

		if ( size > 1 ) {
			for (int i = (size * (size-1)); i < (size*size)+1; i++) {
				ufDup.union(i, size+1);
			}
		} else {
			ufDup.union(size, size+1);
		}
		
		for (int i=0; i < (size+1); i++) {
			for (int j=0; j< (size+1); j++)
				array[i][j] = 0;  // 0 means closed sites
		}
	}
	
	public void open(int i, int j) {          // open site (row i, column j) if it is not already
		if (!isValidPoint(i, j))
			throw new IndexOutOfBoundsException();
		
		if (isOpen(i, j))
			return;
		
		array[i][j] = 1;
		
		int leftX = i;
		int leftY = j-1;
		int rightX = i;
		int rightY = j+1;
		int upX = i-1;
		int upY = j;
		int downX = i+1;
		int downY = j;
		
		if (isValidPoint(leftX, leftY) && isOpen(leftX, leftY)) {
			ufDup.union(indexToVal(leftX, leftY), indexToVal(i, j));
			uf.union(indexToVal(leftX, leftY), indexToVal(i, j));
		}
		
		if (isValidPoint(rightX, rightY) && isOpen(rightX, rightY)) {
			ufDup.union(indexToVal(rightX, rightY), indexToVal(i, j));
			uf.union(indexToVal(rightX, rightY), indexToVal(i, j));
		}
		
		if (isValidPoint(upX, upY) && isOpen(upX, upY)) {
			ufDup.union(indexToVal(upX, upY), indexToVal(i, j));
			uf.union(indexToVal(upX, upY), indexToVal(i, j));
		}
		
		if (isValidPoint(downX, downY) && isOpen(downX, downY)) {
			ufDup.union(indexToVal(downX, downY), indexToVal(i, j));
			uf.union(indexToVal(downX, downY), indexToVal(i, j));
		}
	}
	
	private boolean isValidPoint(int x, int y) {
		if (x < 1 || x > size)
			return false;
		if (y < 1 || y > size)
			return false;
		return true;
	}
	
	private int indexToVal(int x, int y) {
		return (((x-1) * size) + y);
	}

	public boolean isOpen(int i, int j) {     // is site (row i, column j) open?
		if (!isValidPoint(i, j))
			throw new IndexOutOfBoundsException();
		return (array[i][j] == 1);
	}
	
	public boolean isFull(int i, int j) {     // is site (row i, column j) full?
		if (!isValidPoint(i, j))
			throw new IndexOutOfBoundsException();
		
		if(!isOpen(i, j))
			return false;
		
		int val = indexToVal(i, j);
		return uf.connected(0, val);
	}
	
	public boolean percolates() {             // does the system percolate?
		return ufDup.connected(0, size+1);
	}
	
	public static void main(String[] args) {}	
}
