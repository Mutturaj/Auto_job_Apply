package Pages;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    questionAnswerMap.put(normalizeText(entry.getKey()), entry.getValue().getAsString());
                }
                break;
            }
        }
    }

    public String getAnswer(String question) {
        String normalizedQuestion = normalizeText(question);
        String bestMatch = "2";

        System.out.println("Normalized Question: " + normalizedQuestion);

        for (Map.Entry<String, String> entry : questionAnswerMap.entrySet()) {
            String key = entry.getKey();
            String answer = entry.getValue();

            Pattern pattern = Pattern.compile(Pattern.quote(key), Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(normalizedQuestion);

            if (matcher.find()) {
                bestMatch = answer;
                System.out.println("Match Found: " + key + " -> " + answer);
                break;
            }
        }
        System.out.println("Best Match: " + bestMatch);

        return bestMatch;
    }

    private String normalizeText(String text) {
        return text.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", " ").trim();
    }

    public Map<String, String> getQuestionAnswerMap() {
        return questionAnswerMap;
    }
}
