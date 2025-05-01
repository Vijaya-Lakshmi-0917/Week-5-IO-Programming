import java.util.ArrayList;
import java.util.List;
public class SuppressUncheckedWarnings {


    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        List rawList = new ArrayList();
        rawList.add("Hello");
        rawList.add("World");

        for (Object item : rawList) {
            System.out.println(item);
        }
    }
}