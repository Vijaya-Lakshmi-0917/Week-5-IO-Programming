import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class ValidateCSVDataBeforeProcessing {
    public static void main(String[] args) {
        String filePath = "contacts.csv";
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        Pattern phonePattern = Pattern.compile("^\\d{10}$");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            int lineNumber = 1;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (isHeader) {
                    isHeader = false;
                    lineNumber++;
                    continue;
                }

                String email = data[2].trim();
                String phone = data[3].trim();
                boolean isValid = true;

                if (!emailPattern.matcher(email).matches()) {
                    System.out.println("Line " + lineNumber + " - Invalid Email: " + email);
                    isValid = false;
                }

                if (!phonePattern.matcher(phone).matches()) {
                    System.out.println("Line " + lineNumber + " - Invalid Phone Number: " + phone);
                    isValid = false;
                }

                if (!isValid) {
                    System.out.println("Invalid Record: " + line);
                    System.out.println("--------------------------------------");
                }

                lineNumber++;
            }

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}