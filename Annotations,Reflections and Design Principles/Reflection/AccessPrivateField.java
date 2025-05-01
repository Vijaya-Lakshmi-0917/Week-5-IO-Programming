import java.lang.reflect.Field;

class Person {
    private int age;

    public Person() {
        this.age = 0;
    }
}

public class AccessPrivateField {
    public static void main(String[] args) {
        try {
            Person person = new Person();

            Class<?> cls = person.getClass();
            Field ageField = cls.getDeclaredField("age");

            ageField.setAccessible(true);

            ageField.setInt(person, 25);
            int age = ageField.getInt(person);

            System.out.println("Modified age: " + age);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
