#NOTE: Implementation is not exactly as told. Hence the number of iteration are different. But the array is getting sorted. Third implementation is the general one. Used that one to submit the correct implemtation of both Q2 and Q3.

count = 0
c1 =0

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
	global c1
	i = low
	j = low
	while(j<high):
		c1 += 1
		if(array[high] < array[j]):
			j+=1
			continue
		else:
			array[i],array[j] = array[j],array[i]
			i+=1
			j+=1
	array[high],array[i] = array[i],array[high]
	pivot = i
	return pivot

def main():
	global c1
	fname = 'QuickSort.txt'
	with open(fname) as f:
		content = f.readlines()
	content = map(int,content)
	content = quicksort(content,0,len(content)-1)
	print content
	print count
	print c1

if __name__ == '__main__':
	main()
