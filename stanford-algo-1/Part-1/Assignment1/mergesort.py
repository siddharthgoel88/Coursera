def mergesort(array,size):
	if(size <= 1):
		return array
	mid = size/2
	left_array = array[:mid]
	right_array = array[mid:]
	left_sorted = mergesort(left_array, len(left_array))
	right_sorted = mergesort(right_array, len(right_array))
	full_sorted = merge(left_sorted,right_sorted)
	return full_sorted

def merge(left,right):
	lsize = len(left)
	rsize = len(right)
	i = 0
	j = 0
	k = 0
	merged = [None]*(lsize+rsize)
	while (i<lsize and j<rsize):
		if(left[i] < right[j]):
			merged[k] = left[i]
			k=k+1
			i=i+1
		elif(left[i] >= right[j]):
			merged[k] = right[j]
			k=k+1
			j=j+1
	if(i==lsize):
		while(j<rsize):
			merged[k]=right[j]
			k=k+1
			j=j+1
	else:
		while(i<lsize):
			merged[k]=left[i]
			i=i+1
			k=k+1
	return merged

fname = 'IntegerArray.txt'
with open(fname) as f:
    content = f.readlines()

content = map(int, content)
array = mergesort(content,len(content))
print array

