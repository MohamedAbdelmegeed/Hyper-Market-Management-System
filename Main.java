import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserModule user = new UserModule();
        AdminModule admin = new AdminModule();
        InventoryModule inventory = new InventoryModule();
        MarketingModule marketing = new MarketingModule();
        SalesModule sales = new SalesModule();

        System.out.println("\n====================================");
        System.out.println(" HYPER MARKET MANAGEMENT SYSTEM");
        System.out.println("====================================");

        while (true) {

            System.out.print("\n Enter Username: ");
            String username = sc.nextLine();

            System.out.print(" Enter Password: ");
            String password = sc.nextLine();

            System.out.print(" Enter Role (admin/user/employee/manager): ");
            String role = sc.nextLine();

            boolean valid = false;

            // 🔹 DEFAULT LOGIN
            if (role.equals("admin")) {
                if (username.equals("admin") && password.equals("1234"))
                    valid = true;
            }
            else if (role.equals("user")) {
                if (username.equals("user") && password.equals("1111"))
                    valid = true;
            }

            // 🔹 EMPLOYEE FILE LOGIN
            String employeeRole = checkEmployeeLogin(username, password);

            if (employeeRole != null) {
                valid = true;
                role = employeeRole;
            }

            if (!valid) {
                System.out.println("\n❌ INVALID CREDENTIALS");
                continue;
            }

            System.out.println("\n✔ LOGIN SUCCESSFUL");
            user.login(username);

            boolean running = true;

            while (running) {

                System.out.println("\n====================================");
                System.out.println(" MAIN MENU");
                System.out.println("====================================");

                System.out.println("1 Admin Panel");
                System.out.println("2 Inventory");
                System.out.println("3 Sales / Marketing");
                System.out.println("4 Logout");
                System.out.println("5 Exit");

                System.out.print("--> Choose: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1:
                        if (role.equals("admin"))
                            admin.menu();
                        else
                            System.out.println("❌ Access Denied");
                        break;

                    case 2:
                        if (role.equals("admin") || role.equals("employee") || role.equals("manager"))
                            inventory.menu();
                        else
                            System.out.println("❌ Access Denied");
                        break;

                    case 3:
                        if (role.equals("admin") || role.equals("manager"))
                            marketing.menu(inventory.getProducts());
                        else
                            sales.menu(inventory.getProducts(), user, marketing);
                        break;

                    case 4:
                        System.out.println("Logging out...");
                        running = false;
                        break;

                    case 5:
                        System.out.println("System shutting down...");
                        return;
                }
            }
        }
    }

    //  EMPLOYEE LOGIN CHECK
    public static String checkEmployeeLogin(String username, String password) {

        try {

            File file = new File("employees.txt");

            if (!file.exists())
                return null;

            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {

                String[] data = sc.nextLine().split(",");

                String name = data[1];
                String pass = data[2];
                String role = data[3];

                if (name.equals(username) && pass.equals(password)) {
                    sc.close();
                    return role;
                }
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Error reading employees file");
        }

        return null;
    }
}