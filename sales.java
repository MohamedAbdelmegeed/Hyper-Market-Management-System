import java.util.ArrayList;

public class sales {

    private ArrayList<Product> products;

    public sales(ArrayList<Product> products) {
        this.products = products;
    }

    public void makeOrder(int id, int qty) {

        for (Product p : products) {

            if (p.getProductId() == id) {

                if (p.getQuantity() >= qty) {
                    p.setQuantity(p.getQuantity() - qty);
                    System.out.println("Order completed");
                } else {
                    System.out.println("Not enough stock");
                }

                return;
            }
        }

        System.out.println("Product not found");
    }
}