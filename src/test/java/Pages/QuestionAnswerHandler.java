package Pages;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class QuestionAnswerHandler {

    private Map<String, String> questionAnswerMap;

    public QuestionAnswerHandler(String datasetName) throws FileNotFoundException {
        questionAnswerMap = new HashMap<>();
        initializeQuestionAnswerMap(datasetName);
    }

    public void initializeQuestionAnswerMap(String datasetName) throws FileNotFoundException {
        JsonParser jsonParser = new JsonParser();
        FileReader reader = new FileReader("C:\\Users\\Welcome\\Downloads\\testrail_poc-master\\AutoJob_Apply\\dataset.json");
        JsonObject obj = (JsonObject) jsonParser.parse(reader);
        JsonArray datasets = obj.getAsJsonArray("datasets");

        for (int i = 0; i < datasets.size(); i++) {
            JsonObject dataset = datasets.get(i).getAsJsonObject();
            String name = dataset.get("name").getAsString();
            if (name.equalsIgnoreCase(datasetName)) {
                JsonObject data = dataset.getAsJsonObject("data");
                for (Map.Entry<String, JsonElement> entry : data.entrySet()) {
                    questionAnswerMap.put(entry.getKey(), entry.getValue().getAsString());
                }
                break;
            }
        }
    }

    public Map<String, String> getQuestionAnswerMap() {
        return questionAnswerMap;
    }
}
