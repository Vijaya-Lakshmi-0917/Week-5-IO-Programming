import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CustomLoggingProxyUsingReflection {

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> iface, T realObject) {
        return (T) Proxy.newProxyInstance(
                iface.getClassLoader(),
                new Class<?>[] { iface },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("Calling method: " + method.getName());
                        return method.invoke(realObject, args);
                    }
                });
    }

    public interface Greeting {
        void sayHello();
    }

    public static class GreetingImpl implements Greeting {
        public void sayHello() {
            System.out.println("Hello!");
        }
    }

    public static void main(String[] args) {
        Greeting realGreeting = new GreetingImpl();
        Greeting proxyGreeting = create(Greeting.class, realGreeting);

        proxyGreeting.sayHello();
    }
}