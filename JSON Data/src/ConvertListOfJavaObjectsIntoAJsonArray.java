import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.ArrayList;

public class ConvertListOfJavaObjectsIntoAJsonArray {
    public static class Person {
        public String name;
        public String email;

        public Person(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }

    public static void main(String[] args) throws Exception {
        List<Person> people = new ArrayList<>();
        people.add(new Person("Alice", "alice@example.com"));
        people.add(new Person("Bob", "bob@example.com"));

        ObjectMapper mapper = new ObjectMapper();
        String jsonArray = mapper.writeValueAsString(people);

        System.out.println(jsonArray);
    }
}