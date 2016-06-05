package chapter28;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dictionary {
	//알파벳의 각 글자에 대한 인접행렬표현
	// 간선(i,j)는 알파벳 i가 j보다 앞에 와야 함을 나타냄
	int[][] adj;
	
	//주어진 단어들로부터 알파벳 간의 선후관계 그래프를 생성한다.
	public void makeGraph(List<String> words){
		adj = new int[26][100];
		for (int j = 1; j <words.size(); j++) {
			int i=j-1; //사전앞 단어 i와 뒤에 단어 j를 비교 
			int len = Math.min(words.get(i).length(), words.get(j).length());
			
			for (int k = 0; k < len; k++) {
				//각 단어에 알파벳을 하나씩 비교해서 같지 않은 게 나오면
				if(words.get(i).charAt(k) != words.get(j).charAt(k)){
					//'a'는 숫자로 97
					//알파벳 - 'a'하면 a부터 해당 알파벳이 몇번째에 있는지 알 수 있음 ('a'-'a'는 0)
					int a = words.get(i).charAt(k) - 'a'; 
					int b = words.get(j).charAt(k) - 'a';
					adj[a][b] = 1;
					break;
				}
			}
			
		}
	}
	
	public static void main(String[] args) {
		Dictionary d = new Dictionary();
		List<String> array = Arrays.asList("gg","kia","lotte","lg","hanwah");
		
		d.makeGraph(array);
		
				
	}
}
