package chapter04;

public class Sort {
	
	//선택정렬: 최소값을 찾아 앞에다 넣어줌 
	public int[] selectionSort(int[] numbers){
		for(int i=0; i<numbers.length; i++){
			int minIndex = i;
			for (int j = i+1 ; j < numbers.length; j++) {
				if(numbers[minIndex] > numbers[j])
					minIndex = j;
			}
			numbers = swap(numbers, i , minIndex);
		}
		
		return numbers;
	}
	
	//삽입정렬: index의 전값이랑 비교해가면서 자리바꿈
	public int[] insertSort(int[] numbers){
		for (int i = 0; i < numbers.length; i++) {
			int j = i;
			while(j>0 && numbers[j-1] > numbers[j]){
				numbers = swap(numbers, j-1, j);
				j--;
			}
		}
		
		return numbers;
	}

	public int[] swap(int[] numbers, int i, int j) {
		int temp;
		temp = numbers[i];
		numbers[i] = numbers[j];
		numbers[j] = temp;
		
		return numbers;
	}
	
	public static void main(String[] args) {
		Sort sort = new Sort();
		int[] a = {3,2,6,12,1,66,44};
		
//		a = sort.selectionSort(a);
		a = sort.insertSort(a);
		
		//대부분의 경우 삽입정렬이 선택정렬보다 빠름
		
		for (int i : a) {
			System.out.print(i +", ");
		}
	}

	
}
