import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface LogExecutionTime {}

class CreateAnAnnotationForLoggingMethodExecutionTime {

    @LogExecutionTime
    public void fastMethod() {
        for (int i = 0; i < 100_000; i++) { int x = i * i; }
    }

    @LogExecutionTime
    public void slowMethod() {
        try { Thread.sleep(100); } catch (InterruptedException e) {}
    }

    public static void main(String[] args) throws Exception {
        CreateAnAnnotationForLoggingMethodExecutionTime obj = new CreateAnAnnotationForLoggingMethodExecutionTime();
        Method[] methods = CreateAnAnnotationForLoggingMethodExecutionTime.class.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(LogExecutionTime.class)) {
                long start = System.nanoTime();
                method.invoke(obj);
                long end = System.nanoTime();
                System.out.println("Execution time of " + method.getName() + ": " + (end - start) + " ns");
            }
        }
    }
}