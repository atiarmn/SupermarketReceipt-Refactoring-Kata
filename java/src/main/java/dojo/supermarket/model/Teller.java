package dojo.supermarket.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teller {

    private final SupermarketCatalog catalog;
    private Map<Product, Offer> offers = new HashMap<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(SpecialOfferType offerType, Product product, double argument) {
        this.offers.put(product, new Offer(offerType, product, argument));
    }

    public Receipt checksOutArticlesFrom(ShoppingCart theCart) {
        Receipt receipt = new Receipt();
        addProductToReceipt(receipt, theCart.getItems());
        theCart.handleOffers(receipt, this.offers, this.catalog);
        return receipt;
    }

    private void addProductToReceipt(Receipt receipt, List<ProductQuantity> productQuantities) {
        for (ProductQuantity pq: productQuantities) {
            Product p = pq.getProduct();
            receipt.addProduct(p, pq.getQuantity(), this.catalog.getUnitPrice(p));
        }
    }

}
