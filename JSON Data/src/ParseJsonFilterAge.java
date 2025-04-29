import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;

public class ParseJsonFilterAge {
    public static void main(String[] args) throws IOException {
        String json = "[{\"name\":\"Alice\",\"age\":25},{\"name\":\"Bob\",\"age\":30},{\"name\":\"Charlie\",\"age\":22}]";

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = (ArrayNode) mapper.readTree(json);
        ArrayNode filtered = mapper.createArrayNode();

        for (JsonNode node : arrayNode) {
            if (node.has("age") && node.get("age").asInt() > 25) {
                filtered.add(node);
            }
        }

        System.out.println(filtered.toString());
    }
}