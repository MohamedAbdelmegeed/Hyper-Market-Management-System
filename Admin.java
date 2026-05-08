import java.util.ArrayList;
import java.util.Scanner;

public class Admin {

    private static ArrayList<String> employees = new ArrayList<>();
    Scanner input = new Scanner(System.in);

    public void menu() {

        while (true) {

            System.out.println("\n--- ADMIN PANEL ---");
            System.out.println("1. Add Employee");
            System.out.println("2. List Employees");
            System.out.println("3. Delete Employee");
            System.out.println("4. Back");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Employee Name: ");
                    employees.add(input.nextLine());
                    break;

                case 2:
                    System.out.println(employees);
                    break;

                case 3:
                    System.out.print("Name: ");
                    employees.remove(input.nextLine());
                    break;

                case 4:
                    return;
            }
        }
    }
}