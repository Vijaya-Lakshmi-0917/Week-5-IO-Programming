import java.io.*;
import java.util.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertJSONToCSVAndViceVersa {
    static class Student {
        public String id;
        public String name;
        public int age;
        public int marks;

        public Student() {}
        public Student(String id, String name, int age, int marks) {
            this.id = id; this.name = name; this.age = age; this.marks = marks;
        }
    }

    public static void jsonToCsv(String jsonFile, String csvFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Student> students = mapper.readValue(new File(jsonFile), new TypeReference<List<Student>>() {});

        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.append("ID,Name,Age,Marks\n");
            for (Student s : students) {
                writer.append(s.id).append(",")
                        .append(s.name).append(",")
                        .append(String.valueOf(s.age)).append(",")
                        .append(String.valueOf(s.marks)).append("\n");
            }
        }
    }

    public static void csvToJson(String csvFile, String jsonFile) throws IOException {
        List<Student> students = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] parts = line.split(",");
                students.add(new Student(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(jsonFile), students);
    }

    public static void main(String[] args) {
        try {
            jsonToCsv("students.json", "students.csv");
            csvToJson("students.csv", "students_out.json");
            System.out.println("Conversion done.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}