package Chapter11;

import java.util.concurrent.Future;

public class ShopMain {
	public static void main(String[] args) {
		Shop shop = new Shop("BestShop");
		long start = System.nanoTime(); //시작시간 (실행시간log위해)
		//1) 상점에 제품 가격정보 요청 
		Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
		long inocationTime = ((System.nanoTime()-start)/1_000_000); //호출시간, 밀리세컨드로 바꿈
		System.out.println("Invocation returned after "+ inocationTime+" msecs");
		
		//다른 작업 수행
		try {
			//2) 가격정보가 있으면 Future에서 가격정보를 읽고, 없으면 가격정보를 리턴받을 때까지 블록(대기)
			// 블록이 있으므로 성능문제 발생 -> 비블록 코드 만들기(2가지방법 : 병렬스트림, CompletableFutre로 비동기 호출) -> 버전2(v2)에 있음
			// 블록코드인 getPrice를 사용할 수 밖에 없는 상황에서 비동기적으로 여러상점에 질의하는 방법
			double price = futurePrice.get();
			System.out.printf("Price is %.2f%n", price);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		long retrievalTime = ((System.nanoTime()-start)/1_000_000);
		System.out.println("Price returned after "+retrievalTime+" msecs");
	}
}
