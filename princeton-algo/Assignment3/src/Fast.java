import java.util.Arrays;

public class Fast {

	private Point [] pts, copy;
	
	public static void main(String[] args) {
		if(args.length != 1)
		{
			StdOut.println("Usage: java Brute <input file>");
			System.exit(1);
		}
		
		String filename = args[0];
		Fast obj = new Fast();
		obj.readInput(filename);
		obj.findCollinearPoints();
	}

	//Not a good design but a much needed hack 
	private void copyPoints(Point[] src, Point[] dest) {
		int size = pts.length;
		dest = Arrays.copyOf(src, size);
	}
	
	private void findCollinearPoints() {
		int size = pts.length;
		int count=1, start=1, end=1;
		copyPoints(pts, copy);
		for (int i = 0; i < size-1; i++) {
			StdOut.println(pts[i].toString());
			Arrays.sort(pts, pts[i].SLOPE_ORDER);
			StdOut.println(pts[0].toString());
			for (int j = 1; j < size-1; j++) {
				if (pts[0].slopeTo(pts[j]) == pts[0].slopeTo(pts[j+1])) {
					if (count == 1)
						start = j;
					count++;
					if (j == size-2)
						checkPrint(start,end);
				} else {
					end = j;
					checkPrint(start,end);
					count = 1;
				}
			}
			copyPoints(copy, pts);
		}
	}

	private void checkPrint(int start, int end) {
		if (end - start > 1) {
			StdOut.print(pts[0].toString());
			while (start <= end)
				StdOut.print(" -> " + pts[start++].toString());
			StdOut.println();
		}
	}

	private void readInput(String filename) {
		In file = new In(filename);
		int size,x,y;
		String[] input;
		size = Integer.parseInt(file.readString().trim());
		input = file.readAllStrings();
		pts = new Point[size];
		while(size-- > 0) {
			x = Integer.parseInt(input[(2*size)]);
			y = Integer.parseInt(input[(2*size) + 1]);
			pts[size] = new Point(x,y);
		
		}
	}
	
}
