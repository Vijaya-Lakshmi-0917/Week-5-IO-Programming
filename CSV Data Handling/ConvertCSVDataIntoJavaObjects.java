import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Student {
    private String id;
    private String name;
    private int age;
    private int marks;

    public Student(String id, String name, int age, int marks) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Student{" +
                "ID='" + id + '\'' +
                ", Name='" + name + '\'' +
                ", Age=" + age +
                ", Marks=" + marks +
                '}';
    }
}

public class ConvertCSVDataIntoJavaObjects {
    public static void main(String[] args) {
        String filePath = "students.csv";
        List<Student> students = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] data = line.split(",");
                Student student = new Student(
                        data[0],
                        data[1],
                        Integer.parseInt(data[2]),
                        Integer.parseInt(data[3])
                );
                students.add(student);
            }

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }

        for (Student s : students) {
            System.out.println(s);
        }
    }
}