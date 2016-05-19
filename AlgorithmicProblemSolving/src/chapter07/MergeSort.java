package chapter07;

//병합 정렬: 배열을 다 쪼갠 후에 병합하면서 정렬함
public class MergeSort {
	
	
	public int[] mergeSort(int[] array, int left, int right){
		int mid;
	
		//분할이 다 되지 않았을 경우 if문 실행
		if(left < right){
			mid = (left+right)/2;
			System.out.println("1::");
			System.out.println("left:"+left+" right:"+right+" mid:"+mid);
			mergeSort(array, left, mid); //왼쪽 블록 분할
			
			System.out.println("2::");
			System.out.println("left:"+left+" right:"+right+" mid:"+mid);
			mergeSort(array, mid+1, right); //오른쪽 블록 분할
			
			System.out.println("3::");
			System.out.println("left:"+left+" right:"+right+" mid:"+mid);
			array = merge(array, left, mid, right); //분할된 블록 병합
			printArray(array);
		}
		
		return array;
		
	}
	
	
	private int[] merge(int[] array, int left, int mid, int right) {
		int[] tempArray = new int[array.length];
		
		int i=left, j=mid+1, k=left;
		//i는 left 인덱스, j는 right 인덱스
		//k는 결과담을 배열의 index
		
		//left부터 mid까지의 블록과 mid+1부터 right까지의 블록을 비교하는 부분
		while(i<=mid && j<=right){
			if(array[i] < array[j]){ //left값이 작으면 left값을 결과배열에 담음
				tempArray[k] = array[i];
				i++; //left인덱스 증가
			}else{
				tempArray[k] = array[j]; //아니면 right값을 결과배열에 담음
				j++;
			}
			k++; //결과배열에 담은 후 k증가
		}
		
		//left블록은 다 처리했는데 right블록이 남아있을 경우
		//right 블록값을 순차적으로 결과배열에 복사
		if(i>mid){
			for (int m = j; m <= right; m++) {
				tempArray[k] = array[m];
				k++;
			}
		}else{ //left블록이 남아있는 경우
			for (int m = i; m <= mid; m++) {
				tempArray[k] = array[m];
				k++;
			}
		}
		
		for (int m = left; m <= right; m++) {
			array[m] = tempArray[m];
		}
		
		return array;
	}


	public static void main(String[] args) {
		MergeSort merge = new MergeSort();
		
		int[] array = {3,8,0,2,1,4};
		array = merge.mergeSort(array, 0, array.length-1);
		
		merge.printArray(array);
	}
	
	public void printArray(int[] array){
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i]+",");
		}
		System.out.println();
	}
}
