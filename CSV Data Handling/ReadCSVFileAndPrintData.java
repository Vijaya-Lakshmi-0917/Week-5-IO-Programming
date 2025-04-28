import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadCSVFileAndPrintData {
    public static void main(String[] args) {
        String filePath = "students.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            System.out.println("Student Details:\n");

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] data = line.split(",");

                System.out.println("ID     : " + data[0]);
                System.out.println("Name   : " + data[1]);
                System.out.println("Age    : " + data[2]);
                System.out.println("Marks  : " + data[3]);
                System.out.println("-----------------------------");
            }

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}