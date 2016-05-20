package chapter08;

public class JumpGame {

	int n; //게임판크기
	int[][] board = new int[100][100];
	
	//(y,x)에서부터 맨 마지막 칸까지 도달할 수 있는지를 반환하는 method
	public boolean jump(int y, int x){
		//기저 사례: 게임판 밖을 벗어난 경우
		if(y>=n || x>=n) return false;
		//기저 사례: 마지막 칸에 도착한 경우
		if(y==n-1 && x==n-1) return true;
		
		int jumpSize = board[y][x]; //각 칸에 적힌 이동할 수 있는 숫자
		
		return jump(y+jumpSize, x)|| jump(y, x+jumpSize); //아래 또는 오른쪽으로 이동가능하니까 둘중 하나가 true면 됨
	}
	
	//jump에 메모이제이션 방식을 적용하여 만든 method, cache를 사용(값이 -1이면 계산 안한 값)
	int[][] cache = new int[100][100];
	public int jump2(int y, int x){
		//기저 사례: 게임판 밖을 벗어난 경우
		if(y>=n || x>=n) return 0;
		//기저 사례: 마지막 칸에 도착한 경우
		if(y==n-1 && x==n-1) return 1;
		
		//메모이제이션
		int result = cache[y][x];
		
		if(result!=-1) return result;
		
		int jumpSize = board[y][x];
		
		if(jump2(y+jumpSize, x) == 1){
			result = 1;
		}else if(jump2(y, x+jumpSize) == 1){
			result = 1;
		}else{
			result = 0;
		}
		cache[y][x] = result;
		
		return result;
	}
	
	public static void main(String[] args) {
		JumpGame game = new JumpGame();
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				game.board[i][j] = 5;
				game.cache[i][j] = -1;
			}
		}
		
		game.n = 7;
		//board[y][x]
		//도달할 수 있는 경우 
		game.board[0][0] = 2;
		game.board[0][2] = 1;
		game.board[1][2] = 1;
		game.board[1][3] = 2;
		game.board[3][3] = 1;
		game.board[4][3] = 3;
		game.board[4][6] = 2;
		
		//도달할 수 없는 경우
//		game.board[4][6] = 3;
		
		System.out.println(game.jump(0, 0));
		System.out.println(game.jump2(0, 0));
		
	}
}
