package Chapter11;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import static java.util.stream.Collectors.*;

public class ShopMain_v2 {
	List<Shop> shops 
		= Arrays.asList(new Shop("BestPrice"), new Shop("LetsSave")
						,new Shop("BestPrice2"), new Shop("LetsSave4")
						,new Shop("BestPrice2"), new Shop("LetsSave4")
						,new Shop("BuyNow23"), new Shop("BuyNow")
				        ,new Shop("MyFavorit"));
	
	// 1. 모든상점에 순차적으로 상점영과 가격 정보 요청하는 함수 (순차스트림)
	public List<String> findPricesSequential(String product){
		return shops.stream()
				    .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
				    .collect(toList());
	}
	
	// 2. 모든상점에 병렬적으로 상점영과 가격 정보 요청하는 함수 (병렬스트림)
	public List<String> findPricesParallel(String product){
		return shops.parallelStream()
				    .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
				    .collect(toList());
	}
	
	// 3. CompletableFuture로 비동기 호출 구현하기 
	public List<String> findPricesFuture(String product){
		List<CompletableFuture<String>> priceFutures
			 = shops.stream()
					.map(shop -> CompletableFuture.supplyAsync( () -> shop.getName()+" price is "+shop.getPrice(product)))
					.collect(toList());
		
		return priceFutures.stream()
						   .map(CompletableFuture::join)
						   .collect(toList());
		
		//1) 2개의 스트림으로 분리 -> 스트림은 게으른 특성이 있으므로 하나로 처리했다면 순차적과 같은 결과
		//                 -> CompletableFuture(각각의 스레드)된 후 join해야 병렬실행
		//                 -> 메서드체인실행방식 : .A().B().C()가 있다면 한 요소에 대해 A->B->C순으로 실행하고, 다른요소로 넘어가서 다시 반복실행함
		
	}
	
	//4. Executor 사용한 CompletableFuture
	public List<String> findPricesFutureExecutor(String product){
		List<CompletableFuture<String>> priceFutures
		 = shops.stream()
				.map(shop -> CompletableFuture.supplyAsync( () -> shop.getName()+" price is "+shop.getPrice(product), executor))
				.collect(toList());
	
		return priceFutures.stream()
						   .map(CompletableFuture::join)
						   .collect(toList());
	}
	
	public static void main(String[] args) {
		ShopMain_v2 v2 = new ShopMain_v2();
		long start = System.nanoTime(); //시작시간 (실행시간log위해)
		//System.out.println(v2.findPricesSequential("iPhone7")); 
		//System.out.println(v2.findPricesParallel("iPhone7")); 
		//System.out.println(v2.findPricesFuture("iPhone7")); 
		System.out.println(v2.findPricesFutureExecutor("iPhone7")); 
		long duration = ((System.nanoTime()-start)/1_000_000); 
		System.out.println("Done in "+ duration + " msecs"); 
		// *순차실행은 약4초 (4개상점이니까)
		// *병렬실행은 약1.1초 (병렬실행시 순차실행보다 시간 많이 줄었음)
		// *CompletableFuture실행은 약2초 (병렬실행보다 느려서 실망..)
		//   ->병렬스트림은 4개 스레드로 검색중 -> 상점이 9개라면? -> CompletableFuture실행이 약간 더 빠름 (둘 다 3초정도 걸림)
		//   ->병령실행과 CompletableFuture 2가지 모두 내부적으로 'Runtime.getRuntime().availableProcessors()'의 스레드 수를 사용해서 비슷
		//   -> 하지만 CompletableFuture은 다양한 Executor로 스레드풀 크기를 조정할 수 있음 -> 성능 향상 기대
		// *Executor를 사용한 CompletableFuture실행은 1초 (원래 상점 9개에 3초였는데 완전 빨라짐)
		
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

/*
  5. 병렬스트림과 CompletableFuture 중 어떤것을 사용해야 할까?
   1) I/O가 포함되지 않은 계산 중심의 동작을 실행할 때는 스트림 인터페이스가 가장 구현하기 간단하며 효율적일 수 있다.
      (모든 스레드가 계산작업을 수행하는 상황에서는 프로세서 코어 수 이상의 스레드를 가질 필요가 없다.)
   2) I/O를 기다리는 작업을 병렬로 실행할 때는 CompletableFuture가 더 많은 유연성을 제공하며 대기/계산의 비율에 적합한 스레드 수를 
               설정할 수 있다. 특히 스트렘의 게으른 특성 때문에 스트림에서 I/O를 실제로 언제 처리할 지 예측하기 어려운 문제도 있다.  
 */
