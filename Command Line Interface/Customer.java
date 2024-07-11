package CLI;

import static CLI.MainCLI.*;
import java.sql.*;
import java.util.Scanner;

public class Customer {
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
            int quant;
            int medID;
            String Mname;
            String MfgDate;
            String ExpDate;
            String description;
            int Quantity;
            float price;
            int docID = 0;
            String Dname;
            String gender;
            String special;
            long contact;
            String address;

            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter Username(Customer):");
            String user3 = scanner.nextLine();
            System.out.println("Enter Password:");
            String pass3 = scanner.nextLine();
            String role3 = "customer";
            if (authenticateUser(user3, pass3, role3)) {
                System.out.println("Login successful!");
                System.out.println("Welcome " + user3);
                System.out.println("\nMain Menu");
                do {
                    System.out.println("1. View All Medicines");
                    System.out.println("2. Search Medicine");
                    System.out.println("3. View All Doctors");
                    System.out.println("4. Search Doctor");
                    System.out.println("5. Get Prescription");
                    System.out.println("6. Give Feedback or Ratings");
                    System.out.println("7. View Cart");
                    System.out.println("8. Track the order");
                    System.out.println("9. Exit");
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
                                System.out.println("\nDo you want to buy above medicine?");
                                System.out.println("1. Yes(or Y or y)");
                                System.out.println("2. No(or N or n)");
                                System.out.print("Enter your choice: ");
                                choice = scanner.nextInt();
                                switch (choice) {
                                    case 1:
                                        System.out.println("\nSelect Quantity: ");
                                        quant = scanner.nextInt();
                                        System.out.println("\nChoose the option:");
                                        System.out.println("1. Order Medicine");
                                        System.out.println("2. Add to cart");
                                        System.out.print("Enter your choice: ");
                                        choice = scanner.nextInt();
                                        PreparedStatement updateStmt = conn.prepareStatement(
                                                "UPDATE medicine SET Quantity=Quantity-? WHERE medID=?");
                                        updateStmt.setInt(1, quant);
                                        updateStmt.setInt(2, medID);
                                        updateStmt.executeUpdate();
                                        switch (choice) {
                                            case 1:
                                                MainCLI.Payment.method(quant*price);
                                                break;
                                            case 2:
                                                // Add to cart
                                                sql = "INSERT INTO cart (medID, name, quantity, tprice) " +
                                                        "VALUES (" + medID + ", '" + Mname + "', " + quant + ", "
                                                        + quant * price + ")";
                                                stmt.executeUpdate(sql);
                                                System.out.println("\nAdded to cart:");
                                                System.out.println(medID + ". " + Mname + " - $" + price * quant
                                                        + " - Quantity: " + quant);
                                                System.out.println("\n");
                                                break;
                                            default:
                                                System.out.println("Invalid choice. Please try again.");
                                                break;
                                        }
                                        break;
                                    case 2:
                                        // No
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                        break;
                                }
                            } else {
                                System.out.println("No medicine found with medID: " + medID);
                            }
                            break;
                        case 3:
                            // View All Doctors
                            sql = "SELECT * FROM doctor";
                            rs = stmt.executeQuery(sql);

                            System.out.println("\nAll Doctors:");
                            System.out.println("docID\t\tDname\t\t\tGender\t\t\tContact\t\t\tSpeciality\t\t\tAddress");
                            while (rs.next()) {
                                docID = rs.getInt("docID");
                                Dname = rs.getString("Dname");
                                gender = rs.getString("Gender");
                                special = rs.getString("Speciality");
                                contact = rs.getLong("Contact");
                                address = rs.getString("Address");
                                System.out.println(docID + "\t\t" + Dname + "\t\t" + gender + "\t\t" + contact + "\t\t"
                                        + special + "\t\t" + address);
                            }
                            System.out.println("\n");
                            break;
                        case 4:
                            // Search Doctor
                            System.out.print("Enter the docID of the doctor you want to search: ");
                            docID = scanner.nextInt();

                            sql = "SELECT * FROM doctor WHERE docID=" + docID;
                            rs = stmt.executeQuery(sql);
                            if (rs.next()) {
                                System.out.println("\nDoctor details:");
                                System.out.println("docID\tDname\t\t\t\tGender\t\tContact\t\tSpeciality\t\tAddress");
                                docID = rs.getInt("docID");
                                Dname = rs.getString("Dname");
                                gender = rs.getString("Gender");
                                special = rs.getString("Speciality");
                                contact = rs.getLong("Contact");
                                address = rs.getString("Address");
                                System.out.println(docID + "\t\t" + Dname + "\t" + gender + "\t" + contact + "\t"
                                        + special + "\t" + address);
                                System.out.println("\n");
                            } else {
                                System.out.println("No doctor found with docID " + docID);
                            }
                            break;
                        case 5:
                            break;
                        case 6:
                            //Feedback
                            System.out.print("Enter the Medicine ID: ");
                            medID = scanner.nextInt();

                            // Code for retrieving product details from database based on product ID
                            sql = "SELECT * FROM medicine WHERE medID=" + medID;
                            rs = stmt.executeQuery(sql);
                            while (rs.next()) {
                                medID = rs.getInt("medID");
                                Mname = rs.getString("Mname");
                                MfgDate = rs.getString("MfgDate");
                                ExpDate = rs.getString("ExpDate");
                                description = rs.getString("Description");
                                Quantity = rs.getInt("Quantity");
                                price = rs.getFloat("Price");
                                System.out.println("\nMedicine: " + Mname);
                            }

                            // Code for getting user's feedback and rating
                            System.out.println("Please provide your feedback and rating:");
                            scanner.nextLine(); // consume newline character
                            System.out.print("Feedback: ");
                            String feedback = scanner.nextLine();
                            System.out.print("Rating (out of 5): ");
                            int rating = scanner.nextInt();

                            // Code for displaying confirmation message to user
                            System.out.println("\nThank you for your feedback and rating!\n");
                            break;
                        case 7:
                            // View ACart
                            int id;
                            int med;
                            String name;
                            int quantity;
                            float total = 0;
                            sql = "SELECT * FROM cart";
                            rs = stmt.executeQuery(sql);

                            System.out.println("\nAvaliable Items in cart:");
                            System.out.println("S.No.\tMedicine ID\t\tMedicine Name\t\tQuantity\t\tTotal Amount");
                            while (rs.next()) {
                                id = rs.getInt("id");
                                med = rs.getInt("medID");
                                name = rs.getString("name");
                                quantity = rs.getInt("quantity");
                                total = rs.getFloat("tprice");
                                System.out.println(id + "\t\t" + med + "\t\t\t\t" + name + "\t\t\t\t" + quantity
                                        + "\t\t\t" + total);
                            }
                            System.out.println("\n");
                            System.out.println("Do yo want to order above medicine?");
                            System.out.print("Enter the medID of the medicine you want to order: ");
                            medID = scanner.nextInt();
                            PreparedStatement updateStmt = conn.prepareStatement("DELETE from cart WHERE medID=?");
                            updateStmt.setInt(1, medID);
                            updateStmt.executeUpdate();
                            MainCLI.Payment.method(total);
                            break;
                        case 8:
                            break;
                        case 9:
                            System.out.println("Exiting...");
                            MainCLI.main(args);
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                } while (choice != 10);
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