import org.json.JSONObject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonKeyChecker {
    public static void main(String[] args) {
        String filePath = "path/to/your/jsonfile.json"; // Path to your JSON file
        String keyToCheck = "yourKey"; // Key to check in the JSON
        String newValue = "newValue"; // Value to add if key is not present

        try {
            // Read the JSON file
            File file = new File(filePath);
            FileReader reader = new FileReader(file);
            StringBuilder content = new StringBuilder();
            int i;
            while ((i = reader.read()) != -1) {
                content.append((char) i);
            }
            reader.close();

            // Parse the JSON content
            JSONObject jsonObject = new JSONObject(content.toString());

            // Check if the key is present
            if (!jsonObject.has(keyToCheck)) {
                // If key is not present, add the key-value pair
                jsonObject.put(keyToCheck, newValue);

                // Write the updated JSON back to the file
                FileWriter writer = new FileWriter(file);
                writer.write(jsonObject.toString(4)); // Pretty print with an indent factor of 4
                writer.close();

                System.out.println("Key added successfully.");
            } else {
                System.out.println("Key already exists in the JSON.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
