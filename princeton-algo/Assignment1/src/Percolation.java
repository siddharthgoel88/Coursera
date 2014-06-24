/**Coursera Princeton Algorithm - I
 * 
 * Assignment 1 - Percolation Problem
 * 
 * @author Siddharth Goel
 *
 */
import java.lang.IndexOutOfBoundsException;

public class Percolation {
	
	private int[][] array;
	private int size;
	private WeightedQuickUnionUF uf;
	
	public Percolation(int N) // create N-by-N grid, with all sites blocked
	{
		size = N;
		array = new int[N][N];
		for (int i = 0; i < N; i++) 
			for (int j=0; j < N; j++)
				array[i][j]=0;
		uf = new WeightedQuickUnionUF((N*N)+2);
	}
	
	private int getIndex(int i, int j)
	{
		if(i == size || j == size)
			return (size*size);
		else if (i == size+1 || j == size+1)
			return (size*size)+1;
		else
			return (i*size)+j;
	}
	
	private void union(int i1, int j1, int i2, int j2)
	{
		int p = getIndex(i1, j1);
		int q = getIndex(i2, j2);
		uf.union(p, q);
	}
	
	public void open(int i, int j)         // open site (row i, column j) if it is not already
	{
		if (i >= size+2 || j >= size+2 || i < 0 || j < 0)
			throw new IndexOutOfBoundsException("Index is out of range");
		
		array[i][j] = 1;
		if(i == 0)
			union(i, j, size, size);
		if(i == size-1)
			union(i, j, size+1,size+1);
		
		int left = j-1;
		int right = j+1;
		int up = i-1;
		int down = i+1;
		
		if (left >= 0 && isOpen(i, left))
				union(i, j, i, left);
		if (right != size && isOpen(i, right))
			union(i, j, i, right);
		if (up >=0 && isOpen(up, j))
			union(i, j, up, j);
		if (down != size && isOpen(down, j))
			union(i, j, down, j);
	}
	
	public boolean isOpen(int i, int j)    // is site (row i, column j) open?
	{
		if (i >= size+2 || j >= size+2 || i < 0 || j < 0)
			throw new IndexOutOfBoundsException("Index is out of range");
		
		if (array[i][j] == 1)
			return true;
		
		return false;
	}
	
	private boolean connected(int i1, int j1, int i2, int j2)
	{
		int p = getIndex(i1, j1);
		int q = getIndex(i2, j2);
		if (uf.connected(p, q))
			return true;
		else
			return false;
	}
	
	public boolean isFull(int i, int j)    // is site (row i, column j) full?
	{
		if (i >= size+2 || j >= size+2 || i < 0 || j < 0)
			throw new IndexOutOfBoundsException("Index is out of range");
		
		if (connected(i, j, size, size))
			return true;
		else
			return false;
	}
	
	public boolean percolates()            // does the system percolate?
	{
		if (isFull(size+1, size+1))
			return true;
		else
			return false;
	}
}