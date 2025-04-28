import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadLargeCSVFileEfficiently {
    public static void main(String[] args) {
        String filePath = "largefile.csv";
        int chunkSize = 100;
        int totalCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            int chunkCount = 0;

            while (true) {
                int linesRead = 0;

                while (linesRead < chunkSize && (line = br.readLine()) != null) {
                    if (isHeader) {
                        isHeader = false;
                        continue;
                    }
                    linesRead++;
                    totalCount++;
                }

                if (linesRead == 0) break;

                chunkCount++;
                System.out.println("Processed " + totalCount + " records after chunk " + chunkCount);
            }

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}