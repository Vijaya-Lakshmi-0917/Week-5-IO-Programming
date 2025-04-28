import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortCSVRecordsByColumn {
    public static void main(String[] args) {
        String filePath = "employees.csv";
        List<String[]> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            String[] header = null;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (isHeader) {
                    header = data;
                    isHeader = false;
                    continue;
                }
                records.add(data);
            }

            records.sort(Comparator.comparingDouble(r -> -Double.parseDouble(r[3])));

            System.out.println("Top 5 Highest Paid Employees:\n");
            System.out.printf("%-5s %-15s %-15s %-10s%n", header[0], header[1], header[2], header[3]);
            System.out.println("--------------------------------------------------");

            for (int i = 0; i < Math.min(5, records.size()); i++) {
                String[] r = records.get(i);
                System.out.printf("%-5s %-15s %-15s %-10s%n", r[0], r[1], r[2], r[3]);
            }

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}