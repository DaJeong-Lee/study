package Chapter11;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.function.Supplier;

import static java.util.stream.Collectors.*;
import static Chapter11.ExchangeService.*;

import java.util.ArrayList;

public class ShopMain_v3 {
	List<Shop_v3> shops 
		= Arrays.asList(new Shop_v3("BestPrice"), new Shop_v3("LetsSave")
						,new Shop_v3("BestPrice2"), new Shop_v3("LetsSave4")
						,new Shop_v3("BestPrice2"), new Shop_v3("LetsSave4")
						,new Shop_v3("BuyNow23"), new Shop_v3("BuyNow")
				        ,new Shop_v3("MyFavorit"));
	
	// 1. 모든상점에 순차적으로 상점영과 가격 정보 요청하는 함수 (순차스트림)
	public List<String> findPricesSequential(String product){
		return shops.stream()
				    .map(shop -> shop.getPrice(product))
				    .map(Quote_v3::parse)
				    .map(Discount_v3::applyDiscount)
				    .collect(toList());
	}
	
	// 2. 동기작업과 비동기작업 조합하기
	public List<String> findPricesCombine(String product){
		List<CompletableFuture<String>> priceFutures = 
				shops.stream()
					 .map(shop -> CompletableFuture.supplyAsync( () -> shop.getPrice(product), executor))
					 .map(future -> future.thenApply(Quote_v3::parse))
					 .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync( () -> Discount_v3.applyDiscount(quote), executor)))
					 .collect(toList());

		/*
		 2개의 비동기 동작이 있음
		  - 상점에서 가격정보를 얻어와 Quote로 변환하기
		  - 변환된 Quote를 Discount 서비스로 전달해서 할인된 최종가격 얻기
		 
		 thenApply() 메서드 : CompletalbeFuture 후 동기작업 할때 사용 
		                    -> CompletalbeFuture가 동작을 완전히 완료한 후에 thenApply()에 전달된 람다표현시을 적용
		 thenCompose() 메서드 : 비동기 연산을 파이프 라인으로 만들수 있도록 제공된 메서드 ( 비동기이므로 메인스레드는 다른 작업을 할 수 있음, 2개의 ComletableFuture를 붙일 때 사용)
		                    -> Function을 구현( 첫번째 CompletableFuture의 반환결과를 인수로 받고, 두번째 CompletableFuture를 리턴)
		 thenComposeAsync() 메서드 존재 : Async가 안붙어 있으면 이전작업을 수행한 스레드와 같은 스레드에서 작업을 실행
		                           : Async가 있으면 다음작업이 다른 스레드에서 실행되도록 스레드 풀로 작업을 제출함
		                            -> 여기서 두번째 CompletableFuture는 첫번째의 결과를 갖고 계산하므로 Async를 쓰던 안쓰던 영향 없음 (스레드 전환 시간이 없는 thenCompose사용)
		                            
		*/
		
		return priceFutures.stream()
						   .map(CompletableFuture::join)
						   .collect(toList());
	}
	
	// 3. 독립적인 두개의 CompletableFuture 합치기 
	// 상점은 EUR(유로)로 가격을 제공하는데 소비자는 항상 달러로 가격을 봐야 한다 (2가지 독립 실행 : 상품가격 요청하는 작업과 유로와 달러의 현재환율 요청)
	public List<String> findPricesInUSD(String product){
		List<CompletableFuture<Double>> priceFutures = new ArrayList<>();
		for(Shop_v3 shop : shops){
			CompletableFuture<Double> futurePriceInUSD = 
	                CompletableFuture.supplyAsync(() -> shop.getPriceDouble(product)) // 상품가격 요청 (독립실행1)
	                .thenCombine( 
	                		CompletableFuture.supplyAsync( // 환욜 요청 (독립실행2)
		                		() ->  ExchangeService.getRate(Money.EUR, Money.USD) 
		                	)
	                		,(price, rate) -> price * rate // 2개 독립실행 후 합치기 
		             );
			priceFutures.add(futurePriceInUSD);
		}
		
		 List<String> prices = priceFutures
	                .stream()
	                .map(CompletableFuture::join)
	                .map(price -> /*shop.getName() +*/ " price is " + price)
	                .collect(toList());
		 
	    return prices;
	    
	    // 여기서 합치는 연산은 단순 곱셈이므로 별도의 테스크에서 수행하지 않음 -> thenCombineAsync대신 그냥 thenCombine을 사용
	    // 기다리는 작업이 있을 경우에는 Async를 사용하여 비동기를 쓰는게 유리
	}
	
	
	public static void main(String[] args) {
		ShopMain_v3 v3 = new ShopMain_v3();
		long start = System.nanoTime(); //시작시간 (실행시간log위해)
		System.out.println(v3.findPricesSequential("iPhone7"));
		long duration = ((System.nanoTime()-start)/1_000_000);
		System.out.println("Done in "+ duration + " msecs"); 
	}
	
	//4. 커스텀 Executor 만들기
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
