import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.ValidationMessage;

import java.util.Set;

public class ValidateAnEmailFieldUsingJsonSchema {
    public static void main(String[] args) throws Exception {
        String schemaJson = """
        {
          "$schema": "http://json-schema.org/draft-07/schema#",
          "type": "object",
          "properties": {
            "email": {
              "type": "string",
              "format": "email"
            }
          },
          "required": ["email"]
        }
        """;

        String jsonToValidate = """
        {
          "email": "alice@example.com"
        }
        """;

        ObjectMapper mapper = new ObjectMapper();
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance();

        JsonSchema schema = factory.getSchema(mapper.readTree(schemaJson));
        JsonNode jsonNode = mapper.readTree(jsonToValidate);

        Set<ValidationMessage> errors = schema.validate(jsonNode);

        if (errors.isEmpty()) {
            System.out.println("Valid email");
        } else {
            errors.forEach(error -> System.out.println(error.getMessage()));
        }
    }
}