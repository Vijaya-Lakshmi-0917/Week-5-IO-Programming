import java.lang.reflect.Method;

class Calculator {
    private int multiply(int a, int b) {
        return a * b;
    }
}

public class InvokePrivateMethod {
    public static void main(String[] args) {
        try {
            Calculator calculator = new Calculator();

            Class<?> cls = calculator.getClass();
            Method method = cls.getDeclaredMethod("multiply", int.class, int.class);

            method.setAccessible(true);

            Object result = method.invoke(calculator, 6, 7);
            System.out.println("Result: " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
