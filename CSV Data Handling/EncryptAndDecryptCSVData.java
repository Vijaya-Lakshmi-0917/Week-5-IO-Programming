import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;

public class EncryptAndDecryptCSVData {
    private static final String ALGORITHM = "AES";

    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128);
        return keyGen.generateKey();
    }

    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }

    public static void writeEncryptedCSV(String inputFile, String outputFile, SecretKey key) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             FileWriter fw = new FileWriter(outputFile)) {

            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                if (isHeader) {
                    fw.append(line).append("\n");
                    isHeader = false;
                    continue;
                }

                fields[2] = encrypt(fields[2], key);
                fields[3] = encrypt(fields[3], key);

                fw.append(String.join(",", fields)).append("\n");
            }
        } catch (Exception e) {
            System.out.println("Error during encryption write: " + e.getMessage());
        }
    }

    public static void readDecryptedCSV(String encryptedFile, SecretKey key) {
        try (BufferedReader br = new BufferedReader(new FileReader(encryptedFile))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                if (isHeader) {
                    System.out.println(line);
                    isHeader = false;
                    continue;
                }

                fields[2] = decrypt(fields[2], key);
                fields[3] = decrypt(fields[3], key);

                System.out.println(String.join(",", fields));
            }
        } catch (Exception e) {
            System.out.println("Error during decryption read: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        SecretKey key = generateKey();
        String originalFile = "employees.csv";
        String encryptedFile = "employees_encrypted.csv";

        writeEncryptedCSV(originalFile, encryptedFile, key);
        System.out.println("Encrypted CSV written.\nDecrypted content:");

        readDecryptedCSV(encryptedFile, key);
    }
}