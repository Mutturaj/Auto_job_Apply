package Pages;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
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
        String filePath = System.getProperty("user.dir") + File.separator + "dataset.json";
        FileReader reader = new FileReader(filePath);
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

//    public String getAnswer(String question) {
//        String normalizedQuestion = normalizeText(question);
//        String bestMatchKey = null;
//        int bestMatchScore = 0;
//
//        for (Map.Entry<String, String> entry : questionAnswerMap.entrySet()) {
//            String key = normalizeText(entry.getKey());
//            int matchScore = calculateMatchScore(normalizedQuestion, key);
//
//            if (matchScore > bestMatchScore) {
//                bestMatchScore = matchScore;
//                bestMatchKey = key;
//            }
//        }
//
//        if (bestMatchKey != null) {
//            System.out.println("Best Match Found: " + bestMatchKey + " -> " + questionAnswerMap.get(bestMatchKey));
//            return questionAnswerMap.get(bestMatchKey);
//        }
//        System.out.println("No match found.");
//        return "3";
//    }
//
//    private int calculateMatchScore(String question, String key) {
//        Set<String> questionWords = new HashSet<>(Arrays.asList(question.split("\\s+")));
//        Set<String> keyWords = new HashSet<>(Arrays.asList(key.split("\\s+")));
//        questionWords.retainAll(keyWords);
//        return questionWords.size();
//    }

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
        return text.toLowerCase()
                .replaceAll("[^a-zA-Z0-9 ]", " ")
                .trim();
    }

    public Map<String, String> getQuestionAnswerMap() {
        return questionAnswerMap;
    }
}
