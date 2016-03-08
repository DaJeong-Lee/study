package Chapter14;

import java.util.function.DoubleUnaryOperator;

public class Curring {
	
	//UnaryOperator는 람다표현식이나 메소드레퍼런스를 return할 때 사용 
	static DoubleUnaryOperator curriedConverter(double f, double b){
		return (double x) -> x*f+b;
	}
	
	public static void main(String[] args) {
		
		//1. 섭씨 to 화씨
		DoubleUnaryOperator converterCtoF = curriedConverter(9.0/5, 32);
		
		//2. 환율 변환
		DoubleUnaryOperator converterUSDtoGBP = curriedConverter(0.6, 0);
		
		//3. 단위 변환
		DoubleUnaryOperator converterKmtoMi = curriedConverter(0.6214, 0);
	
		//DoubleUnaryOperator는 applyAsDouble 추상메서드를 가진 함수형 인터페이스이므로 아래와 같이 실행
		System.out.println("USD->GBP : 100 -> "+ converterUSDtoGBP.applyAsDouble(100.0) );
	}
	
}
