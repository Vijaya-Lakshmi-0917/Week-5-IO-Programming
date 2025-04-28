import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SearchForARecordInCSV {
    public static void main(String[] args) {
        String filePath = "employees.csv";
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter employee name to search: ");
        String searchName = scanner.nextLine().trim();

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] data = line.split(",");
                String name = data[1].trim();

                if (name.equalsIgnoreCase(searchName)) {
                    System.out.println("Department: " + data[2]);
                    System.out.println("Salary    : " + data[3]);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Employee not found.");
            }

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }

        scanner.close();
    }
}