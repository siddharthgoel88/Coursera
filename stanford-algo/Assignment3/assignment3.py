'''
***********************
Karger's Mincut Problem
***********************

Test files named kargerMinCut.txt and small-test-case are stored along with this code. The file contains the adjacency list representation of a simple undirected graph. There are 200 vertices labeled 1 to 200. The first column in the file represents the vertex label, and the particular row (other entries except the first column) tells all the vertices that the vertex is adjacent to. So for example, the 6th row looks like : "6	155	56	52	120	......". This just means that the vertex with label 6 is adjacent to (i.e., shares an edge with) the vertices with labels 155,56,52,120,......,etc

Your task is to code up and run the randomized contraction algorithm for the min cut problem and use it on the above graph to compute the min cut. (HINT: Note that you'll have to figure out an implementation of edge contractions. Initially, you might want to do this naively, creating a new graph from the old every time there's an edge contraction. But you should also think about more efficient implementations.) (WARNING: As per the video lectures, please make sure to run the algorithm many times with different random seeds, and remember the smallest cut that you ever find.) Write your numeric answer in the space provided. So e.g., if your answer is 5, just type 5 in the space provided.
'''

import pdb
import random
seed = 1947

def kargerMinCut(verAdlist):
	numVertex = len(verAdlist)
	nodes = list(range(1,numVertex))
	while len(nodes)>2 :
		#print "Node length",len(nodes),nodes
		randNode1 = random.choice(nodes)
		#print "RandNode1 List", verAdlist[randNode1]
		randNode2 = random.choice(verAdlist[randNode1])
		#print "Test",randNode1,randNode2
		nodes.remove(randNode2)
		verAdlist[randNode1][:] = [x for x in verAdlist[randNode1] if x != randNode2]
		verAdlist[randNode2][:] = [x for x in verAdlist[randNode2] if x != randNode1]
		verAdlist[randNode1] += verAdlist[randNode2]
		for subnode in verAdlist[randNode2]:
			for i in range(len(verAdlist[subnode])):
				if(verAdlist[subnode][i] == randNode2):
					verAdlist[subnode][i] = randNode1
			
		#print randNode1,randNode2
	print len(verAdlist[nodes[0]]),len(verAdlist[nodes[1]])
	return len(verAdlist[nodes[0]])

def unsharedCopy(origList):
	if isinstance(origList, list):
		return list( map(unsharedCopy, origList))
	return origList


def main():
	global seed
	fname = 'kargerMinCut.txt'
	#fname = 'small-test-case'
	verAdlist = []
	nodes = []
	verAdlist.insert(0,nodes)
	mincut = 100000000
	with open(fname) as fp:
		for line in fp:
			nodes = map(int,line.split())
			index = nodes[0]
			nodes = nodes[1:]
			nodes.sort()
			verAdlist.insert(index,nodes)
	for i in range(len(verAdlist)):
		temp = unsharedCopy(verAdlist)
		random.seed(seed)
		cut = kargerMinCut(temp)
		if(cut < mincut):
			mincut = cut
		seed += 1
	print "Mincut:",mincut

if __name__ == '__main__':
	main()
