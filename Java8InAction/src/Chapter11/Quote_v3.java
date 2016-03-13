package Chapter11;

public class Quote_v3 {

    private final String shopName;
    private final double price;
    private final Discount_v3.Code DiscountCode;

    public Quote_v3(String shopName, double price, Discount_v3.Code DiscountCode) {
        this.shopName = shopName;
        this.price = price;
        this.DiscountCode = DiscountCode;
    }

    public static Quote_v3 parse(String s) {
        String[] split = s.split(":");
        String shopName = split[0];
        double price = Double.parseDouble(split[1]);
        Discount_v3.Code DiscountCode = Discount_v3.Code.valueOf(split[2]);
        return new Quote_v3(shopName, price, DiscountCode);
    }

    public String getShopName() {
        return shopName;
    }

    public double getPrice() {
        return price;
    }

    public Discount_v3.Code getDiscountCode() {
        return DiscountCode;
    }
}
