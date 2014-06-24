import java.lang.Math;

public class PercolationStats 
{
	private int T;
	private int N;
	private Percolation per;
	private double iterations[];
	
	public PercolationStats(int N, int T)
	{
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
					x = StdRandom.uniform(N);
					y = StdRandom.uniform(N);
				} while (per.isOpen(x, y));
				count++;
				per.open(x, y);
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
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);
		if (n < 0 || t < 0)
			throw new IllegalArgumentException("Arguments less than zero");
		
		PercolationStats perstat = new PercolationStats(n, t);
		perstat.monteCarloSimulation();
		perstat.displayResult();
	}

}
