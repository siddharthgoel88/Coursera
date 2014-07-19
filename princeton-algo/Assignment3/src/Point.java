/*************************************************************************
 * Name: Siddharth Goel
 * Email: siddharth98391@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlope(this);       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
    	if (that.x == this.x) {
    		if (that.y == this.y)
    			return Double.NEGATIVE_INFINITY;
    		else
    			return Double.POSITIVE_INFINITY;
    	}
    	
    	if (that.y == this.y)
    		return (+0.0);
    	
    	return ((double)(that.y - this.y))/(that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if(this.y < that.y)
        	return this.y - that.y;
        else if (this.y > that.y)
        	return this.y - that.y;
        else {
        	if (this.x < that.x)
        		return this.x - that.x;
        	else if(this.x > that.x)
        		return this.x - that.x;
        	else
        		return 0;
        }
    
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    private static void display(Point[] pt) {
		String res="";
		for (int i=0; i<pt.length; i++) {
			res += pt[i].toString();
			if (i != pt.length -1)
				res += " -> ";
		}
		StdOut.println(res);
	}
    
    // unit test
    public static void main(String[] args) {
        Point p,q,r,s;
        Point [] arr;
        p = new Point(448, 137);
        q = new Point(344, 19);
        r = new Point(56, 21);
        s = new Point(135,96);
        StdOut.println(p.SLOPE_ORDER.compare(q, r));
        StdOut.println(p.SLOPE_ORDER.compare(s, q));
        arr = new Point[4];
        arr[0] = new Point(0,10000);
        arr[1] = new Point(10000, 0);
        arr[2] = new Point(7000, 3000);
        arr[3] = new Point(3000, 7000);
        Arrays.sort(arr);
        display(arr);
    }
    
    private static class BySlope implements Comparator<Point> {
    	Point outer;
    	BySlope(Point ot) {
    		this.outer = ot;
    	}
    	
    	public int compare(Point x, Point y) {
    			if (x == null || y == null)
    				throw new NullPointerException();
    			
    		if( outer.slopeTo(x) == outer.slopeTo(y))
    			return 0;
    		else if (outer.slopeTo(x) > outer.slopeTo(y))
    			return 1;
    		else
    			return -1;
    	}
    }
}
