
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Retention(RetentionPolicy.RUNTIME)
@interface Inject {}

class SimpleDIContainer {
    private final Map<Class<?>, Object> instances = new HashMap<>();

    public <T> T getBean(Class<T> clazz) {
        try {
            if (instances.containsKey(clazz)) {
                return clazz.cast(instances.get(clazz));
            }
            T instance = clazz.getDeclaredConstructor().newInstance();
            instances.put(clazz, instance);

            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Inject.class)) {
                    field.setAccessible(true);
                    Object dependency = getBean(field.getType());
                    field.set(instance, dependency);
                }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

class ServiceA {
    public void hello() {
        System.out.println("Hello from ServiceA");
    }
}

class ServiceB {
    @Inject
    private ServiceA serviceA;

    public void callServiceA() {
        serviceA.hello();
    }
}

public class DependencyInjectionUsingReflection {
    public static void main(String[] args) {
        SimpleDIContainer container = new SimpleDIContainer();
        ServiceB serviceB = container.getBean(ServiceB.class);
        serviceB.callServiceA();
    }
}