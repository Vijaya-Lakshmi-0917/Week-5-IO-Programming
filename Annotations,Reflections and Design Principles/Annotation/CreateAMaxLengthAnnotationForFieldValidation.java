import java.lang.annotation.*;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

class CreateAMaxLengthAnnotationForFieldValidation {

    static class User {
        @MaxLength(10)
        private String username;

        public User(String username) {
            this.username = username;
            validate();
        }

        private void validate() {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(MaxLength.class)) {
                    MaxLength maxLength = field.getAnnotation(MaxLength.class);
                    field.setAccessible(true);
                    try {
                        Object val = field.get(this);
                        if (val instanceof String) {
                            String str = (String) val;
                            if (str.length() > maxLength.value()) {
                                throw new IllegalArgumentException(
                                        field.getName() + " length must be <= " + maxLength.value());
                            }
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new User("shortname");
        System.out.println("User with valid username created.");

        new User("thisIsAVeryLongUsername");  // Should throw IllegalArgumentException
    }
}







