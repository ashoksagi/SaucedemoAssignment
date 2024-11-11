import org.json.JSONObject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonKeyChecker {
    public static void main(String[] args) {
        String filePath = "path/to/your/jsonfile.json"; // Path to your JSON file
        String keyToCheck = "yourKey"; // Key to check in the JSON
        String jsonStringValue = "{\"name\":\"John Doe\",\"age\":30}"; // JSON string to add as value

        try {
            // Create a file object
            File file = new File(filePath);

            // Check if the file exists
            if (!file.exists()) {
                // If the file does not exist, create it with initial JSON content
                JSONObject initialJson = new JSONObject();
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write(initialJson.toString(4)); // Pretty print with an indent factor of 4
                }
                System.out.println("File did not exist and has been created with initial content.");
            }

            // Read the JSON file
            FileReader reader = new FileReader(file);
            StringBuilder content = new StringBuilder();
            int i;
            while ((i = reader.read()) != -1) {
                content.append((char) i);
            }
            reader.close();

            // Convert the StringBuilder content to String
            String jsonString = content.toString();

            // Parse the JSON content
            JSONObject jsonObject = new JSONObject(jsonString);

            // Check if the key is present
            if (jsonObject.has(keyToCheck)) {
                // Key is present, retrieve its value
                Object value = jsonObject.get(keyToCheck);
                System.out.println("Key found! Value: " + value);
            } else {
                // Parse the JSON string value
                JSONObject valueObject = new JSONObject(jsonStringValue);

                // If key is not present, add the key-value pair
                jsonObject.put(keyToCheck, valueObject);

                // Write the updated JSON back to the file
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write(jsonObject.toString(4)); // Pretty print with an indent factor of 4
                }

                System.out.println("Key added successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
