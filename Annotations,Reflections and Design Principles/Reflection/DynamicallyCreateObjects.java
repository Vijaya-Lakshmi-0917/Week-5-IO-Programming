class Student {
    private String name;

    public Student() {
        this.name = "Default Name";
    }

    public String getName() {
        return name;
    }
}

public class DynamicallyCreateObjects {
    public static void main(String[] args) {
        try {
            Class<?> cls = Class.forName("Student");
            Object obj = cls.getDeclaredConstructor().newInstance();

            Student student = (Student) obj;
            System.out.println("Student name: " + student.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}