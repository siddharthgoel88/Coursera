import time
INF = 100000000
start_time = 0.0

class Edge:
	def __init__(self):
		self.node1 = 0
		self.node2 = 0
		self.cost = 0

def timer(msg):
	global start_time
	if msg is None:
		start_time = time.time()
	else:
		if start_time is None:
			print msg, "0.00 seconds"
		else:
			print msg, (time.time() - start_time), "seconds"
		start_time = time.time()

def main():
	timer(None)
	fname = 'edges.txt'
	#fname = 'test-prims.txt'
	with open(fname) as fp:
		[vert,edges] = map(int, fp.readline().split())
		data = fp.readlines()
	graph = [[] for i in range(vert+1)]
	
	for row in data:
		eg = Edge()
		eg.node1,eg.node2,eg.cost = map(int, row.split())
		graph[eg.node1].append(eg)
		graph[eg.node2].append(eg)

	timer("Time taken in reading input and storing it =")
	x = [1]
	t = []
	res = 0
	count = 0

	for i in range(vert-1):
		mn = INF
		obj = None
		minNode = 0
		for node in x:
			for conns in graph[node]:
				sec = conns.node2
				if(conns.node2 == node):
					sec = conns.node1
				if (sec not in x):
					if(conns.cost < mn):
						mn = conns.cost
						minNode = sec
		res += mn
		x.append(minNode)
	
	timer("Time taken in calculating the MST through Prim's algorithm =")
	print res


if __name__ == '__main__':
	main()
