/* Implementing Deque (Double-ended queue) as part of 
 * assignment of Princeton Algorithms - I
 * 
 *  @author: Siddharth Goel
 */

import java.lang.NullPointerException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>
{
	private Node first,last;	// Points to first and last element of the queue
	private int count; 			// Keeps the count of total number of elements in the deque
	
	private class Node
	{
		Item elem;
		Node next;
		Node prev;
		
		private Node(Item item)
		{
			elem = item;
			next = null;
			prev = null;
		}
	}
	
	public Deque()                           // construct an empty deque
	{
		first = null;
		last = null;
		count = 0;
	}
	
	public boolean isEmpty()                 // is the deque empty?
	{
		return size() == 0;
	}
	
	public int size()                        // return the number of items on the deque
	{
		return count;
	}
	
	public void addFirst(Item item)          // insert the item at the front
	{
		if (item == null)
			throw new NullPointerException("Null cannot be added to the queue");
		
		Node node = new Node(item);
		if (first == null)
		{
			first = node;
			last = node;
		}
		else
		{
			node.next = first;
			first.prev = node;
			first = node;
		}
		count++;
	}
	
	public void addLast(Item item)           // insert the item at the end
	{
		if (item == null)
			throw new NullPointerException("Null cannot be added to the queue");
		
		Node node = new Node(item);
		if (first == null)
		{
			first = node;
			last = node;
		}
		else
		{
			last.next = node;
			node.prev = last;
			last = node;
		}
		count++;
	}
	
	public Item removeFirst()                // delete and return the item at the front
	{
		if(isEmpty())
			throw new NoSuchElementException("Queue already empty!! Cannot perform delete operation.");

		Node oldFirst = first;
		Item deleted;
		
		if (size() == 1)
		{
			first = null;
			last = null;
		}
		else
		{
			first = first.next;
			first.prev = null;
		}
		
		count--;
		deleted = oldFirst.elem;
		oldFirst = null;
		return deleted;
	}
	
	public Item removeLast()                 // delete and return the item at the end
	{
		if(isEmpty())
			throw new NoSuchElementException("Queue already empty!! Cannot perform delete operation.");
		
		Node oldLast = last;
		Item deleted;
		
		if(size() == 1)
		{
			first = null;
			last = null;
		}
		else
		{
			last = last.prev;
			last.next = null;
		}
		
		count--;
		deleted = oldLast.elem;
		oldLast = null;
		return deleted;
	}
	
	public Iterator<Item> iterator()         // return an iterator over items in order from front to end
	{
		return new DequeIterator();   
	}
	
	private class DequeIterator implements Iterator<Item>
	{
		private Node current = first;
		
		public boolean hasNext()
		{
			return current != null;
		}
		
		public void remove()
		{
			throw new UnsupportedOperationException("remove() method is not implemented.");
		}
		
		public Item next()
		{
			if(!hasNext())
				throw new NoSuchElementException("Either the queue is empty or you have iterated to the last element of the queue.");
			
			Item item = current.elem;
			current = current.next;
			return item;
		}
	}
	
	public static void main(String[] args)   // unit testing
	{
		   Deque<String> queue = new Deque<String>();
		   //queue.addFirst("Hello");
		   //queue.addLast("World");
		   StdOut.println("Queue size = " + queue.size());
		   for (String string : queue) {
			   StdOut.println(string+"\t");
		}
	}
}
