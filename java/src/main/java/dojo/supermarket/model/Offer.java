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


    public Discount getOfferDiscount(Product p,double unitPrice, double quantity){
        double basePrice = quantity * unitPrice;
        double discount = 0;
        String description = "";
        switch (offerType) {
            case TwoForAmount:
                discount = ((int) quantity >= 2) ? basePrice -getForAmountDiscount(unitPrice, (int) quantity, 2) : 0;
                description = "2 for " + argument;
                break;
            case ThreeForTwo:
                discount = ((int) quantity > 2) ? basePrice - getThreeForTwoDiscount(unitPrice, (int) quantity) : 0;
                description = "3 for 2";
                break;
            case TenPercentDiscount:
                discount = quantity * unitPrice * argument / 100.0;
                description = argument + "% off";
                break;
            case FiveForAmount:
                discount = ((int) quantity >= 5) ? basePrice -getForAmountDiscount(unitPrice, (int) quantity,5) : 0;
                description = "5 for " + argument;
                break;
        }
        if(discount != 0) {
            return new Discount(p, description, -discount);
        }
        return null;
    }

    private double getThreeForTwoDiscount(double unitPrice, int quantityAsInt) {
        return  (quantityAsInt / 3 * 2 * unitPrice) + quantityAsInt % 3 * unitPrice;
    }

    private double getForAmountDiscount(double unitPrice, int quantityAsInt, int divisor) {
        return argument * (quantityAsInt / divisor) + quantityAsInt % divisor * unitPrice;
    }


}
