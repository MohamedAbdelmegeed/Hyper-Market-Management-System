
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Product {

    private int productId;
    private String name;
    private double price;
    private int quantity;
    private int minimumStockLevel;
    private LocalDate expiryDate;

    public Product(int productId, String name, double price,
            int quantity, int minimumStockLevel,
            LocalDate expiryDate) {

        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.minimumStockLevel = minimumStockLevel;
        this.expiryDate = expiryDate;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void displayProduct() {
        System.out.println(productId + " | " + name + " | " + price + " | " + quantity);
    }

    public void checkStock() {
        if (quantity <= minimumStockLevel) {
            System.out.println("⚠ LOW STOCK WARNING");
        }
    }

    public void checkExpiry() {
        long days = ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);

        if (days <= 30 && days >= 0) {
            System.out.println("EXPIRY SOON"); 
        }else if (days < 0) {
            System.out.println("EXPIRED");
        }
    }
}
