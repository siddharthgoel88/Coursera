/**Coursera Princeton Algorithm - I
 * 
 * Assignment 1 - Percolation Problem
 * 
 * @author Siddharth Goel
 *
 */
import java.lang.IndexOutOfBoundsException;

public class Percolation {
	
	private byte[][] array;
	private int size;
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF ufCopy;
	
	public Percolation(int N) // create N-by-N grid, with all sites blocked
	{
		if (N <= 0)
			throw new IllegalArgumentException("Value of N less than or equal to zero.");
		size = N;
		array = new byte[N][N];
		for (int i = 0; i < N; i++) 
			for (int j=0; j < N; j++)
				array[i][j]=0;
		uf = new WeightedQuickUnionUF((N*N)+2);
		ufCopy = new WeightedQuickUnionUF((N*N)+1);
	}
	
	/*
	private void display()
	{
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
				StdOut.print(array[i][j]+" ");
			StdOut.println();
		}
		StdOut.println();
	}
	*/
	
	private int getIndex(int i, int j)
	{
		i--;
		j--;
		if(i == size || j == size)
			return (size*size);
		else if (i == size+1 || j == size+1)
			return (size*size)+1;
		else
			return (i*size)+j;
	}
	
	private void union(int i1, int j1, int i2, int j2, int both)
	{
		int p = getIndex(i1, j1);
		int q = getIndex(i2, j2);
		
		if (both == 0)
			uf.union(p, q);
		else
		{
			uf.union(p, q);
			ufCopy.union(p, q);
		}
	}
	
	public void open(int i, int j)         // open site (row i, column j) if it is not already
	{
		if (i > size || j > size || i < 1 || j < 1)
			throw new IndexOutOfBoundsException("Index is out of range");
		
		array[i-1][j-1] = 1;
		
		if(i == 1)
			union(i, j, size+1, size+1, 1);
		if(i == size)
			union(i, j, size+2, size+2, 0);
		
		int left = j-1;
		int right = j+1;
		int up = i-1;
		int down = i+1;
		
		if (left > 0 && isOpen(i, left))
			union(i, j, i, left, 1);
		if (right != size+1 && isOpen(i, right))
			union(i, j, i, right, 1);
		if (up >0 && isOpen(up, j))
			union(i, j, up, j, 1);
		if (down != size+1 && isOpen(down, j))
			union(i, j, down, j, 1);
	}
	
	public boolean isOpen(int i, int j)    // is site (row i, column j) open?
	{
		if (i > size || j > size || i < 1 || j < 1)
			throw new IndexOutOfBoundsException("Index is out of range");
		
		if (array[i-1][j-1] == 1)
			return true;
		
		return false;
	}
	
	private boolean connected(int i1, int j1, int i2, int j2, int type)
	{
		int p = getIndex(i1, j1);
		int q = getIndex(i2, j2);
		
		if (type == 0)
		{
			if (uf.connected(p, q))
				return true;
			else
				return false;
		}
		else
		{
			if (ufCopy.connected(p, q))
				return true;
			else
				return false;
		}
	}
	
	public boolean isFull(int i, int j)    // is site (row i, column j) full?
	{
		if (i > size || j > size || i < 1 || j < 1)
			throw new IndexOutOfBoundsException("Index is out of range");
		
		if (connected(i, j, size+1, size+1, 1))
			return true;
		else
			return false;
	}
	
	public boolean percolates()            // does the system percolate?
	{
		if (connected(size+1, size+1, size+2, size+2, 0))
			return true;
		else
			return false;
	}
}