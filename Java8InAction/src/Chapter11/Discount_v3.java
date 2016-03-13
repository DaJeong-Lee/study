package Chapter11;

import static Chapter11.Shop_v3.delay;
import static Chapter11.Shop_v3.format;

import Chapter11.Discount_v3.Code;
public class Discount_v3 {
	public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    public static String applyDiscount(Quote_v3 quote) {
    	//1. 기존가격에 할인코드 적용하여 할인 후 가격을 얻음
        return quote.getShopName() + " price is " + Discount_v3.apply(quote.getPrice(), quote.getDiscountCode());
    }
    private static double apply(double price, Code code) {
        delay(); //할인계산 응답지연 흉내
        return format(price * (100 - code.percentage) / 100);
    }
}
