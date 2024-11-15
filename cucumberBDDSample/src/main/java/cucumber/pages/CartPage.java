import org.json.JSONArray;
import org.json.JSONObject;

public class JsonArrayToObject {
    public static void main(String[] args) {
        // JSON array string
        String jsonArrayString = "[{\"name\":\"John Doe\",\"age\":30},{\"name\":\"Jane Doe\",\"age\":25}]";

        // Parse the JSON array string
        JSONArray jsonArray = new JSONArray(jsonArrayString);

        // Wrap the JSON array into a JSON object with a key
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", jsonArray);

        // Convert JSON object to string
        String jsonObjectString = jsonObject.toString(4); // Pretty print with an indent factor of 4

        // Print the JSON object string
        System.out.println(jsonObjectString);
    }
}
