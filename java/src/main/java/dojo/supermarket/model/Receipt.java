package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private List<ReceiptItem> items = new ArrayList<>();
    private List<Discount> discounts = new ArrayList<>();

    public Double getTotalPrice() {
        return getItemsPrice() + getDiscountsAmount();
    }

    private double getDiscountsAmount() {
        double total = 0.0;
        for (Discount discount : this.discounts) {
            total += discount.getDiscountAmount();
        }
        return total;
    }

    private double getItemsPrice() {
        double total = 0.0;
        for (ReceiptItem item : this.items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void addProduct(Product p, double quantity, double price) {
        this.items.add(new ReceiptItem(p, quantity, price, quantity * price));
    }

    public List<ReceiptItem> getItems() {
        return new ArrayList<>(this.items);
    }

    public void addDiscount(Discount discount) {
        this.discounts.add(discount);
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }
}
