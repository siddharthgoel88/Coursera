'''
*******************************************
Inversion Counting using Divide and Conquer
*******************************************

IntegerArray.txt contains all of the 100,000 integers between 1 and 100,000 (inclusive) in some order, with no integer repeated.

Your task is to compute the number of inversions in the file given, where the ith row of the file indicates the ith entry of an array.
Because of the large size of this array, you should implement the fast divide-and-conquer algorithm covered in the video lectures. The numeric answer for the given input file should be typed in the space below.
So if your answer is 1198233847, then just type 1198233847 in the space provided without any space / commas / any other punctuation marks. You can make up to 5 attempts, and we'll use the best one for grading.
(We do not require you to submit your code, so feel free to use any programming language you want --- just type the final numeric answer in the following space.)

[TIP: before submitting, first test the correctness of your program on some small test files or your own devising. Then post your best test cases to the discussion forums to help your fellow students!]
'''
#Divide and Conquer technique (Piggybacking Mergesort)
def sort_and_count(array,size):
	if(size <= 1):
		return [array,0]
	mid = size/2
	left_array = array[:mid]
	right_array = array[mid:]
	[left_sorted,left_inv_count] = sort_and_count(left_array, len(left_array))
	[right_sorted,right_inv_count] = sort_and_count(right_array, len(right_array))
	[full_sorted,split_inv_count] = count_split_inv(left_sorted,right_sorted)
	return [full_sorted,left_inv_count+right_inv_count+split_inv_count]

def count_split_inv(left,right):
	inv_count=0
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
			inv_count += lsize-i
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
	return [merged,inv_count]

#End of Divide and Conquer


#Brute Force method
def brute_force_inv_count(content):
	for i in range(0,len(content)):
		for j in range(i+1, len(content)):
			if(content[i]>content[j]):
				count = count+1
	return count
#End of Brute Force method

def main():
	#fname = 'hw1_test'
	fname = 'IntegerArray.txt'
	with open(fname) as f:
		content = f.readlines()
	count = 0
	content = map(int, content)
	[content,result] = sort_and_count(content,len(content))
	print result
	
if __name__ == '__main__':
        main()

