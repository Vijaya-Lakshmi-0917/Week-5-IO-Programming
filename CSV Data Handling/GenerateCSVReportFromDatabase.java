import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GenerateCSVReportFromDatabase {
    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/your_database";
        String username = "your_username";
        String password = "your_password";
        String outputFile = "employee_report.csv";

        try (
                Connection conn = DriverManager.getConnection(jdbcURL, username, password);
                Statement stmt = conn.createStatement();
                FileWriter writer = new FileWriter(outputFile)
        ) {
            String query = "SELECT employee_id, name, department, salary FROM employees";
            ResultSet rs = stmt.executeQuery(query);

            writer.append("Employee ID,Name,Department,Salary\n");

            while (rs.next()) {
                writer.append(rs.getString("employee_id")).append(",");
                writer.append(rs.getString("name")).append(",");
                writer.append(rs.getString("department")).append(",");
                writer.append(rs.getString("salary")).append("\n");
            }

            System.out.println("CSV report generated successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}