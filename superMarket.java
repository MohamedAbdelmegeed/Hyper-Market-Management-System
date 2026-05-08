
import java.time.LocalDate;
import java.util.*;

public class superMarket {

    static ArrayList<Product> inventory = new ArrayList<>();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        sales sales = new sales(inventory);
        Admin admin = new Admin();
        Marketing marketing = new Marketing(inventory);

        boolean running = true;

        while (running) {

            System.out.println("\n★★★★★★ Hyper Market System ★★★★★★");

            System.out.println("1. Add Product");
            System.out.println("2. Display Products");
            System.out.println("3. Search Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Sell Product");
            System.out.println("6. Marketing Report");
            System.out.println("7. Admin Panel");
            System.out.println("8. Exit");

            System.out.print("Choose an option: ");

            int choice = input.nextInt();

            switch (choice) {

                // Add Product
                case 1:

                    System.out.print("Enter Product ID: ");
                    int id = input.nextInt();

                    input.nextLine();

                    System.out.print("Enter Product Name: ");
                    String name = input.nextLine();

                    System.out.print("Enter Product Price: ");
                    double price = input.nextDouble();

                    System.out.print("Enter Quantity: ");
                    int quantity = input.nextInt();

                    System.out.print("Enter Minimum Stock Level: ");
                    int minStock = input.nextInt();

                    input.nextLine();

                    System.out.print("Enter Expiry Date (YYYY-MM-DD): ");
                    String expiryInput = input.nextLine();

                    LocalDate expiryDate = LocalDate.parse(expiryInput);

                    Product newProduct = new Product(
                            id,
                            name,
                            price,
                            quantity,
                            minStock,
                            expiryDate
                    );

                    inventory.add(newProduct);

                    System.out.println("Product Added Successfully!");

                    break;

                // Display Products
                case 2:

                    if (inventory.isEmpty()) {

                        System.out.println("Inventory is Empty!");
                    } else {

                        for (Product item : inventory) {

                            item.displayProduct();

                            // el stock el low warning 
                            item.checkStock();

                            // el expiry warning 
                            item.checkExpiry();
                        }
                    }

                    break;

                // Search Product
                case 3:

                    System.out.print("Enter Product ID to Search: ");

                    int searchId = input.nextInt();

                    boolean found = false;

                    for (Product item : inventory) {

                        if (item.getProductId() == searchId) {

                            item.displayProduct();

                            found = true;

                            break;
                        }
                    }

                    if (!found) {

                        System.out.println("Product Not Found!");
                    }

                    break;

                // Delete Product
                case 4:

                    System.out.print("Enter Product ID to Delete: ");

                    int deleteId = input.nextInt();

                    boolean deleted = false;

                    for (int i = 0; i < inventory.size(); i++) {

                        if (inventory.get(i).getProductId() == deleteId) {

                            inventory.remove(i);

                            deleted = true;

                            System.out.println("Product Deleted Successfully!");

                            break;
                        }
                    }

                    if (!deleted) {

                        System.out.println("Product Not Found!");
                    }

                    break;

                // Sell Product
                case 5:

                    System.out.print("Enter Product ID: ");

                    int sellId = input.nextInt();

                    System.out.print("Enter Sold Quantity: ");

                    int soldQuantity = input.nextInt();

                    // yala n2oll el sales system y3ml order 
                    sales.makeOrder(sellId, soldQuantity);

                    break;

                // Marketing Module
                case 6:

                    // nshof el offers w el reports 
                    marketing.generateReport();

                    break;

                // Admin Module
                case 7:

                    // nfta7 el admin panel 
                    admin.menu();

                    break;

                // Exit
                case 8:

                    running = false;

                    System.out.println("System Closed.");

                    break;

                default:

                    System.out.println("Invalid Choice!");
            }
        }

        input.close();
    }
}
