package Pages;

import org.json.JSONObject;
import org.json.JSONArray;
import java.nio.file.*;
import java.io.IOException;

public class ProfileFormatter {

    public static String loadProfileAsPrompt(String datasetName, String jsonPath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(jsonPath)));
        JSONObject json = new JSONObject(content);
        JSONArray datasets = json.getJSONArray("datasets");

        for (int i = 0; i < datasets.length(); i++) {
            JSONObject dataset = datasets.getJSONObject(i);
            if (dataset.getString("name").equalsIgnoreCase(datasetName)) {
                JSONObject data = dataset.getJSONObject("data");

                StringBuilder sb = new StringBuilder();
                sb.append("Name: ").append(data.optString("First name")).append(" ").append(data.optString("Last name")).append("\n");
                sb.append("Email: ").append(data.optString("EmailID")).append("\n");
                sb.append("Phone: ").append(data.optString("Mobile phone number")).append("\n");
                sb.append("Experience: ").append(data.optString("Experience")).append(" years\n");
                sb.append("Current Company: ").append(data.optString("Current company")).append("\n");
                sb.append("Skills: Selenium, Java, API Testing, Appium, Cypress, Manual Testing\n");
                sb.append("Location: ").append(data.optString("JobLocation")).append("\n");
                sb.append("LinkedIn: ").append(data.optString("LinkedIn Profile")).append("\n");
                sb.append("Qualification: ").append(data.optString("Highest Qualification")).append(" in ").append(data.optString("Field(s) of Study")).append("\n");
                sb.append("Current CTC: ₹").append(data.optString("current ctc")).append(" | Expected CTC: ₹").append(data.optString("expected ctc")).append("\n");
                sb.append("Notice Period: ").append(data.optString("notice period")).append(" days\n");

                return sb.toString();
            }
        }
        throw new IllegalArgumentException("Profile not found: " + datasetName);
    }
}