import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;

public class ValidateJsonStructure {
    public static boolean validateJson(String jsonStr) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(jsonStr);
            if (!root.isObject()) return false;

            if (!root.has("name") || !root.has("email")) return false;

            if (!root.get("name").isTextual()) return false;
            if (!root.get("email").isTextual()) return false;


            return true;
        } catch (JsonProcessingException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        String validJson = "{\"name\":\"Alice\",\"email\":\"alice@example.com\",\"age\":25}";
        String invalidJson = "{\"name\":123,\"email\":\"alice@example.com\"}";
        String malformedJson = "{name:\"Alice\",email:\"alice@example.com\"}";

        System.out.println(validateJson(validJson));
        System.out.println(validateJson(invalidJson));
        System.out.println(validateJson(malformedJson));
    }
}