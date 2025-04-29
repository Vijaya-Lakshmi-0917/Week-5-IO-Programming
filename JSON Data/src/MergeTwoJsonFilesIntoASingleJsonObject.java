import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;

public class MergeTwoJsonFilesIntoASingleJsonObject {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode json1 = mapper.readTree(new File("file1.json"));
        JsonNode json2 = mapper.readTree(new File("file2.json"));

        ObjectNode merged = mapper.createObjectNode();
        merged.setAll((ObjectNode) json1);
        merged.setAll((ObjectNode) json2);

        System.out.println(merged.toString());
    }
}