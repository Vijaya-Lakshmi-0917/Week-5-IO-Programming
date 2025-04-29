import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;

public class MergeTwoJsonObjectsIntoOne  {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String json1 = "{\"name\":\"Alice\",\"email\":\"alice@example.com\"}";
        String json2 = "{\"age\":25,\"city\":\"New York\"}";

        JsonNode node1 = mapper.readTree(json1);
        JsonNode node2 = mapper.readTree(json2);

        ObjectNode merged = mapper.createObjectNode();
        merged.setAll((ObjectNode) node1);
        merged.setAll((ObjectNode) node2);

        System.out.println(merged.toString());
    }
}