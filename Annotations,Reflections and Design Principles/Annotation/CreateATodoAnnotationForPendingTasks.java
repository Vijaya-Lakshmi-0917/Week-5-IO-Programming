import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Todo {
    String task();
    String assignedTo();
    String priority() default "MEDIUM";
}

class CreateATodoAnnotationForPendingTasks {

    @Todo(task = "Implement login feature", assignedTo = "Alice", priority = "HIGH")
    public void login() {}

    @Todo(task = "Add search functionality", assignedTo = "Bob")
    public void search() {}

    @Todo(task = "Optimize database queries", assignedTo = "Charlie", priority = "LOW")
    public void optimizeDB() {}

    public void completedFeature() {}

    public static void main(String[] args) {
        Method[] methods = CreateATodoAnnotationForPendingTasks.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Todo.class)) {
                Todo todo = method.getAnnotation(Todo.class);
                System.out.println("Method: " + method.getName() +
                        ", Task: " + todo.task() +
                        ", Assigned To: " + todo.assignedTo() +
                        ", Priority: " + todo.priority());
            }
        }
    }
}