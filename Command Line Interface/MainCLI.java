package CLI;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class MainCLI {
    public static void main(String[] args) {
        // JDBC driver name, database URL, username and password
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/ops";
        String USER = "root";
        String PASS = "Hs19282001@@";

        Connection conn = null;
        Statement stmt = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            ResultSet rs = null;
            int choice = 0;
            Scanner scanner = new Scanner(System.in);

            do {
                System.out
                        .print("************************************************************************************");
                System.out.println("\n                          Welcome to HEALTHCARE Pharmacy");
                System.out.println("                             Command Line Interafce");
                System.out.print(
                        "************************************************************************************\n");
                System.out.println("Login Page (Choose your Role)");
                System.out.println("1. Admin");
                System.out.println("2. Doctor");
                System.out.println("3. Customer");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        Admin.main(args);
                        break;
                    case 2:
                        Doctor.main(args);
                        break;
                    case 3:
                        Customer.main(args);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (choice != 4);

            // Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // Finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
        }
    }

    static boolean authenticateUser(String username, String password, String role) {
        String DB_URL = "jdbc:mysql://localhost:3306/ops";
        String USER = "root";
        String PASS = "Hs19282001@@";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT * FROM login WHERE username=? AND password=? AND role=?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            System.out.println("Error connecting to database: " + ex.getMessage());
            return false;
        }
    }

    static class Payment{

        public static void method(double amount){
            System.out.println("\nChoose the mode of Payment:");
            System.out.println("1. Debit-card");
            System.out.println("2. NetBanking");
            System.out.println("3. Cash-on-Delivery");
            System.out.print("Enter your choice: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("You have selected Debit-card payment mode.");
                    // Code for Debit-card payment
                    System.out.print("\nEnter the card number: ");
                    String cardNumber = scanner.next();

                    System.out.print("Enter the card holder name: ");
                    String cardHolderName = scanner.next();

                    System.out.print("Enter the CVV: ");
                    String cvv = scanner.next();

                    System.out.print("Enter the expiry date (MM/YYYY): ");
                    String expiryDate = scanner.next();

                    // Add code to validate card details and process payment
                    System.out.println("Payment of $" + amount + " processed successfully with debit card ending in " + cardNumber.substring(12));
                    System.out.println("Wait for a while....");
                    System.out.println("Payment successful.");
                    System.out.println("\nThank you for shopping with us!");
                    System.out.println("Have a Nice Day!\n");
                    break;
                case 2:
                    System.out.println("You have selected NetBanking payment mode.");
                    // Code for NetBanking payment
                    String bankName, username, password;

                    // Prompt the user for payment details
                    System.out.println("Enter the payment details:");
                    System.out.print("Amount: ");
                    amount = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline character

                    System.out.print("Bank Name: ");
                    bankName = scanner.nextLine();

                    System.out.print("Username: ");
                    username = scanner.nextLine();

                    System.out.print("Password: ");
                    password = scanner.nextLine();

                    System.out.println("Payment of $" + amount + " successful!");
                    System.out.println("\nThank you for shopping with us!");
                    System.out.println("Have a Nice Day!\n");
                    break;
                case 3:
                    System.out.println("You have selected Cash-on-Delivery payment mode.");
                    // Code for Cash-on-Delivery payment
                    System.out.print("Enter your Name: ");
                    String name = scanner.next();

                    System.out.print("Enter your Phone number: ");
                    String phoneNumber = scanner.next();

                    System.out.print("Enter your Address: ");
                    String address = scanner.next();

                    // Code for calculating total amount to be paid

                    System.out.println("\nOrder summary:");
                    System.out.println("Total amount to be paid: " + amount);

                    System.out.println("\nPlease keep exact change ready.");

                    System.out.println("\nYour order will be delivered to:");
                    System.out.println("Name: " + name);
                    System.out.println("Phone number: " + phoneNumber);
                    System.out.println("Address: " + address);

                    System.out.println("\nThank you for shopping with us!");
                    System.out.println("Have a Nice Day!\n");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        }
    }

}