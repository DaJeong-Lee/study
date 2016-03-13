package Chapter11;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static Chapter11.ExchangeService.*;

import java.util.ArrayList;

public class ShopMain_v4 {
	List<Shop_v4> shops 
		= Arrays.asList(new Shop_v4("BestPrice"), new Shop_v4("LetsSave")
						,new Shop_v4("BestPrice2"), new Shop_v4("LetsSave4")
						,new Shop_v4("BestPrice2"), new Shop_v4("LetsSave4")
						,new Shop_v4("BuyNow23"), new Shop_v4("BuyNow")
				        ,new Shop_v4("MyFavorit"));
	
	public Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote_v3::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount_v3.applyDiscount(quote), executor)));
    }
	
	 public void printPricesStream(String product) {
	        long start = System.nanoTime();
	        CompletableFuture[] futures = findPricesStream(product)
	                .map(f -> f.thenAccept(s -> System.out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
	                .toArray(size -> new CompletableFuture[size]);
	        
	        CompletableFuture.allOf(futures).join();
	        System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
	        
	        //thenAccept() 메서드 : 연산결과가 끝난 후 값을 소비하기 위해 사용 -> 완료되는 즉시 println 해주기 위해
	        //CompletableFuture.allOf(futures).join(); : 모든 CompletableFuture가 완료된 후 실행
	    }
	
	public static void main(String[] args) {
		ShopMain_v4 v4 = new ShopMain_v4();
		long start = System.nanoTime(); //시작시간 (실행시간log위해)
		v4.printPricesStream("iPhone7");
		long duration = ((System.nanoTime()-start)/1_000_000);
		System.out.println("Done in "+ duration + " msecs"); 
	}
	
	
	
	private final Executor executor = 
			//상점 수와 100중 작은 값으로 스레드 풀 생성
			Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r);
					t.setDaemon(true); //데몬스레드: 일반스레드는 실행중이면 자바 종료불가능, 데몬스레드는 자바가 종료될 때 강제 종료 가능
					return t;
				}
			});
	
}
