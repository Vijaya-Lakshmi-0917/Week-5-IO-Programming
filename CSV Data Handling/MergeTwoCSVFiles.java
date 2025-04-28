import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MergeTwoCSVFiles {
    public static void main(String[] args) {
        String file1 = "students1.csv";
        String file2 = "students2.csv";
        String outputFile = "merged_students.csv";

        Map<String, String[]> map1 = new HashMap<>();
        Map<String, String[]> map2 = new HashMap<>();

        try (BufferedReader br1 = new BufferedReader(new FileReader(file1))) {
            String line;
            boolean isHeader = true;

            while ((line = br1.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] data = line.split(",");
                map1.put(data[0], new String[]{data[1], data[2]});
            }

        } catch (IOException e) {
            System.out.println("Error reading " + file1 + ": " + e.getMessage());
            return;
        }

        try (BufferedReader br2 = new BufferedReader(new FileReader(file2))) {
            String line;
            boolean isHeader = true;

            while ((line = br2.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] data = line.split(",");
                map2.put(data[0], new String[]{data[1], data[2]});
            }

        } catch (IOException e) {
            System.out.println("Error reading " + file2 + ": " + e.getMessage());
            return;
        }

        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.append("ID,Name,Age,Marks,Grade\n");

            for (String id : map1.keySet()) {
                String[] details1 = map1.get(id);
                String[] details2 = map2.get(id);

                String marks = "";
                String grade = "";

                if (details2 != null) {
                    marks = details2[0];
                    grade = details2[1];
                }

                writer.append(id).append(",")
                        .append(details1[0]).append(",")
                        .append(details1[1]).append(",")
                        .append(marks).append(",")
                        .append(grade).append("\n");
            }

            System.out.println("Files merged successfully.");

        } catch (IOException e) {
            System.out.println("Error writing the file: " + e.getMessage());
        }
    }
}