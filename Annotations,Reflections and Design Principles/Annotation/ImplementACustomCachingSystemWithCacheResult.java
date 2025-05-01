import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface CacheResult {}

public class ImplementACustomCachingSystemWithCacheResult {

    static class Calculator {
        @CacheResult
        public long expensiveOperation(int n) {
            try {
                Thread.sleep(1000); // simulate expensive computation
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return (long) n * n;
        }
    }

    static class CacheProxy implements InvocationHandler {
        private final Object target;
        private final Map<String, Object> cache = new HashMap<>();

        public CacheProxy(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isAnnotationPresent(CacheResult.class)) {
                String key = method.getName() + "-" + (args != null ? args[0].toString() : "noArgs");
                if (cache.containsKey(key)) {
                    System.out.println("Returning cached result for " + key);
                    return cache.get(key);
                }
                Object result = method.invoke(target, args);
                cache.put(key, result);
                return result;
            }
            return method.invoke(target, args);
        }
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        Calculator proxy = (Calculator) Proxy.newProxyInstance(
                Calculator.class.getClassLoader(),
                new Class[]{Calculator.class},
                new CacheProxy(calc));

        System.out.println(proxy.expensiveOperation(5));
        System.out.println(proxy.expensiveOperation(5));
        System.out.println(proxy.expensiveOperation(10));
        System.out.println(proxy.expensiveOperation(10));
    }
}