import java.lang.reflect.Field;

class Configuration {
    private static String API_KEY = "ORIGINAL_KEY";
}

public class AccessAndModifyStaticFields {
    public static void main(String[] args) {
        try {
            Class<?> cls = Configuration.class;
            Field field = cls.getDeclaredField("API_KEY");

            field.setAccessible(true);
            field.set(null, "UPDATED_KEY");

            String value = (String) field.get(null);
            System.out.println("Modified API_KEY: " + value);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}