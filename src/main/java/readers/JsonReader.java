package readers;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JsonReader {

    String jsonReader;

    String jsonFileName;

    private final String test_data_path = "src/test/resources/test-Data/";
    //Constructor

    public JsonReader(String jsonFileName) {
        this.jsonFileName = jsonFileName;

        try {
            JSONObject data = (JSONObject)new JSONParser().parse(new FileReader(test_data_path + jsonFileName + ".json"));
            jsonReader = data.toJSONString();
        } catch (Exception e) {
            System.out.println("Exception in JsonReader constructor " + e.getMessage());
        }
    }

    //valid.username
    public String getJsonData(String jsonPath) {
        try {
            return JsonPath.read(jsonReader, jsonPath);
        } catch (Exception e) {
            System.out.println("Exception in getJsonReader " + e.getMessage());
            return "";
        }
    }
}