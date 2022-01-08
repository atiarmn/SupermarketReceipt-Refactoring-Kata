package dojo.supermarket;

import dojo.supermarket.model.*;

import java.util.Locale;

public class ReceiptPrinter {

    private final int columns;
    private StringBuilder receiptText = new StringBuilder();

    public ReceiptPrinter() {
        this(40);
    }

    public ReceiptPrinter(int columns) {
        this.columns = columns;
    }

    public String printReceipt(Receipt receipt) {
        addReceiptItems(receipt);
        addReceiptDiscounts(receipt);
        addTotalPrice(receipt);
        return receiptText.toString();
    }

    private void addTotalPrice(Receipt receipt) {
        receiptText.append("\n");
        receiptText.append(presentTotal(receipt));
    }

    private void addReceiptDiscounts(Receipt receipt) {
        for (Discount discount : receipt.getDiscounts()) {
            String discountPresentation = presentDiscount(discount);
            receiptText.append(discountPresentation);
        }
    }

    private void addReceiptItems(Receipt receipt) {
        for (ReceiptItem item : receipt.getItems()) {
            String receiptItem = presentReceiptItem(item);
            receiptText.append(receiptItem);
        }
    }

    private String presentReceiptItem(ReceiptItem item) {
        String totalPricePresentation = presentPrice(item.getTotalPrice());
        String name = item.getProduct().getName();

        String line = formatLineWithWhitespace(name, totalPricePresentation);

        if (item.getQuantity() != 1) {
            line += "  " + presentPrice(item.getPrice()) + " * " + presentQuantity(item) + "\n";
        }
        return line;
    }

    private String presentDiscount(Discount discount) {
        String name = discount.getDescription() + "(" + discount.getProduct().getName() + ")";
        String value = presentPrice(discount.getDiscountAmount());

        return formatLineWithWhitespace(name, value);
    }

    private String presentTotal(Receipt receipt) {
        String name = "Total: ";
        String value = presentPrice(receipt.getTotalPrice());
        return formatLineWithWhitespace(name, value);
    }

    private String formatLineWithWhitespace(String name, String value) {
        StringBuilder line = new StringBuilder();
        line.append(name);
        int whitespaceSize = this.columns - name.length() - value.length();
        for (int i = 0; i < whitespaceSize; i++) {
            line.append(" ");
        }
        line.append(value);
        line.append('\n');
        return line.toString();
    }

    private static String presentPrice(double price) {
        return String.format(Locale.UK, "%.2f", price);
    }

    private static String presentQuantity(ReceiptItem item) {
        return ProductUnit.Each.equals(item.getProduct().getUnit())
                ? String.format("%x", (int)item.getQuantity())
                : String.format(Locale.UK, "%.3f", item.getQuantity());
    }

}
