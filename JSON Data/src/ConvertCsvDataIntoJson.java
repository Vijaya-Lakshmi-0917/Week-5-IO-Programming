import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConvertCsvDataIntoJson {
    public static void main(String[] args) throws IOException {
        String csvFile = "data.csv";

        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        String line;
        String[] headers = null;

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode jsonArray = mapper.createArrayNode();

        if ((line = br.readLine()) != null) {
            headers = line.split(",");
        }

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            ObjectNode obj = mapper.createObjectNode();
            for (int i = 0; i < headers.length && i < values.length; i++) {
                obj.put(headers[i], values[i]);
            }
            jsonArray.add(obj);
        }
        br.close();

        System.out.println(jsonArray.toPrettyString());
    }
}