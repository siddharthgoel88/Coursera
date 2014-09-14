import java.util.Arrays;


public class Brute {
	private Point [] pts;
	
	public static void main(String[] args) {
		if(args.length != 1)
		{
			StdOut.println("Usage: java Brute <input file>");
			System.exit(1);
		}
		
		String filename = args[0];
		Brute obj = new Brute();
		obj.readInput(filename);
		obj.findCollinearPoints();
	}

	private void findCollinearPoints() {
		int size = pts.length;
		Arrays.sort(pts);
		for (int i = 0; i < size-3; i++ ) 
			for (int j = i+1; j < size-2; j++)
				for (int k = j+1; k < size-1; k++)
					for(int l = k+1; l < size; l++) {
						if((pts[i].slopeTo(pts[j]) == pts[i].slopeTo(pts[k])) && 
								(pts[i].slopeTo(pts[k])	== pts[i].slopeTo(pts[l]))) {
							StdOut.println(pts[i].toString() + " -> " +
									pts[j].toString() + " -> " +
									pts[k].toString() + " -> " +
									pts[l].toString() );
							pts[i].drawTo(pts[l]);
						}
					}
	}


	private void readInput(String filename) {
		In file = new In(filename);
		int size,x,y;
		String[] input;
		size = Integer.parseInt(file.readString().trim());
		input = file.readAllStrings();
		pts = new Point[size];
		
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		
		while(size-- > 0) {
			x = Integer.parseInt(input[(2*size)]);
			y = Integer.parseInt(input[(2*size) + 1]);
			pts[size] = new Point(x,y);
			pts[size].draw();
		}
	}

}
