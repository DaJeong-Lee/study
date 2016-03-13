package Chapter11;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop_v3 {
	private final String name;
    private final Random random;

    public Shop_v3(String name) {
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
	
	private static final DecimalFormat formatter = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
	public static double format(double number) {
        synchronized (formatter) {
            return new Double(formatter.format(number));
        }
    }

	public String getPrice(String product){
		double price = calculatePrice(product);
		//Discount 코드 랜덤으로 리턴
		Discount_v3.Code code = Discount_v3.Code.values()[random.nextInt(Discount_v3.Code.values().length)];
		return  String.format("%s:%.2f:%s", name, price, code);
	}
	
	public double getPriceDouble(String product) {
        return calculatePrice(product);
    }
	
	private double calculatePrice(String product){
		delay();
		return random.nextDouble()*product.charAt(0)+product.charAt(1); //랜덤값 리턴
	}
	
}
