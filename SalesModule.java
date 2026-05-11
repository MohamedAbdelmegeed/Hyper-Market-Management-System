import java.util.*;
import java.io.*;

public class SalesModule {

    Scanner sc = new Scanner(System.in);

    public void menu(ArrayList<Product> products, UserModule user, MarketingModule marketing) {

        while (true) {

            System.out.println("\n SALES");
            System.out.println("1. Make Order");
            System.out.println("2. List Products");
            System.out.println("3. Search Product");
            System.out.println("4. Back");

            System.out.print("--> Choice: ");
            int c = sc.nextInt();

            if (c == 1)
                makeOrder(products, user, marketing);

            else if (c == 2)
                listProducts(products);

            else if (c == 3)
                searchProduct(products);

            else
                break;
        }
    }

    // ==========================
    // LIST PRODUCTS
    // ==========================

    public void listProducts(ArrayList<Product> products) {

        System.out.println("\n====================================");
        System.out.println(" AVAILABLE PRODUCTS");
        System.out.println("====================================");

        if (products.isEmpty()) {

            System.out.println("❌ No products available");
            return;
        }

        for (Product p : products) {

            System.out.println(
                    "ID: " + p.id +
                            " | " + p.name +
                            " | Price: $" + p.price +
                            " | Qty: " + p.quantity
            );
        }

        System.out.println("====================================");
    }

    // ==========================
    // SEARCH PRODUCT
    // ==========================

    public void searchProduct(ArrayList<Product> products) {

        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();

        boolean found = false;

        for (Product p : products) {

            if (p.id == id) {

                found = true;

                System.out.println("\n====================================");
                System.out.println(" PRODUCT DETAILS");
                System.out.println("====================================");

                System.out.println("ID       : " + p.id);
                System.out.println("Name     : " + p.name);
                System.out.println("Price    : $" + p.price);
                System.out.println("Quantity : " + p.quantity);

                System.out.println("====================================");
            }
        }

        if (!found) {

            System.out.println("❌ Product not found");
        }
    }

    // ==========================
    // MAKE ORDER
    // ==========================

    public void makeOrder(ArrayList<Product> products, UserModule user, MarketingModule marketing) {

        // AVAILABLE PRODUCTS
        System.out.println("\n====================================");
        System.out.println(" AVAILABLE PRODUCTS");
        System.out.println("====================================");

        for (Product p : products) {

            System.out.println(
                    "ID: " + p.id +
                            " | " + p.name +
                            " | Price: $" + p.price +
                            " | Qty: " + p.quantity
            );
        }

        System.out.println("====================================");

        System.out.print("Product ID: ");
        int id = sc.nextInt();

        System.out.print("Quantity: ");
        int q = sc.nextInt();

        boolean found = false;

        for (Product p : products) {

            if (p.id == id) {

                found = true;

                if (p.quantity >= q) {

                    p.quantity -= q;

                    // ==========================
                    // SAVE UPDATED INVENTORY
                    // ==========================

                    try {

                        PrintWriter writer =
                                new PrintWriter("products.txt");

                        for (Product pr : products) {

                            writer.println(
                                    pr.id + "," +
                                            pr.name + "," +
                                            pr.price + "," +
                                            pr.quantity + "," +
                                            pr.minStock + "," +
                                            pr.expiry
                            );
                        }

                        writer.close();

                    } catch (Exception e) {

                        System.out.println(
                                "❌ Error updating inventory file"
                        );
                    }

                    // ==========================
                    // OFFER LOGIC
                    // ==========================

                    Offer offer =
                            marketing.getOfferForProduct(p.name);

                    double original = p.price;
                    double finalPrice = original;
                    double discount = 0;

                    if (offer != null) {

                        discount = offer.discount;

                        finalPrice =
                                original -
                                        (original * discount / 100);
                    }

                    double total = finalPrice * q;

                    // ==========================
                    // ORDER SUMMARY
                    // ==========================

                    System.out.println("\n ORDER SUMMARY");

                    System.out.println(
                            "Product: " + p.name
                    );

                    System.out.println(
                            "Price before: $" + original
                    );

                    if (offer != null) {

                        System.out.println(
                                "Discount: " + discount + "%"
                        );

                        System.out.println(
                                "Price after: $" + finalPrice
                        );

                    } else {

                        System.out.println(
                                "No active offer"
                        );

                        System.out.println(
                                "Price after: $" + finalPrice
                        );
                    }

                    System.out.println("Qty: " + q);

                    System.out.println(
                            "✔ Total = " + total
                    );

                    // ==========================
                    // SAVE PURCHASE HISTORY
                    // ==========================

                    user.addPurchase(
                            p.name,
                            q,
                            total
                    );

                    // ==========================
                    // SAVE SALES REPORT
                    // ==========================

                    try {

                        PrintWriter invoice =
                                new PrintWriter(
                                        new FileWriter(
                                                "sales_report.txt",
                                                true
                                        )
                                );

                        invoice.println(
                                "===================================="
                        );

                        invoice.println(
                                "          SALES INVOICE"
                        );

                        invoice.println(
                                "===================================="
                        );

                        invoice.println(
                                "Product        : " + p.name
                        );

                        invoice.println(
                                "Original Price : $" +
                                        original
                        );

                        if (offer != null) {

                            invoice.println(
                                    "Discount       : " +
                                            discount + "%"
                            );

                        } else {

                            invoice.println(
                                    "Discount       : No Offer"
                            );
                        }

                        invoice.println(
                                "Final Price    : $" +
                                        finalPrice
                        );

                        invoice.println(
                                "Quantity       : " + q
                        );

                        invoice.println(
                                "Total          : $" + total
                        );

                        invoice.println(
                                "Date           : " +
                                        java.time.LocalDateTime.now()
                        );

                        invoice.println(
                                "====================================\n"
                        );

                        invoice.close();

                    } catch (Exception e) {

                        System.out.println(
                                "❌ Error saving sales report"
                        );
                    }

                    System.out.println(
                            " Thank you for your purchase!"
                    );

                } else {

                    System.out.println(
                            "❌ Not enough stock"
                    );
                }
            }
        }

        if (!found) {

            System.out.println(
                    "❌ Product not found"
            );
        }
    }
}