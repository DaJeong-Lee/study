package Chapter11;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop {
	private final String name;
    private final Random random;

    public Shop(String name) {
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
		CompletableFuture<Double> futurePrice = new CompletableFuture<>();
		new Thread( () -> {
			try{
				double price = calculatePrice(product);
				futurePrice.complete(price);
			}catch(Exception ex){
				futurePrice.completeExceptionally(ex);
			}
		}).start();
		return futurePrice;
	}
}
