import java.lang.reflect.*;
import java.util.Scanner;

public class GetClassInformation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter full class name (e.g., java.util.ArrayList): ");
        String className = scanner.nextLine();

        try {
            Class<?> cls = Class.forName(className);

            System.out.println("Class: " + cls.getName());

            System.out.println("\nFields:");
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                System.out.println("  " + Modifier.toString(field.getModifiers()) + " " +
                        field.getType().getSimpleName() + " " + field.getName());
            }

            System.out.println("\nConstructors:");
            Constructor<?>[] constructors = cls.getDeclaredConstructors();
            for (Constructor<?> constructor : constructors) {
                System.out.print("  " + Modifier.toString(constructor.getModifiers()) + " " +
                        constructor.getName() + "(");
                Class<?>[] paramTypes = constructor.getParameterTypes();
                for (int i = 0; i < paramTypes.length; i++) {
                    System.out.print(paramTypes[i].getSimpleName());
                    if (i < paramTypes.length - 1) System.out.print(", ");
                }
                System.out.println(")");
            }

            System.out.println("\nMethods:");
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                System.out.print("  " + Modifier.toString(method.getModifiers()) + " " +
                        method.getReturnType().getSimpleName() + " " + method.getName() + "(");
                Class<?>[] paramTypes = method.getParameterTypes();
                for (int i = 0; i < paramTypes.length; i++) {
                    System.out.print(paramTypes[i].getSimpleName());
                    if (i < paramTypes.length - 1) System.out.print(", ");
                }
                System.out.println(")");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getMessage());
        }

        scanner.close();
    }
}
