import java.util.NoSuchElementException;


public class Subset
{

	public static void main(String[] args) 
	{
		RandomizedQueue<String> queue = new RandomizedQueue<String>();
		String s;
		int k = Integer.parseInt(args[0]);
		
		while(true)
		{
			try
			{
				s = StdIn.readString();
				queue.enqueue(s);
			}
			catch(NoSuchElementException err)
			{
				break;
			}
		}
		
		for(int i=0; i < k; i++)
			StdOut.println(queue.dequeue());
		
	}

}
