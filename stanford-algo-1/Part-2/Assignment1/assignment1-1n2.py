class Node:
	def __init__(self):
		self.weight = 0
		self.length = 0
		self.score = 0
		self.ratio = 0

def main():
	fname = 'jobs.txt'
	#fname = 'test.txt'
	with open(fname) as fp:
		size = fp.readline()
		data = fp.readlines()
	processes = []
	for row in data:
		[wt,ln] = map(int, row.split())
		node = Node()
		node.weight = wt
		node.length = ln
		node.score = wt-ln
		node.ratio = (wt*1.0)/ln
		processes.append(node)
	
	#Calculating part 1
	processes.sort(key = lambda proc: (proc.score, proc.weight), reverse = True)
	res=0
	time = 0
	for node in processes:
		time += node.length
		res += node.weight * time
	print "Part 1 answer = ",res

	#Calculating part 2
	processes.sort(key = lambda proc: (proc.ratio, proc.weight), reverse = True)
	res=0
	time = 0
	for node in processes:
		time += node.length
		res += node.weight * time
	print "Part 2 answer = ",res




if __name__ == '__main__':
	main()
