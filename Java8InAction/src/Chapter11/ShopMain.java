package Chapter11;

import java.util.concurrent.Future;

public class ShopMain {
	public static void main(String[] args) {
		Shop shop = new Shop("BestShop");
		long start = System.nanoTime(); //시작시간 (실행시간log위해)
		Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
		long inocationTime = ((System.nanoTime()-start)/1_000_000); //호출시간, 밀리세컨드로 바꿈
		System.out.println("Invocation returned after "+ inocationTime+" msecs");
		
		//다른 작업 수행
		
		
	}
}
