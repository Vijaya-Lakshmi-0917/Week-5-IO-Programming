import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.StringJoiner;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface JsonField {
    String name();
}

public class ImplementACustomSerializationAnnotationJsonField {

    static class User {
        @JsonField(name = "user_name")
        private String username;

        @JsonField(name = "user_age")
        private int age;

        private String ignoredField;

        public User(String username, int age, String ignoredField) {
            this.username = username;
            this.age = age;
            this.ignoredField = ignoredField;
        }
    }

    public static void main(String[] args) throws IllegalAccessException {
        User user = new User("alice", 30, "should be ignored");
        System.out.println(toJson(user));
    }

    public static String toJson(Object obj) throws IllegalAccessException {
        Class<?> cls = obj.getClass();
        StringJoiner json = new StringJoiner(", ", "{", "}");
        for (Field field : cls.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(JsonField.class)) {
                JsonField jsonField = field.getAnnotation(JsonField.class);
                Object value = field.get(obj);
                String valStr = value instanceof String ? "\"" + value + "\"" : value.toString();
                json.add("\"" + jsonField.name() + "\":" + valStr);
            }
        }
        return json.toString();
    }
}