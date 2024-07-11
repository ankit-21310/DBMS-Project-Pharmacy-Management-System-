package CLI;

import static CLI.MainCLI.*;
import java.sql.*;
import java.util.Scanner;

public class Doctor {
    public static void main(String[] args) {
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
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute a query
            stmt = conn.createStatement();
            String sql;
            ResultSet rs = null;

            int choice = 0;
            int cid;
            String cname;
            String gender;
            int age;
            long contact;
            String address;
            int medID;
            String Mname;
            String MfgDate;
            String ExpDate;
            String description;
            int Quantity;
            float price;

            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter Username(Doctor):");
            String user2 = scanner.nextLine();
            System.out.println("Enter Password:");
            String pass2 = scanner.nextLine();
            String role2 = "doctor";
            if (authenticateUser(user2, pass2, role2)) {
                System.out.println("Login successful!");
                System.out.println("Welcome " + user2);
                System.out.println("\nMain Menu");
                do {
                    System.out.println("1. View All Medicines");
                    System.out.println("2. View All Customers");
                    System.out.println("3. Search Medicine");
                    System.out.println("4. Search Customer");
                    System.out.println("5. Give Prescription");
                    System.out.println("6. Exit");
                    System.out.print("Enter your choice: ");
                    choice = scanner.nextInt();
                    switch (choice) {
                        case 1:
                            // View All Medicines
                            sql = "SELECT * FROM medicine";
                            rs = stmt.executeQuery(sql);

                            System.out.println("\nAll Medicines:");
                            System.out.println(
                                    "medID\t\tMname\t\t\t\tMfgDate\t\t\t\tExpDate\t\t\t\tdescription\t\t\t\tQuantity\t\t\t\tprice");
                            while (rs.next()) {
                                medID = rs.getInt("medID");
                                Mname = rs.getString("Mname");
                                MfgDate = rs.getString("MfgDate");
                                ExpDate = rs.getString("ExpDate");
                                description = rs.getString("Description");
                                Quantity = rs.getInt("Quantity");
                                price = rs.getFloat("Price");
                                System.out.println(medID + "\t\t" + Mname + "\t\t" + MfgDate + "\t\t" + ExpDate + "\t\t"
                                        + description + "\t\t" + Quantity + "\t\t" + price);
                            }
                            System.out.println("\n");
                            break;
                        case 2:
                            // View All Customers
                            sql = "SELECT * FROM customer";
                            rs = stmt.executeQuery(sql);

                            System.out.println("\nAll Customers:");
                            System.out.println("CID\t\tCname\t\t\tGender\t\t\tAge\t\t\tContact\t\t\tAddress");
                            while (rs.next()) {
                                cid = rs.getInt("CID");
                                cname = rs.getString("Cname");
                                gender = rs.getString("Gender");
                                age = rs.getInt("Age");
                                contact = rs.getLong("Contact");
                                address = rs.getString("Address");
                                System.out.println(cid + "\t\t" + cname + "\t\t" + gender + "\t\t" + age + "\t\t"
                                        + contact + "\t\t" + address);
                            }
                            break;
                        case 3:
                            // Search Medicine
                            System.out.print("Enter the medID of the medicine you want to search: ");
                            medID = scanner.nextInt();

                            sql = "SELECT * FROM medicine WHERE medID=" + medID;
                            rs = stmt.executeQuery(sql);
                            if (rs.next()) {
                                System.out.println("\nMedicine details:");
                                System.out.println(
                                        "medID\t\tMname\t\tMfgDate\t\tExpDate\t\tDescription\t\t\tQuantity\t\t\tPrice");
                                medID = rs.getInt("medID");
                                Mname = rs.getString("Mname");
                                MfgDate = rs.getString("MfgDate");
                                ExpDate = rs.getString("ExpDate");
                                description = rs.getString("Description");
                                Quantity = rs.getInt("Quantity");
                                price = rs.getFloat("Price");
                                System.out.println(medID + "\t\t" + Mname + "\t" + MfgDate + "\t" + ExpDate + "\t"
                                        + description + "\t" + Quantity + "\t\t" + price);
                                System.out.println("\n");
                            } else {
                                System.out.println("No medicine found with medID: " + medID);
                            }
                            break;
                        case 4:
                            // Search Customer
                            System.out.print("Enter the CID of the customer you want to search: ");
                            cid = scanner.nextInt();

                            sql = "SELECT * FROM customer WHERE CID=" + cid;
                            rs = stmt.executeQuery(sql);
                            if (rs.next()) {
                                System.out.println("\nCustomer details:");
                                System.out.println("CID\tCname\tGender\tAge\tContact\tAddress");
                                cid = rs.getInt("CID");
                                cname = rs.getString("Cname");
                                gender = rs.getString("Gender");
                                age = rs.getInt("Age");
                                contact = rs.getLong("Contact");
                                address = rs.getString("Address");
                                System.out.println(cid + "\t" + cname + "\t" + gender + "\t" + age + "\t" + contact
                                        + "\t" + address);
                                System.out.println("\n");
                            } else {
                                System.out.println("No customer found with CID " + cid);
                            }
                            break;
                        case 5:
                            break;
                        case 6:
                            System.out.println("Exiting...");
                            MainCLI.main(args);
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                } while (choice != 7);
            } else {
                System.out.println("Login failed.");
            }

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

}