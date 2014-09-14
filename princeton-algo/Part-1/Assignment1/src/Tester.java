import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Tester 
{

	public static void main(String[] args) throws FileNotFoundException
	{
		String fileName;
		System.out.print("Enter filename for input file:");
		Scanner file = new Scanner(System.in);
		fileName = file.next();
		
		File inputFile = new File(fileName);
		Scanner fp = new Scanner(inputFile);
		
		int iter = Integer.parseInt(fp.nextLine().trim());
		System.out.println(iter);
		
		EagerQuickFindUF uf = new EagerQuickFindUF(iter);
		//LazyQuickFindUF uf = new LazyQuickFindUF(iter);
		//WeightedQuickFindUF uf = new WeightedQuickFindUF(iter);
		
		/*
		System.out.println("Which algorithm do you want to check:");
		System.out.println("1. Eager Approach");
		System.out.println("2. Lazy Approach");
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();
		switch(option)
		{
		case 1:
			EagerQuickFindUF euf = new EagerQuickFindUF(iter);
			break;
		case 2:
			LazyQuickFindUF luf = new LazyQuickFindUF(iter);
			break;
		default:
			LazyQuickFindUF duf = new LazyQuickFindUF(iter);
		}
		*/
		
		while(fp.hasNextLine())
		{
			String[] data = fp.nextLine().split(" ");
			//System.out.println("Data:"+data[0]);
			int p = Integer.parseInt(data[0].trim());
			int q = Integer.parseInt(data[1].trim());
			
			if(!uf.connected(p, q))
			{
				uf.union(p, q);
				//System.out.println(p+ " " + q);
			}
		}
		System.out.println(uf.connected(1, iter-1));
		uf.print();
	}

}
