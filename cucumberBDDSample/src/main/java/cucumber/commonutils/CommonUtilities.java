import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteJsonOnException {
    public static void main(String[] args) {
        String jsonString = "{\"name\":\"John Doe\",\"age\":30}"; // your JSON content
        String filePath = null; // Declare file path variable

        try {
            // Dynamically obtain the file path (simulate this part)
            filePath = getPathDynamically();
            System.out.println("Dynamically obtained file path: " + filePath);

            // Simulate some code that may throw a NullPointerException
            simulateCodeThatMayThrowException();
        } catch (NullPointerException e) {
            System.out.println("NullPointerException caught: " + e.getMessage());

            if (filePath != null) { // Ensure filePath is not null
                // Create a file and write the JSON content to it
                try {
                    File file = new File(filePath);
                    
                    // Create parent directories if they don't exist
                    if (file.getParentFile() != null) {
                        file.getParentFile().mkdirs();
                    }

                    // Create the file if it doesn't exist
                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    // Write JSON string to the file
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(jsonString);
                    fileWriter.close();

                    System.out.println("File created and written successfully: " + filePath);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else {
                System.err.println("File path is null. Unable to create file.");
            }
        }
    }

    private static String getPathDynamically() {
        // Simulate obtaining the file path dynamically
        return "C:\\Users\\YourUsername\\path\\to\\your\\output.json"; 
    }

    private static void simulateCodeThatMayThrowException() {
        // Simulate code that throws NullPointerException
        throw new NullPointerException("Simulated NullPointerException");
    }
}
