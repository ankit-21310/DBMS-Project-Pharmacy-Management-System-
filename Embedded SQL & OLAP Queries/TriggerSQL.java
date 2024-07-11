import java.sql.*;

public class TriggerSQL {
    public static void main(String[] args) {
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

            // TRIGGER QUERY 01
            String sql1 = "DROP TRIGGER IF EXISTS tQuery01";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql1);

            System.out.println("Trigger01 removed successfully");
            String t01 = "CREATE TRIGGER tQuery01 " +
                    "AFTER INSERT ON invoice " +
                    "FOR EACH ROW " +
                    "BEGIN " +
                    "  UPDATE medicine " +
                    "  SET Quantity = Quantity- NEW.Quantity " +
                    "  WHERE medID = NEW.medID;" +
                    "END";
            stmt = conn.createStatement();
            stmt.executeUpdate(t01);
            System.out.println("Trigger01 created successfully");

            // TRIGGER QUERY 02
            String sql2 = "DROP TRIGGER IF EXISTS tQuery02";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql2);

            System.out.println("Trigger02 removed successfully");
            String t02 = "CREATE TRIGGER tQuery02 " +
                    "AFTER INSERT ON employee " +
                    "FOR EACH ROW " +
                    "BEGIN " +
                    "  UPDATE login " +
                    "  SET Role = Role + 1 " +
                    "  WHERE EID = NEW.EID;" +
                    "END";
            stmt = conn.createStatement();
            stmt.executeUpdate(t02);
            System.out.println("Trigger02 created successfully");

            // TRIGGER QUERY 03
            String sql3 = "DROP TRIGGER IF EXISTS tQuery03";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql3);

            // Close connection
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}
