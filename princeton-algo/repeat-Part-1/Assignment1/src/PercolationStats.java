public class PercolationStats {
	private double threshold[];
	private int T;
	
	public PercolationStats(int N, int T) {   // perform T independent computational experiments on an N-by-N grid
		if (N < 1 || T < 1)
			throw new java.lang.IllegalArgumentException("Illegal Parameter");
		
		this.T = T;
		threshold = new double[T];
		monteCarloExperiment(N,T);
	}
	
	private void monteCarloExperiment(int N, int T) {
		Percolation perObj;
		for (int i = 0; i < T; i++) {
			int count = 0;
			int x,y;
			
//			StdDraw.show(0); //Draw
			
			perObj = new Percolation(N);
			while(!perObj.percolates()) {
				do {
					x = rand(N);
					y = rand(N);
				} while(perObj.isOpen(x, y));
				perObj.open(x, y);
				count++;
//				PercolationVisualizer.draw(perObj, N);
//		        StdDraw.show(100);
			}
			threshold[i] = (double)count / (N * N);
			perObj = null;
		}
	}
	
	private int rand(int N) {
		return StdRandom.uniform(N)+1;
	}

	public double mean() {                    // sample mean of percolation threshold
		return StdStats.mean(threshold);
	}
	
	public double stddev() {                  // sample standard deviation of percolation threshold
		return StdStats.stddev(threshold);
	}
	
	public double confidenceLo() {            // returns lower bound of the 95% confidence interval
		return (mean() - ( (1.96 * stddev()) / Math.sqrt(T) ) );
	}
	
	public double confidenceHi() {            // returns upper bound of the 95% confidence interval
		return (mean() + ( (1.96 * stddev()) / Math.sqrt(T) ) );
	}
		
	public static void main(String[] args) {
		if (args.length != 2) 
			throw new IllegalArgumentException("Usage: java PercolationStats <size_of_array> <number_of_experiment_runs>");
	
		PercolationStats obj = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]) );
		StdOut.println("mean\t\t\t= " + obj.mean());
		StdOut.println("stddev\t\t\t= " + obj.stddev());
		StdOut.println("95% confidence interval\t= " + obj.confidenceLo() + ", " + obj.confidenceHi());
	}

}
