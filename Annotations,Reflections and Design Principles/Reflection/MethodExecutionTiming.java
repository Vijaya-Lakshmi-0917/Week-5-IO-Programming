import java.lang.reflect.Method;

public class MethodExecutionTiming {

    public static void measureMethods(Object obj) {
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (method.getParameterCount() == 0) {
                method.setAccessible(true);
                try {
                    long start = System.nanoTime();
                    method.invoke(obj);
                    long end = System.nanoTime();
                    System.out.println(method.getName() + " executed in " + (end - start) + " ns");
                } catch (Exception e) {
                    System.out.println("Failed to invoke " + method.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    public static class Demo {
        public void fastMethod() {
            System.out.println("Fast method");
        }
        public void slowMethod() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {}
        }
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        measureMethods(demo);
    }
}