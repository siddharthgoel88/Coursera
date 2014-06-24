public class EagerQuickFindUF 
{

	private  int[] id;
	
	public EagerQuickFindUF (int N) 
	{
		id = new int[N];
		for (int i=0; i<N; i++)
			id[i] = i;
	}
	
	public boolean connected(int p, int q)
	{
		return id[p]==id[q];
	}
	
	public void union(int p, int q)
	{
		int pid = id[p];
		int qid = id[q];
		for (int i=0; i<id.length; i++)
			if(id[i] == pid) id[i]=qid;
	}
	
	public void print()
	{
		for(int i=0; i<id.length;i++)
			System.out.print(id[i]+" ");
	}
}