import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JsonFieldExtractor {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootArray = mapper.readTree(new File("data.json"));

            for (JsonNode objNode : rootArray) {
                String name = objNode.has("name") ? objNode.get("name").asText() : null;
                String email = objNode.has("email") ? objNode.get("email").asText() : null;

                System.out.println("Name: " + name + ", Email: " + email);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}