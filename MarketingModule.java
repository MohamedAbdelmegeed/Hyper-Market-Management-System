import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


// OOP for adding functions of the system everything
class Offer {
    String productName;
    double originalPrice;
    double discount;
    double finalPrice;
    LocalDateTime startTime;
    int durationDays;

    // define the functions so i can call them alone with the system it works
    Offer(String productName, double originalPrice, double discount, int days, LocalDateTime startTime) {
        this.productName = productName;
        this.originalPrice = originalPrice;
        this.discount = discount;
        this.durationDays = days;
        this.startTime = startTime;
        this.finalPrice = originalPrice - (originalPrice * discount / 100);
    }

    // checks if the time is expired yet or no
    public boolean isExpired() {
        LocalDateTime expiryTime = startTime.plus(durationDays, ChronoUnit.DAYS);
        return LocalDateTime.now().isAfter(expiryTime);
    }

    // display the remaining time of the offer when is going to end
    public String getRemainingTime() {
        if (isExpired()) return "EXPIRED";
        LocalDateTime expiryTime = startTime.plus(durationDays, ChronoUnit.DAYS);
        Duration res = Duration.between(LocalDateTime.now(), expiryTime);
        return String.format("%d Days, %d Hours, %d Minutes", res.toDays(), res.toHoursPart(), res.toMinutesPart());
    }

    // display everything about the offer product etc.
    public void show() {
        System.out.println("------------------------------------");
        System.out.println("Product     : " + productName);
        System.out.println("Discount    : " + discount + "% OFF");
        System.out.println("Offer Price : $" + (isExpired() ? originalPrice : finalPrice));
        System.out.println("Time Left   : " + getRemainingTime());
        System.out.println("------------------------------------");
    }
}

// saves the offer in the list
public class MarketingModule {
    ArrayList<Offer> offers = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    // saves the offers in the file so i can see print it like a report
    public MarketingModule() {
        loadOffersFromFile();
    }

    public void menu(ArrayList<Product> products) {
        while (true) {
            System.out.println("\n MARKETING MENU");
            System.out.println("1 Create Offer");
            System.out.println("2 Show Offers & Timers");
            System.out.println("3 Query Specific Offer (Search)");
            System.out.println("4 Generate Marketing Report");
            System.out.println("5 Back");

            int c = sc.nextInt();
            if (c == 1) createOffer(products);
            else if (c == 2) showOffers();
            else if (c == 3) queryOffers();
            else if (c == 4) saveOffersToFile();
            else break;
        }
    }

    // here is the creating offer it display all the products you got and you look at them and create the amount of offers you want with the times in days
    public void createOffer(ArrayList<Product> products) {
        System.out.println("\n Products Available:");
        for (Product p : products) System.out.println(p.id + " | " + p.name + " | $" + p.price);
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();
        Product selected = null;
        for (Product p : products) if (p.id == id) selected = p;

        if (selected == null) { System.out.println("❌ Not found"); return; }
        System.out.print("Discount %: ");
        double discount = sc.nextDouble();
        System.out.print("Duration (Days): ");
        int days = sc.nextInt();

        offers.add(new Offer(selected.name, selected.price, discount, days, LocalDateTime.now()));
        saveOffersToFile();
        System.out.println("✔ Offer created.");
    }

    public void showOffers() {
        if (offers.isEmpty()) {
            System.out.println("No offers found.");
            return;
        }
        for (Offer o : offers) o.show();
    }


    // here is the query i can search the product name to see the all the product and offers
    public void queryOffers() {
        System.out.print("Enter Product Name to Search: ");
        String name = sc.next();
        boolean found = false;
        for (Offer o : offers) {
            if (o.productName.equalsIgnoreCase(name)) {
                o.show();
                found = true;
            }
        }
        if (!found) System.out.println("❌ No offers found for " + name);
    }

    // here is get the saved function of the file and print it in the file text
    public void saveOffersToFile() {
        try {
            // Data file for the system
            PrintWriter writer = new PrintWriter("offers.txt");
            PrintWriter report = new PrintWriter("offers_report.txt");

            report.println("=== MARKETING STATUS REPORT ===");
            report.println("Generated on: " + LocalDateTime.now());
            report.println("------------------------------------");

            for (Offer o : offers) {

                // Save raw data
                writer.println(o.productName + "," + o.originalPrice + "," + o.discount + "," + o.finalPrice + "," + o.durationDays + "," + o.startTime);

                // Write to human-readable report
                String status = o.isExpired() ? "[EXPIRED]" : "[ACTIVE]";
                report.println(status + " Product: " + o.productName + " | Time Left: " + o.getRemainingTime());
            }
            writer.close();
            report.close();
            System.out.println("✔ Report generated and saved to offers_report.txt");
        } catch (Exception e) { System.out.println("❌ Error saving"); }
    }


    // here creating the file so i can see it and print it
    public void loadOffersFromFile() {
        try {
            File file = new File("offers.txt");
            if (!file.exists()) return;
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                String[] data = fileReader.nextLine().split(",");
                Offer o = new Offer(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]), Integer.parseInt(data[4]), LocalDateTime.parse(data[5]));
                o.finalPrice = Double.parseDouble(data[3]);
                offers.add(o);
            }
            fileReader.close();
        } catch (Exception e) {}
    }

    //This is the way that sends offers to other modules
    public Offer getOfferForProduct(String productName) {
        for (Offer o : offers) {
            if (o.productName.equalsIgnoreCase(productName) && !o.isExpired()) return o;
        }
        return null;
    }
}