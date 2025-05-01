import java.lang.reflect.Field;
import java.util.Map;

public class CreateACustomObjectMapper {
    public static <T> T toObject(Class<T> clazz, Map<String, Object> properties) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            for (Map.Entry<String, Object> entry : properties.entrySet()) {
                try {
                    Field field = clazz.getDeclaredField(entry.getKey());
                    field.setAccessible(true);
                    field.set(instance, entry.getValue());
                } catch (NoSuchFieldException ignored) {}
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
