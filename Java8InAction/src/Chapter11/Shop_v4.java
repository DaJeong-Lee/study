package Chapter11;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop_v4 {
	private final String name;
    private static final Random random = new Random();

    public Shop_v4(String name) {
        this.name = name;
    }

	
	public static void delay(){
		try {
			Thread.sleep(1000L); //1초 지연
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void randomDelay(){
		int delay = 500 + random.nextInt(2000);
		try {
			Thread.sleep(delay); 
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
		randomDelay();
		return random.nextDouble()*product.charAt(0)+product.charAt(1); //랜덤값 리턴
	}
	
}
