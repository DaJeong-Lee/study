package Chapter11;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop_v2 {
	private final String name;
    private final Random random;

    public Shop_v2(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

	
	public static void delay(){
		try {
			Thread.sleep(1000L); //1초 지연
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	//1. 동기API
	public double getPrice(String product){
		return calculatePrice(product);
	}
	
	private double calculatePrice(String product){
		delay();
		return random.nextDouble()*product.charAt(0)+product.charAt(1); //랜덤값 리턴
	}
	
	//2. 비동기API (동기API를 비동기API로 고침. new Thread 사용)
	public Future<Double> getPriceAsync( String product){
		//1) 계산결과를 포함할 CompletableFuture 생성
		CompletableFuture<Double> futurePrice = new CompletableFuture<>();
		new Thread( () -> {
			try{
				//2) 새로운 thread에서 비동기적으로 계산실행
				double price = calculatePrice(product);
				//3) 오랜시간이 걸리는 계산이 완료되면 Future에 값을 설정
				futurePrice.complete(price);
			}catch(Exception ex){
				//5)계산도중 에러가 발생하면 발생한 에러를 포함시켜 Future 종료
				futurePrice.completeExceptionally(ex);
			}
		}).start();
		//4) 계산결과가 완료되길 기다리지 않고 Future를 반환
		return futurePrice;
	}
	
	//3. 위의 비동기API를  팩토리메서드 supplyAsync로 CompletableFuture 만들기
	public Future<Double> getPriceSupplyAsync( String product){
		// 1) supplyAsync 인자로 함수형 인터페이스 Supplier 필요 ( 인자없고, 리턴있는 추상메서드 get() 존재 )
		// 새로운 thread 생성해서 calculatePrice()계산하고 완료되면 CompletableFuture에 담아 리턴 
		return CompletableFuture.supplyAsync(() -> calculatePrice(product) );
	}
}
