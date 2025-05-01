
import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(BugReports.class)
@interface BugReport {
    String description();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface BugReports {
    BugReport[] value();
}

class SoftwareModule {
    @BugReport(description = "Null pointer exception when input is null")
    @BugReport(description = "Performance issue on large datasets")
    public void process() {
        System.out.println("Processing module...");
    }
}

public class CreateAndUseARepeatableAnnotation {
    public static void main(String[] args) throws Exception {
        Method method = SoftwareModule.class.getMethod("process");
        if (method.isAnnotationPresent(BugReports.class)) {
            BugReport[] reports = method.getAnnotation(BugReports.class).value();
            for (BugReport report : reports) {
                System.out.println("Bug: " + report.description());
            }
        }

        new SoftwareModule().process();
    }
}