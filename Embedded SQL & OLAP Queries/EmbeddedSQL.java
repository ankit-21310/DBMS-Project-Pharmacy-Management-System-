import java.sql.*;

public class EmbeddedSQL {
    public static void main(String[] args){
        Connection conn = null;
        Statement stmt = null;

        String url = "jdbc:mysql://localhost:3306/ops";
        String user = "root";
        String password = "Hs19282001@@";

        try {
            // Establish connection
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection Established...");

            // Perform SQL statements
            stmt = conn.createStatement();
            // Query01
            System.out.println("Embedded SQl: 01\n");
            ResultSet rs1 = stmt.executeQuery("SELECT * FROM login where Role = 'doctor';");
            while (rs1.next()) {
                int id = rs1.getInt("Lid");
                String name = rs1.getString("Role");
                System.out.println("ID: " + id + ",     Role: " + name);
            }

            //Query02
            System.out.println("Embedded SQl: 02\n");
            PreparedStatement pstmt1 = conn.prepareStatement("INSERT INTO customer(CID, Cname, Gender, Age, Contact, Address) VALUES (?, ?, ?, ?, ?, ?)");
            pstmt1.setInt(1, 101);
            pstmt1.setString(2, "Ankit Kumar Singh");
            pstmt1.setString(3, "Male");
            pstmt1.setInt(4, 25);
            pstmt1.setString(5, "9654393760");
            pstmt1.setString(6, "Sangam Vihar, New Delhi");
            pstmt1.executeUpdate();

            //Query03
            System.out.println("Embedded SQl: 03\n");
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM customer;");
            while (rs2.next()) {
                int id = rs2.getInt("CID");
                String name = rs2.getString("Cname");
                String gender = rs2.getString("Gender");
                int age = rs2.getInt("Age");
                String contact = rs2.getString("Contact");
                String add = rs2.getString("Address");
                System.out.println("CID: " + id + ",    Cname: " + name + ",    Gender: " + gender + ",     Age: " + age + ",    Contact: " + contact + ",    Address: " + add);
            }

            //Query04
            System.out.println("Embedded SQl: 04\n");
            PreparedStatement pstmt2 = conn.prepareStatement("DELETE FROM customer WHERE Cname = ?;");
            pstmt2.setString(1, "Ankit Kumar Singh");
            pstmt2.executeUpdate();

            //Query05
            System.out.println("Embedded SQl: 05\n");
            PreparedStatement pstmt3 = conn.prepareStatement("UPDATE customer SET Contact = ? WHERE Cname = ?;");
            pstmt3.setString(1, "9811565583");
            pstmt3.setString(2, "Ankit Kumar Singh");
            pstmt3.executeUpdate();

            // Close connection
            rs1.close();
            rs2.close();
            pstmt2.close();
            pstmt3.close();
            pstmt1.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

}
