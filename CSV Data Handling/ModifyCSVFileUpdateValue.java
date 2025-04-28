import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModifyCSVFileUpdateValue {
    public static void main(String[] args) {
        String inputFile = "employees.csv";
        String outputFile = "updated_employees.csv";

        try (
                BufferedReader br = new BufferedReader(new FileReader(inputFile));
                FileWriter writer = new FileWriter(outputFile)
        ) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (isHeader) {
                    writer.append(line).append("\n");
                    isHeader = false;
                    continue;
                }

                String department = data[2].trim();

                if (department.equalsIgnoreCase("IT")) {
                    double salary = Double.parseDouble(data[3].trim());
                    salary *= 1.10;
                    data[3] = String.format("%.2f", salary);
                }

                writer.append(String.join(",", data)).append("\n");
            }

            System.out.println("Updated CSV file created successfully.");

        } catch (IOException e) {
            System.out.println("Error processing the file: " + e.getMessage());
        }
    }
}