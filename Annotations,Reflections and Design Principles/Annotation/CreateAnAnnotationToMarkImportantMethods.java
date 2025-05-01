import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface ImportantMethod {
    String level() default "HIGH";
}

class CreateAnAnnotationToMarkImportantMethods {

    @ImportantMethod
    public void criticalTask() {
        System.out.println("Executing critical task");
    }

    @ImportantMethod(level = "MEDIUM")
    public void regularTask() {
        System.out.println("Executing regular task");
    }

    public void normalTask() {
        System.out.println("Executing normal task");
    }

    public static void main(String[] args) {
        Method[] methods = CreateAnAnnotationToMarkImportantMethods.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(ImportantMethod.class)) {
                ImportantMethod annotation = method.getAnnotation(ImportantMethod.class);
                System.out.println("Method: " + method.getName() + ", Importance Level: " + annotation.level());
            }
        }
    }
}