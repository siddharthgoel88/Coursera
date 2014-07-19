import java.util.Arrays;
import java.util.HashMap;

public class Fast {

	private Point [] pts, temp;
	private HashMap<String, String> check;

	public Fast() {
		check = new HashMap<String, String>();
	}
	
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

	private void findCollinearPoints() {
		int size = pts.length,j;
		int start=1, end=1;
		Arrays.sort(pts);
		
		for (int i = 0; i < size-1; i++) {
			Arrays.sort(pts, pts[i].SLOPE_ORDER);
			j = 1;
			while (j < size) {
				start = j;
				while ((++j < size) && (pts[0].slopeTo(pts[start]) == pts[0].slopeTo(pts[j])));
				end = j-1;
				checkPrint(start, end, i);
			}
			Arrays.sort(pts);
		}
	}
	
	private String display(Point[] pt) {
		String res="";
		for (int i=0; i<pt.length; i++) {
			res += pt[i].toString();
			if (i != pt.length -1)
				res += " -> ";
		}
		return res;
	}
	
	private void checkPrint(int start, int end, int i) {
		if (end - start > 1) {
			int count = end -start + 2;
			int j =0;
			String result = "";
			
			temp = new Point[end-start+2];
			temp[0] = pts[0];
			for (j = 1; j<count; j++)
				temp[j] = pts[start+j-1];
			
			//StdOut.println("i = " + i);
			//StdOut.println(display(temp));
			Arrays.sort(temp);
			//StdOut.println(display(temp));
			
			result = display(temp);
			if (check.get(result) == null) {
				StdOut.println(result);
				temp[0].drawTo(temp[count-1]);
				check.put(result, "1");
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
