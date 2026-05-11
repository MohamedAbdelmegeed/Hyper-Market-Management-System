import java.util.*;
import java.io.*;

class Employee {

    int id;
    String name;
    String password;
    String type;

    Employee(int id, String name, String password, String type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.type = type;
    }
}

public class AdminModule {

    ArrayList<Employee> employees = new ArrayList<>();
    String adminPassword = "1234";

    Scanner sc = new Scanner(System.in);

    public AdminModule() {
        loadEmployeesFromFile();
    }

    public void menu() {

        while (true) {

            System.out.println("\n ADMIN PANEL");
            System.out.println("1. Add Employee");
            System.out.println("2. List Employees");
            System.out.println("3. Delete Employee");
            System.out.println("4. Change Password");
            System.out.println("5. Back");

            System.out.print("--> Choice: ");
            int choice = sc.nextInt();

            if (choice == 1)
                addEmployee();
            else if (choice == 2)
                listEmployees();
            else if (choice == 3)
                deleteEmployee();
            else if (choice == 4)
                changePassword();
            else
                break;
        }
    }

    public void addEmployee() {

        System.out.println("\n+ ADD EMPLOYEE");

        System.out.print("ID: ");
        int id = sc.nextInt();

        System.out.print("Name: ");
        String name = sc.next();

        System.out.print("Password: ");
        String pass = sc.next();

        System.out.println("Select Role:");
        System.out.println("1. admin");
        System.out.println("2. user");
        System.out.println("3. employee");
        System.out.println("4. manager");

        int r = sc.nextInt();

        String type = switch (r) {
            case 1 -> "admin";
            case 2 -> "user";
            case 3 -> "employee";
            case 4 -> "manager";
            default -> "user";
        };

        employees.add(new Employee(id, name, pass, type));

        saveEmployeesToFile();

        System.out.println("✔ Employee added");
    }

    public void listEmployees() {

        System.out.println("\n EMPLOYEES:");

        if (employees.isEmpty()) {
            System.out.println("No employees.");
            return;
        }

        for (Employee e : employees)
            System.out.println(e.id + " | " + e.name + " | " + e.type);
    }

    public void deleteEmployee() {

        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();

        employees.removeIf(e -> e.id == id);

        saveEmployeesToFile();

        System.out.println("✔ Done");
    }

    public void changePassword() {

        System.out.print("Old password: ");
        String old = sc.next();

        if (old.equals(adminPassword)) {
            System.out.print("New password: ");
            adminPassword = sc.next();
            System.out.println("✔ Updated");
        } else {
            System.out.println("❌ Wrong password");
        }
    }

    // SAVE
    public void saveEmployeesToFile() {

        try {

            PrintWriter writer = new PrintWriter("employees.txt");

            for (Employee e : employees) {
                writer.println(e.id + "," + e.name + "," + e.password + "," + e.type);
            }

            writer.close();

        } catch (Exception e) {
            System.out.println("❌ Error saving employees file");
        }
    }

    // LOAD
    public void loadEmployeesFromFile() {

        try {

            File file = new File("employees.txt");

            if (!file.exists())
                return;

            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {

                String[] data = fileReader.nextLine().split(",");

                employees.add(new Employee(
                        Integer.parseInt(data[0]),
                        data[1],
                        data[2],
                        data[3]
                ));
            }

            fileReader.close();

        } catch (Exception e) {
            System.out.println("❌ Error loading employees file");
        }
    }
}