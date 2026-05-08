
import java.util.ArrayList;

public class Marketing {

    private ArrayList<Product> products;

    public Marketing(ArrayList<Product> products) {
        this.products = products;
    }

    public void generateReport() {

        System.out.println("\n--- MARKETING REPORT ---");

        for (Product p : products) {
            System.out.println(
                    p.getName() + " | Price: " + p.getPrice()
                    + " | Qty: " + p.getQuantity()
            );
        }
    }
}
