count = 0
c2 = 0

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
	global c2
	i = low+1
	j = low+1
	while(j<=high):
		c2 += 1
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

def main():
	fname = 'QuickSort.txt'
	with open(fname) as f:
		content = f.readlines()
	content = map(int,content)
	print len(content)
	content = quicksort(content,0,len(content)-1)
	print content
	print count
	print c2

if __name__ == '__main__':
	main()
