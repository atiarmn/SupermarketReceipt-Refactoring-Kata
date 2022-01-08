package dojo.supermarket.model;

public class Offer {
    SpecialOfferType offerType;
    private final Product product;
    double argument;

    public Offer(SpecialOfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }

    Product getProduct() {
        return this.product;
    }


    public double getDiscountAmount(double unitPrice, double quantity){
        double basePrice = quantity * unitPrice;
        double discount = 0;
        switch (offerType) {
            case TwoForAmount:
                discount = ((int) quantity >= 2) ? getForAmountDiscount(unitPrice, (int) quantity, 2) : 0;
                break;
            case ThreeForTwo:
                discount = ((int) quantity > 2) ? getThreeForTwoDiscount(unitPrice, (int) quantity) : 0;
                break;
            case TenPercentDiscount:
                return quantity * unitPrice * argument / 100.0;
            case FiveForAmount:
                discount = ((int) quantity >= 5) ? getForAmountDiscount(unitPrice, (int) quantity,5) : 0;
                break;
        }
        return basePrice - discount;
    }

    private double getThreeForTwoDiscount(double unitPrice, int quantityAsInt) {
        return  (quantityAsInt / 3 * 2 * unitPrice) + quantityAsInt % 3 * unitPrice;
    }

    private double getForAmountDiscount(double unitPrice, int quantityAsInt, int divisor) {
        return argument * (quantityAsInt / divisor) + quantityAsInt % divisor * unitPrice;
    }

    public String getDiscountDescription(){
        switch (offerType) {
            case TwoForAmount:
                return "2 for " + argument;
            case ThreeForTwo:
                return "3 for 2";
            case TenPercentDiscount:
                return argument + "% off";
            case FiveForAmount:
                return "5 for " + argument;
            default:
                return "";
        }
    }
    public Discount getOfferDiscount(Product p,double unitPrice, double quantity){
        if(getDiscountAmount(unitPrice,quantity) == 0)
            return null;
        return new Discount(p, getDiscountDescription(), -getDiscountAmount(unitPrice,quantity));
    }

}
