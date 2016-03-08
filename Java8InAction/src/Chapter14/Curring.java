package Chapter14;

import java.util.function.DoubleUnaryOperator;

public class Curring {
	
	//UnaryOperator�� ����ǥ�����̳� �޼ҵ巹�۷����� return�� �� ��� 
	static DoubleUnaryOperator curriedConverter(double f, double b){
		return (double x) -> x*f+b;
	}
	
	public static void main(String[] args) {
		
		//1. ���� to ȭ��
		DoubleUnaryOperator converterCtoF = curriedConverter(9.0/5, 32);
		
		//2. ȯ�� ��ȯ
		DoubleUnaryOperator converterUSDtoGBP = curriedConverter(0.6, 0);
		
		//3. ���� ��ȯ
		DoubleUnaryOperator converterKmtoMi = curriedConverter(0.6214, 0);
	
		//DoubleUnaryOperator�� applyAsDouble �߻�޼��带 ���� �Լ��� �������̽��̹Ƿ� �Ʒ��� ���� ����
		System.out.println("USD->GBP : 100 -> "+ converterUSDtoGBP.applyAsDouble(100.0) );
	}
	
}
