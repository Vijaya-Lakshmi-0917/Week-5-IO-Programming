import java.lang.reflect.Field;

public class GenerateAJSONRepresentation {

    public static String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }

        Class<?> clazz = obj.getClass();

        if (clazz == String.class) {
            return "\"" + obj + "\"";
        }

        if (Number.class.isAssignableFrom(clazz) || clazz == Boolean.class || clazz.isPrimitive()) {
            return obj.toString();
        }

        StringBuilder json = new StringBuilder();
        json.append("{");

        Field[] fields = clazz.getDeclaredFields();
        boolean first = true;

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (!first) {
                    json.append(",");
                } else {
                    first = false;
                }
                json.append("\"").append(field.getName()).append("\":");
                json.append(toJson(value));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        json.append("}");
        return json.toString();
    }

    public static void main(String[] args) {
        class Person {
            private String name = "Alice";
            private int age = 30;
            private boolean active = true;
        }

        Person person = new Person();
        System.out.println(toJson(person));
    }
}