package CLI;

import static CLI.MainCLI.*;
import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Admin {
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
            int medID;
            String Mname;
            String MfgDate;
            String ExpDate;
            String description;
            int Quantity;
            float price;

            Scanner scanner = new Scanner(System.in);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            System.out.print("Enter Username(Admin): ");
            String user = scanner.next();
            System.out.print("Enter Password: ");
            String pass = scanner.next();
            String role = "employee";

            if (authenticateUser(user, pass, role)) {
                System.out.println("Login successful!");
                System.out.println("Welcome " + user);
                System.out.println("\nMain Menu");
                do {
                    System.out.println("1. Manage Medicine");
                    System.out.println("2. Manage Customer");
                    System.out.println("3. Manage Doctor");
                    System.out.println("4. Exit");
                    choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            do {
                                System.out.println("1. Add Medicine");
                                System.out.println("2. Update Medicine");
                                System.out.println("3. Delete Medicine");
                                System.out.println("4. View All Medicines");
                                System.out.println("5. Search Medicine");
                                System.out.println("6. Back");
                                System.out.print("Enter your choice: ");
                                choice = scanner.nextInt();
                                switch (choice) {
                                    case 1:
                                        // Add Medicine
                                        System.out.println("\nEnter Medicine details:");
                                        System.out.print("medID: ");
                                        medID = scanner.nextInt();
                                        scanner.nextLine(); // consume the newline character
                                        System.out.print("Mname: ");
                                        Mname = scanner.nextLine();
                                        System.out.print("MfgDate: ");
                                        MfgDate = scanner.nextLine();
                                        // LocalDate Mdate = LocalDate.parse(MfgDate, formatter);
                                        System.out.print("ExpDate: ");
                                        ExpDate = scanner.nextLine();
                                        // LocalDate Edate = LocalDate.parse(ExpDate, formatter);
                                        System.out.print("Description: ");
                                        description = scanner.nextLine();
                                        System.out.print("Quantity: ");
                                        Quantity = scanner.nextInt();
                                        scanner.nextLine(); // consume the newline character
                                        System.out.print("Price: ");
                                        price = scanner.nextFloat();
                                        scanner.nextLine(); // consume the newline character

                                        sql = "INSERT INTO medicine (medID, Mname, MfgDate, ExpDate, description, Quantity, price) "
                                                +
                                                "VALUES (" + medID + ", '" + Mname + "', '" + MfgDate + "', '" + ExpDate
                                                + "', '" + description + "', " + Quantity + ", " + price + ")";
                                        stmt.executeUpdate(sql);
                                        System.out.println("Medicine added successfully.");
                                        break;
                                    case 2:
                                        // Update Medicine
                                        System.out.print("Enter the medID of the medicine you want to update: ");
                                        medID = scanner.nextInt();
                                        scanner.nextLine(); // consume the newline character

                                        sql = "SELECT * FROM medicine WHERE medID=?";
                                        PreparedStatement selectStmt = conn.prepareStatement(sql);
                                        selectStmt.setInt(1, medID);
                                        rs = selectStmt.executeQuery();

                                        PreparedStatement updateStmt = conn.prepareStatement(
                                                "UPDATE medicine SET Mname=?, MfgDate=?, ExpDate=?, description=?, Quantity=?, Price=? WHERE medID=?");
                                        if (rs.next()) {
                                            System.out.println(
                                                    "\nEnter new details for the medicine (or press Enter to leave a field unchanged):");

                                            System.out.print("Mname (" + rs.getString("Mname") + "): ");
                                            String mnew = scanner.nextLine();
                                            if (mnew.isEmpty()) {
                                                updateStmt.setString(1, rs.getString("Mname"));
                                            } else {
                                                updateStmt.setString(1, mnew);
                                            }

                                            System.out.print("MfgDate (" + rs.getString("MfgDate") + "): ");
                                            String mdate = scanner.nextLine();
                                            if (mdate.isEmpty()) {
                                                updateStmt.setString(2, rs.getString("MfgDate"));
                                            } else {
                                                LocalDate date1 = LocalDate.parse(mdate, formatter);
                                                updateStmt.setDate(2, Date.valueOf(date1));
                                            }

                                            System.out.print("ExpDate (" + rs.getString("ExpDate") + "): ");
                                            String edate = scanner.nextLine();
                                            if (edate.isEmpty()) {
                                                updateStmt.setString(3, rs.getString("ExpDate"));
                                            } else {
                                                LocalDate date2 = LocalDate.parse(edate, formatter);
                                                updateStmt.setDate(3, Date.valueOf(date2));
                                            }

                                            System.out.print("Description (" + rs.getString("description") + "): ");
                                            String des = scanner.nextLine();
                                            if (des.isEmpty()) {
                                                updateStmt.setString(4, rs.getString("description"));
                                            } else {
                                                updateStmt.setString(4, des);
                                            }

                                            System.out.print("Quantity (" + rs.getLong("Quantity") + "): ");
                                            String quan = scanner.nextLine();
                                            if (quan.isEmpty()) {
                                                updateStmt.setInt(5, rs.getInt("Quantity"));
                                            } else {
                                                int num = Integer.parseInt(quan);
                                                updateStmt.setInt(5, num);
                                            }

                                            System.out.print("Price (" + rs.getFloat("Price") + "): ");
                                            String pri = scanner.nextLine();
                                            if (pri.isEmpty()) {
                                                updateStmt.setFloat(6, rs.getFloat("Price"));
                                            } else {
                                                float num1 = Float.parseFloat(pri);
                                                updateStmt.setFloat(6, num1);
                                            }

                                            updateStmt.setInt(7, medID);
                                            updateStmt.executeUpdate();
                                            System.out.println("Medicine updated successfully.\n");
                                        } else {
                                            System.out.println("No medicine found with medID " + medID);
                                        }
                                        break;
                                    case 3:
                                        // Delete Medicine
                                        System.out.print("Enter the medID of the medicine you want to delete: ");
                                        medID = scanner.nextInt();

                                        sql = "SELECT * FROM medicine WHERE medID=" + medID;
                                        rs = stmt.executeQuery(sql);
                                        if (rs.next()) {
                                            sql = "DELETE FROM medicine WHERE medID=" + medID;
                                            stmt.executeUpdate(sql);
                                            System.out.println("Medicine deleted successfully.");
                                        } else {
                                            System.out.println("No medicine found with medID " + medID);
                                        }
                                        break;
                                    case 4:
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
                                            System.out.println(medID + "\t\t" + Mname + "\t\t" + MfgDate + "\t\t"
                                                    + ExpDate + "\t\t" + description + "\t\t" + Quantity + "\t\t"
                                                    + price);
                                        }
                                        System.out.println("\n");
                                        break;
                                    case 5:
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
                                            System.out.println(medID + "\t\t" + Mname + "\t\t" + MfgDate + "\t\t"
                                                    + ExpDate + "\t\t" + description + "\t\t" + Quantity + "\t\t\t"
                                                    + price);
                                            System.out.println("\n");
                                        } else {
                                            System.out.println("No medicine found with medID: " + medID);
                                        }
                                        break;
                                    case 6:
                                        // Exit
                                        System.out.println("\nBack to the Main Menu");
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                        break;
                                }
                            } while (choice != 6);
                            break;
                        case 2:
                            do {
                                System.out.println("1. Update Customer");
                                System.out.println("2. Delete Customer");
                                System.out.println("3. View All Customers");
                                System.out.println("4. Search Customer");
                                System.out.println("5. Back");
                                System.out.print("Enter your choice: ");
                                choice = scanner.nextInt();
                                int cid;
                                String cname;
                                String gender;
                                int age;
                                long contact;
                                String address;
                                switch (choice) {
                                    // case 0:
                                    // // Add Customer
                                    // System.out.println("\nEnter Customer details:");
                                    // System.out.print("CID: ");
                                    // int cid = scanner.nextInt();
                                    // scanner.nextLine(); // consume the newline character
                                    // System.out.print("Cname: ");
                                    // String cname = scanner.nextLine();
                                    // System.out.print("Gender: ");
                                    // String gender = scanner.nextLine();
                                    // System.out.print("Age: ");
                                    // int age = scanner.nextInt();
                                    // scanner.nextLine(); // consume the newline character
                                    // System.out.print("Contact: ");
                                    // long contact = scanner.nextLong();
                                    // scanner.nextLine(); // consume the newline character
                                    // System.out.print("Address: ");
                                    // String address = scanner.nextLine();
                                    //
                                    // sql = "INSERT INTO customer (CID, Cname, Gender, Age, Contact, Address) " +
                                    // "VALUES (" + cid + ", '" + cname + "', '" + gender + "', " + age + ", " +
                                    // contact + ", '" + address + "')";
                                    // stmt.executeUpdate(sql);
                                    // System.out.println("Customer added successfully.");
                                    // break;
                                    case 1:
                                        // Update Customer
                                        System.out.print("Enter the CID of the customer you want to update: ");
                                        cid = scanner.nextInt();
                                        scanner.nextLine(); // consume the newline character

                                        sql = "SELECT * FROM customer WHERE CID=?";
                                        PreparedStatement selectStmt = conn.prepareStatement(sql);
                                        selectStmt.setInt(1, cid);
                                        rs = selectStmt.executeQuery();

                                        PreparedStatement updateStmt = conn.prepareStatement(
                                                "UPDATE customer SET Cname=?, Gender=?, Age=?, Contact=?, Address=? WHERE CID=?");
                                        if (rs.next()) {
                                            System.out.println(
                                                    "\nEnter new details for the customer (or press Enter to leave a field unchanged):");

                                            System.out.print("Cname (" + rs.getString("Cname") + "): ");
                                            String cnew = scanner.nextLine();
                                            if (cnew.isEmpty()) {
                                                updateStmt.setString(1, rs.getString("Cname"));
                                            } else {
                                                updateStmt.setString(1, cnew);
                                            }

                                            System.out.print("Gender (" + rs.getString("Gender") + "): ");
                                            String gnew = scanner.nextLine();
                                            if (gnew.isEmpty()) {
                                                updateStmt.setString(2, rs.getString("Gender"));
                                            } else {
                                                updateStmt.setString(2, gnew);
                                            }

                                            System.out.print("Age (" + rs.getInt("Age") + "): ");
                                            String ageStr = scanner.nextLine();
                                            if (ageStr.isEmpty()) {
                                                updateStmt.setInt(3, rs.getInt("Age"));
                                            } else {
                                                int age1 = Integer.parseInt(ageStr);
                                                updateStmt.setInt(3, age1);
                                            }

                                            System.out.print("Contact (" + rs.getLong("Contact") + "): ");
                                            String contactStr = scanner.nextLine();
                                            if (contactStr.isEmpty()) {
                                                updateStmt.setLong(4, rs.getLong("Contact"));
                                            } else {
                                                long contact1 = Long.parseLong(contactStr);
                                                updateStmt.setLong(4, contact1);
                                            }

                                            System.out.print("Address (" + rs.getString("Address") + "): ");
                                            String add = scanner.nextLine();
                                            if (add.isEmpty()) {
                                                updateStmt.setString(5, rs.getString("Address"));
                                            } else {
                                                updateStmt.setString(5, add);
                                            }

                                            updateStmt.setInt(6, cid);
                                            updateStmt.executeUpdate();
                                            System.out.println("Customer updated successfully.\n");
                                        } else {
                                            System.out.println("No customer found with CID " + cid);
                                        }
                                        break;
                                    case 2:
                                        // Delete Customer
                                        System.out.print("Enter the CID of the customer you want to delete: ");
                                        cid = scanner.nextInt();

                                        sql = "SELECT * FROM customer WHERE CID=" + cid;
                                        rs = stmt.executeQuery(sql);
                                        if (rs.next()) {
                                            sql = "DELETE FROM customer WHERE CID=" + cid;
                                            stmt.executeUpdate(sql);
                                            System.out.println("Customer deleted successfully.");
                                        } else {
                                            System.out.println("No customer found with CID " + cid);
                                        }
                                        break;
                                    case 3:
                                        // View All Customers
                                        sql = "SELECT * FROM customer";
                                        rs = stmt.executeQuery(sql);

                                        System.out.println("\nAll Customers:");
                                        System.out
                                                .println("CID\t\tCname\t\t\tGender\t\t\tAge\t\t\tContact\t\t\tAddress");
                                        while (rs.next()) {
                                            cid = rs.getInt("CID");
                                            cname = rs.getString("Cname");
                                            gender = rs.getString("Gender");
                                            age = rs.getInt("Age");
                                            contact = rs.getLong("Contact");
                                            address = rs.getString("Address");
                                            System.out.println(cid + "\t\t" + cname + "\t\t" + gender + "\t\t" + age
                                                    + "\t\t" + contact + "\t\t" + address);
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
                                            System.out.println(cid + "\t" + cname + "\t" + gender + "\t" + age + "\t"
                                                    + contact + "\t" + address);
                                            System.out.println("\n");
                                        } else {
                                            System.out.println("No customer found with CID " + cid);
                                        }
                                        break;
                                    case 5:
                                        // Exit
                                        System.out.println("\nBack to the Main Menu");
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                        break;
                                }
                            } while (choice != 5);
                            break;
                        case 3:
                            do {
                                System.out.println("1. Update Doctor");
                                System.out.println("2. Delete Doctor");
                                System.out.println("3. View All Doctors");
                                System.out.println("4. Search Doctor");
                                System.out.println("5. Back");
                                System.out.print("Enter your choice: ");
                                choice = scanner.nextInt();
                                int docID;
                                String Dname;
                                String gender;
                                String special;
                                long contact;
                                String address;
                                switch (choice) {
                                    case 1:
                                        // Update Doctor
                                        System.out.print("Enter the docID of the doctor you want to update: ");
                                        docID = scanner.nextInt();
                                        scanner.nextLine(); // consume the newline character

                                        sql = "SELECT * FROM doctor WHERE docID=?";
                                        PreparedStatement selectStmt = conn.prepareStatement(sql);
                                        selectStmt.setInt(1, docID);
                                        rs = selectStmt.executeQuery();

                                        PreparedStatement updateStmt = conn.prepareStatement(
                                                "UPDATE doctor SET Dname=?, Gender=?, Contact=?, Speciality=?, Address=? WHERE docID=?");
                                        if (rs.next()) {
                                            System.out.println(
                                                    "\nEnter new details for the doctor (or press Enter to leave a field unchanged):");

                                            System.out.print("Dname (" + rs.getString("Dname") + "): ");
                                            String dnew = scanner.nextLine();
                                            if (dnew.isEmpty()) {
                                                updateStmt.setString(1, rs.getString("Dname"));
                                            } else {
                                                updateStmt.setString(1, dnew);
                                            }

                                            System.out.print("Gender (" + rs.getString("Gender") + "): ");
                                            String gen = scanner.nextLine();
                                            if (gen.isEmpty()) {
                                                updateStmt.setString(2, rs.getString("Gender"));
                                            } else {
                                                updateStmt.setString(2, gen);
                                            }

                                            System.out.print("Contact (" + rs.getLong("Contact") + "): ");
                                            String contactStr = scanner.nextLine();
                                            if (contactStr.isEmpty()) {
                                                updateStmt.setLong(3, rs.getLong("Contact"));
                                            } else {
                                                long contact1 = Long.parseLong(contactStr);
                                                updateStmt.setLong(3, contact1);
                                            }

                                            System.out.print("Speciality (" + rs.getString("Speciality") + "): ");
                                            String spec = scanner.nextLine();
                                            if (spec.isEmpty()) {
                                                updateStmt.setString(4, rs.getString("Speciality"));
                                            } else {
                                                updateStmt.setString(4, spec);
                                            }

                                            System.out.print("Address (" + rs.getString("Address") + "): ");
                                            String ad = scanner.nextLine();
                                            if (ad.isEmpty()) {
                                                updateStmt.setString(5, rs.getString("Address"));
                                            } else {
                                                updateStmt.setString(5, ad);
                                            }

                                            updateStmt.setInt(6, docID);
                                            updateStmt.executeUpdate();
                                            System.out.println("Doctor updated successfully.\n");
                                        } else {
                                            System.out.println("No doctor found with docID " + docID);
                                        }
                                        break;
                                    case 2:
                                        // Delete Doctor
                                        System.out.print("Enter the docID of the doctor you want to delete: ");
                                        docID = scanner.nextInt();

                                        sql = "SELECT * FROM doctor WHERE docID=" + docID;
                                        rs = stmt.executeQuery(sql);
                                        if (rs.next()) {
                                            sql = "DELETE FROM doctor WHERE docID=" + docID;
                                            stmt.executeUpdate(sql);
                                            System.out.println("Doctor deleted successfully.");
                                        } else {
                                            System.out.println("No doctor found with docID " + docID);
                                        }
                                        break;
                                    case 3:
                                        // View All Doctors
                                        sql = "SELECT * FROM doctor";
                                        rs = stmt.executeQuery(sql);

                                        System.out.println("\nAll Doctors:");
                                        System.out.println(
                                                "docID\t\tDname\t\t\tGender\t\t\tContact\t\t\tSpeciality\t\t\tAddress");
                                        while (rs.next()) {
                                            docID = rs.getInt("docID");
                                            Dname = rs.getString("Dname");
                                            gender = rs.getString("Gender");
                                            special = rs.getString("Speciality");
                                            contact = rs.getLong("Contact");
                                            address = rs.getString("Address");
                                            System.out.println(docID + "\t\t" + Dname + "\t\t" + gender + "\t\t"
                                                    + contact + "\t\t" + special + "\t\t" + address);
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
                                            System.out.println(
                                                    "docID\tDname\t\t\t\tGender\t\tContact\t\tSpeciality\t\tAddress");
                                            docID = rs.getInt("docID");
                                            Dname = rs.getString("Dname");
                                            gender = rs.getString("Gender");
                                            special = rs.getString("Speciality");
                                            contact = rs.getLong("Contact");
                                            address = rs.getString("Address");
                                            System.out.println(docID + "\t\t" + Dname + "\t" + gender + "\t" + contact
                                                    + "\t" + special + "\t" + address);
                                            System.out.println("\n");
                                        } else {
                                            System.out.println("No doctor found with docID " + docID);
                                        }
                                        break;
                                    case 5:
                                        // Exit
                                        System.out.println("\nBack to the Main Menu");
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                        break;
                                }
                            } while (choice != 5);
                            break;
                        case 4:
                            System.out.println("Exiting...");
                            MainCLI.main(args);
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                } while (choice != 5);
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