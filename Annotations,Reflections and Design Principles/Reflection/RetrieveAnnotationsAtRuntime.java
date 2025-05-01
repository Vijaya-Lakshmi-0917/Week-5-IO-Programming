import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Annotation;

@Retention(RetentionPolicy.RUNTIME)
@interface Author {
    String name();
}

@Author(name = "John Doe")
class Book {

}

public class RetrieveAnnotationsAtRuntime {
    public static void main(String[] args) {
        Class<Book> obj = Book.class;

        if (obj.isAnnotationPresent(Author.class)) {
            Author author = obj.getAnnotation(Author.class);
            System.out.println("Author: " + author.name());
        } else {
            System.out.println("Author annotation not present.");
        }
    }
}