import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item>
{
	private Item[] array;
	private int count;
	private int first;
	private int last;
	
	@SuppressWarnings("unchecked")
	public RandomizedQueue()                 // construct an empty randomized queue
	{
		count = 0;
		first = -1;
		last = -1;
		array = (Item[]) new Object[2];
	}
	
	public boolean isEmpty()                 // is the queue empty?
	{
		return size()==0;			
	}
	
	public int size()                        // return the number of items on the queue
	{
		return count;
	}
	
	public void enqueue(Item item)           // add the item
	{
		if (item == null)
			throw new NullPointerException("Null cannot be added to the queue.");
			
		if(count == array.length)
			resize(array.length*2);
		
		if(first == -1 && last == -1)
		{
			first = 0;
			last = 0;
			array[last] = item;
		}
		else
		{
			last = (last+1)%array.length;
			array[last] = item;
		}
		count++;
	}
	
	private void resize(int length)
	{
		if(length < size())
			throw new IllegalStateException("The queue has "+ size() + 
					" elements and you are trying to resize the array size to "+ 
					length + ", which is obviously illegal.");
		
		@SuppressWarnings("unchecked")
		Item[] temp = (Item[]) new Object[length];
		for (int i = 0; i < size(); i++)
			temp[i] = array[(first+i)%array.length];
		
		array = temp;
		first = 0;
		last = size()-1;
	}
	
	public Item dequeue()                    // delete and return a random item
	{
		if (isEmpty())
			throw new NoSuchElementException("Queue is already empty!");
		
		Item deleted;
		
		shuffle();
		
		deleted = array[first];
		array[first] = null;
		if (size() == 1)
		{
			first = -1;
			last = -1;
		}
		else
			first = (first + 1)% array.length;
		
		count--;
		
		if(size() > 0 && size() == (array.length/4))
			resize(array.length/2);
		
		return deleted;
	}
	
	private void shuffle() 
	{
		if (first <= last)
			StdRandom.shuffle(array, first, last);
		else
		{
			StdRandom.shuffle(array, 0, last);
			StdRandom.shuffle(array, first, array.length-1);
		}
		
	}

	public Item sample()                     // return (but do not delete) a random item
	{
		int rand = StdRandom.uniform(size());
		rand = (rand+first)%array.length;
		return array[rand];
	}
	
	public Iterator<Item> iterator()         // return an independent iterator over items in random order
	{
		return new RandomQueueIterator();
	}
	
	private class RandomQueueIterator implements Iterator<Item>
	{
		int index;
		
		public RandomQueueIterator()
		{
			index = 0;
			if(!isEmpty())
				shuffle();
		}
		
		public boolean hasNext()
		{
			return (index != size());
		}
		
		public void remove()
		{
			throw new UnsupportedOperationException("remove() method is not implemented.");
		}

		@Override
		public Item next() 
		{
			if(!hasNext())
				throw new NoSuchElementException("Queue empty or already iterated to the last element");
			
			return array[(index++ + first)%array.length];
		}
	}
	
	public static void main(String[] args)   // unit testing
	{
		RandomizedQueue<String> rqueue = new RandomizedQueue<String>();
		rqueue.enqueue("Hello");
		
		rqueue.enqueue("World");
		StdOut.println("Remove --> " + rqueue.dequeue());
		StdOut.println("Remove --> " + rqueue.dequeue());
		
		for (String string : rqueue) {
			StdOut.println(string + "\t");
			
		}
	}
}