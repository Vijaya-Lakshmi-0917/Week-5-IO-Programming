import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;

public class ConvertJsonToXmlFormat {
    public static void main(String[] args) throws IOException {
        String json = """
        {
          "person": {
            "name": "Alice",
            "email": "alice@example.com",
            "age": 30
          }
        }
        """;

        ObjectMapper jsonMapper = new ObjectMapper();
        JsonNode jsonNode = jsonMapper.readTree(json);

        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(jsonNode);

        System.out.println(xml);
    }
}