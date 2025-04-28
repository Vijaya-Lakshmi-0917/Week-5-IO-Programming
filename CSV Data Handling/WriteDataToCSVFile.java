import java.io.FileWriter;
import java.io.IOException;

public class WriteDataToCSVFile {
    public static void main(String[] args) {
        String filePath = "employees.csv";

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("ID,Name,Department,Salary\n");
            writer.append("201,John Doe,Engineering,75000\n");
            writer.append("202,Jane Smith,Marketing,68000\n");
            writer.append("203,Robert Brown,Sales,72000\n");
            writer.append("204,Linda Johnson,HR,65000\n");
            writer.append("205,Michael Lee,Finance,80000\n");

            System.out.println("CSV file written successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }
}
