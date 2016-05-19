package chapter07;

import java.util.ArrayList;

//퀵 정렬: 배열을 분할하는 과정에서 Sort시킴 -> 쪼갤때 pivot의 자리를 찾아줌
public class QuickSort {
	
	public void quickSort(int[] array, int length) {
		quick(array, 0, length-1);
	}

	public void quick(int[] array, int left, int right) {
		if(left >= right){
			//printArray(array);
			return;
		}
		
		int low = left+1; //피벗옆
		int high = right;
		int pivot = array[left];
		
		while(low<=high){
			
			//피벗값보다 크거나 같은 값까지 low증가
			while(low<=right && array[low]<=pivot){
				low++;
			}
			
			//피벗값보다 작거나 같은 값까지 high감소
			while(left+1<=high && pivot <= array[high]){
				high--;
			}
			
			//low보다 high가 크거나 같으면 두 값을 서로 교환
			if(low<=high){
				int temp = array[low];
				array[low] = array[high];
				array[high] = temp;
			}else{
				//high가 low보다 크면 
				//pivot값이랑 array[high]값이랑 교환
				array[left] = array[high];
				array[high] = pivot;
			}
		}
		
		//이 시점에서 array[high](즉,pivot)값은 고정됨: 앞은 pivot보다 작은값, 뒤는 pivot보다 큰값
		quick(array, left, high-1);
		quick(array, high+1, right);
		
	}

	public void printArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i]+", ");
		}
		System.out.println();
	}
	

	public static void main(String[] args) {
		int[] data = {3,7,8,5,2,1,9,11,4};
		QuickSort sort = new QuickSort();
		
		sort.quickSort(data, data.length);
		sort.printArray(data);
		
		ArrayList<Integer> a = new ArrayList<>();
	}
	
}
