count = 0

def quicksort(array,low,high):
	global count 
	if(low <0 or low>high):
		return
	count += high-low
	if(low == high):
		return array
	pivot = partition(array,low,high)
	quicksort(array,low,pivot-1)
	quicksort(array,pivot+1,high)
	return array

def partition(array,low,high):
	i = low+1
	j = low+1
	median = findMedian(array,low,high)
	array[low],array[median] = array[median],array[low]
	while(j<=high):
		if(array[low] < array[j]):
			j+=1
			continue
		else:
			array[i],array[j] = array[j],array[i]
			i+=1
			j+=1
	array[low],array[i-1] = array[i-1],array[low]
	pivot = i-1
	return pivot

#Implementation of Q2
'''
def findMedian(array,low,high):
	return high
'''

#Generic Implementation
def findMedian(array,low,high):
	a=array[low]
	c=array[high]
	if((low+high)%2 == 0):
		mid = (low+high)/2
		b=array[mid]
	else:
		mid=(low+high-1)/2
		b=array[mid]

	if ((b>a and b<c) or (b>c and b<a)) :
		return mid
	if ((c>a and c<b) or (c>b and c<a)):
		return high
	return low

def main():
	fname = 'QuickSort.txt'
	with open(fname) as f:
		content = f.readlines()
	content = map(int,content)
	content = quicksort(content,0,len(content)-1)
	print content
	print count

if __name__ == '__main__':
	main()
