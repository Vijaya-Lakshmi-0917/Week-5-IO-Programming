import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class ReadJsonFileAndPrintAllKeysValues {
    public static void printJson(JsonNode node) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                System.out.print(entry.getKey() + ": ");
                printJson(entry.getValue());
            }
        } else if (node.isArray()) {
            for (JsonNode item : node) {
                printJson(item);
            }
        } else {
            System.out.println(node.asText());
        }
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("data.json"));
        printJson(root);
    }
}