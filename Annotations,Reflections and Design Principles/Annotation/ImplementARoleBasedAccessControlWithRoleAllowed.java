import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface RoleAllowed {
    String value();
}

public class ImplementARoleBasedAccessControlWithRoleAllowed {

    static class SecurityContext {
        private static String currentUserRole = "USER";

        public static void setRole(String role) {
            currentUserRole = role;
        }

        public static String getRole() {
            return currentUserRole;
        }
    }

    static class Service {

        @RoleAllowed("ADMIN")
        public void adminOnlyOperation() {
            System.out.println("Admin operation performed.");
        }

        public void generalOperation() {
            System.out.println("General operation performed.");
        }
    }

    public static void main(String[] args) throws Exception {
        Service service = new Service();

        invokeIfAllowed(service, "adminOnlyOperation");
        invokeIfAllowed(service, "generalOperation");

        SecurityContext.setRole("ADMIN");

        invokeIfAllowed(service, "adminOnlyOperation");
        invokeIfAllowed(service, "generalOperation");
    }

    private static void invokeIfAllowed(Object obj, String methodName) throws Exception {
        Method method = obj.getClass().getMethod(methodName);
        if (method.isAnnotationPresent(RoleAllowed.class)) {
            RoleAllowed roleAllowed = method.getAnnotation(RoleAllowed.class);
            if (!roleAllowed.value().equals(SecurityContext.getRole())) {
                System.out.println("Access Denied!");
                return;
            }
        }
        method.invoke(obj);
    }
}