package chapter21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Traversal {
	
	public static void printPostOrder(List<Integer> preorder, List<Integer> inorder){
		
		//트리에 포함된 노드의 수 
		final int N = preorder.size();
		
		//기저 사례: 텅빈 트리면 종료
		if(preorder.isEmpty()){
			return;
		}
		
		//루트는 전위 순회의 첫번째 값
		final int root = preorder.get(0);

		//왼쪽 서브트리의 크기는 중위 탐색 결과의 루트 위치를 찾아서 알아냄
		final int L = inorder.indexOf(root);

		//오른쪽 크기는 N에서 L을 빼고, root(1)를 빼면 됨
		final int R = N-L-1;
		
		//재귀 호출 실행
		//왼쪽 서브트리 -> preorder의 1~L+1, inorder는 0~L을 보냄 -> 서브트리의 루트가 root가 됨 
		printPostOrder(preorder.subList(1, L+1), inorder.subList(0, L));
		//오른쪽 서브트리 
		printPostOrder(preorder.subList(L+1, N), inorder.subList(L+1, N));
		System.out.print(root+" ");
	}
	
	public static void main(String[] args) {
		
		Traversal.printPostOrder(Arrays.asList(27,16,9,12,54,36,72),Arrays.asList(9,12,16,27,36,54,72));
		
	}
}
