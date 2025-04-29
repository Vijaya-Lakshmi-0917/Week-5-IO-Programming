import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.*;

public class IPLAndCensorAnalyzer {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        String jsonInput = "ipl_input.json";
        String csvInput = "ipl_input.csv";
        String censoredJsonOutput = "ipl_censored_output.json";
        String censoredCsvOutput = "ipl_censored_output.csv";

        ArrayNode jsonData = readJsonFile(jsonInput);
        censorJsonData(jsonData);
        writeJsonFile(jsonData, censoredJsonOutput);

        List<Map<String, String>> csvData = readCsvFile(csvInput);
        censorCsvData(csvData);
        writeCsvFile(csvData, censoredCsvOutput);
    }

    private static ArrayNode readJsonFile(String filename) throws IOException {
        JsonNode root = mapper.readTree(new File(filename));
        if (!root.isArray()) throw new IllegalArgumentException("Expected JSON array");
        return (ArrayNode) root;
    }

    private static void writeJsonFile(ArrayNode data, String filename) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), data);
    }

    private static void censorJsonData(ArrayNode jsonData) {
        for (JsonNode node : jsonData) {
            ObjectNode obj = (ObjectNode) node;
            obj.put("team1", maskTeamName(obj.get("team1").asText()));
            obj.put("team2", maskTeamName(obj.get("team2").asText()));
            obj.put("winner", maskTeamName(obj.get("winner").asText()));
            obj.put("player_of_match", "REDACTED");
            if (obj.has("score")) {
                ObjectNode scoreNode = (ObjectNode) obj.get("score");
                Iterator<String> fields = scoreNode.fieldNames();
                List<String> keys = new ArrayList<>();
                while (fields.hasNext()) keys.add(fields.next());
                for (String key : keys) {
                    int score = scoreNode.get(key).asInt();
                    scoreNode.remove(key);
                    scoreNode.put(maskTeamName(key), score);
                }
            }
        }
    }

    private static List<Map<String, String>> readCsvFile(String filename) throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            List<String[]> rows = reader.readAll();
            if (rows.isEmpty()) return Collections.emptyList();
            String[] headers = rows.get(0);
            List<Map<String, String>> records = new ArrayList<>();
            for (int i = 1; i < rows.size(); i++) {
                String[] row = rows.get(i);
                Map<String, String> record = new LinkedHashMap<>();
                for (int j = 0; j < headers.length && j < row.length; j++) {
                    record.put(headers[j], row[j]);
                }
                records.add(record);
            }
            return records;
        }
    }

    private static void writeCsvFile(List<Map<String, String>> data, String filename) throws IOException {
        if (data.isEmpty()) return;
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename))) {
            Set<String> headerSet = data.get(0).keySet();
            String[] headers = headerSet.toArray(new String[0]);
            writer.writeNext(headers);
            for (Map<String, String> row : data) {
                String[] line = Arrays.stream(headers).map(h -> row.getOrDefault(h, "")).toArray(String[]::new);
                writer.writeNext(line);
            }
        }
    }

    private static void censorCsvData(List<Map<String, String>> csvData) {
        for (Map<String, String> row : csvData) {
            if (row.containsKey("team1")) row.put("team1", maskTeamName(row.get("team1")));
            if (row.containsKey("team2")) row.put("team2", maskTeamName(row.get("team2")));
            if (row.containsKey("winner")) row.put("winner", maskTeamName(row.get("winner")));
            if (row.containsKey("player_of_match")) row.put("player_of_match", "REDACTED");
        }
    }

    private static String maskTeamName(String teamName) {
        if (teamName == null || teamName.trim().isEmpty()) return teamName;
        String[] parts = teamName.split(" ");
        if (parts.length <= 1) return teamName + " ***";
        return parts[0] + " ***";
    }
}