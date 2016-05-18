package chapter04;

import java.util.ArrayList;

public class MovingAverage {
	
	//수행시간 N^2 알고리즘
	//이동평균 = weight중 최근 M개월의 평균 ( 최근 M개의 합 / M)
	public ArrayList<Double> movingAverage1(int[] weight, int M){
		ArrayList<Double> result = new ArrayList<>();
		
		int N = weight.length;
		for(int i=M-1; i<N; ++i){
			//weight[i]까지의 이동평균
			double partialSum = 0;
			for(int j=0; j<M; ++j){
				partialSum += weight[i-j];
			}
			result.add(partialSum/M);
		}
		
		return result;
	}
	
	
	//수행시간 N 알고리즘
	//최근M개의 합을 구할 때 M개씩 더하는게 아니라 앞을 빼고 새로운걸 더하는 방법으로 합을 구함 -> 이중for필요없는 방법
	public ArrayList<Double> movingAverage2(int[] weight, int M){
		ArrayList<Double> result = new ArrayList<>();
		
		int N = weight.length;
		double paritialSum = 0;
		for(int i=0; i<M-1; i++){
			paritialSum += weight[i];
		}
		
		for(int i=M-1; i<N; i++){
			paritialSum += weight[i];
			result.add(paritialSum/M);
			paritialSum -= weight[i-M+1];
		}
		
		return result;
	}
	
	
	public static void main(String[] args) {
		MovingAverage mv = new MovingAverage();
		
		int[] weight = {1,2,3,4,5,6,7};
		
		System.out.println(mv.movingAverage1(weight,3).toString());
	
		System.out.println(mv.movingAverage2(weight,3).toString());
		
		
	}
}
