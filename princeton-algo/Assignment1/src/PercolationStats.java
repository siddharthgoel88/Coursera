import java.lang.Math;

public class PercolationStats 
{
	private int T;
	private int N;
	private Percolation per;
	private double iterations[];
	
	public PercolationStats(int N, int T)
	{
		if (N <= 0 || T <= 0)
			throw new IllegalArgumentException("Arguments less than or equal to zero");
		
		this.N = N;
		this.T = T;
		iterations = new double[T];
	}
	
	public double mean()
	{
		return StdStats.mean(iterations);
	}
	
	public double stddev()
	{
		return StdStats.stddev(iterations);
	}
	
	public double confidenceLo()
	{
		return (mean() - ((1.96*stddev())/Math.sqrt(T)));
	}
	
	public double confidenceHi()
	{
		return (mean() + ((1.96*stddev())/Math.sqrt(T)));
	}
	
	private void monteCarloSimulation()
	{
		int x,y,count;
		for (int i = 0; i < T; i++)
		{
			per = new Percolation(N);
			count = 0;
			do
			{	
				do
				{
					x = StdRandom.uniform(1,N+1);
					y = StdRandom.uniform(1,N+1);
				} while (per.isOpen(x, y));
				count++;
				//StdOut.println("X="+x+" Y="+y);
				per.open(x, y);
				//per.display();
			} while(!per.percolates());
			//StdOut.println("Iteration = " + i +" Count = "+count);
			iterations[i] = (double)((1.0*count)/(N*N));
		}
	}
	
	private void displayResult()
	{
		StdOut.println("mean\t\t\t= " + mean());
		StdOut.println("stddev\t\t\t= " + stddev());
		StdOut.println("95% confidence interval\t= "+confidenceLo()+", "+confidenceHi());
	}

	public static void main(String[] args) 
	{
				
		PercolationStats perstat = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		perstat.monteCarloSimulation();
		perstat.displayResult();
	}
 
}
