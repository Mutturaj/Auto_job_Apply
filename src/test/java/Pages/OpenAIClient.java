package Pages;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

public class OpenAIClient {

    private static final String API_KEY = "AIzaSyAyrAzKZgos_EpYhE72GvqjUmlKBjMYTh4";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;

    public OpenAIClient(String currentDatasetName) {
    }

    public static String getAnswer(String question, String profile) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String jsonBody = "{\n" +
                "  \"contents\": [\n" +
                "    {\n" +
                "      \"parts\": [\n" +
                "        {\n" +
                "          \"text\": \"You are a helpful assistant. Use this profile: " + profile + "\\nQuestion: " + question + "\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseBody = response.body().string();

            JSONObject json = new JSONObject(responseBody);
            return json
                    .getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");
        }
    }

    public static void main(String[] args) throws IOException {
        String profile = ProfileFormatter.loadProfileAsPrompt("Bindu", "/Users/mutturaj.annigerizeptonow.com/IdeaProjects/Auto_job_Apply/dataset.json");
        String question = "Why are you a good fit for this QA role? pls give me single ans";
        String answer = OpenAIClient.getAnswer(question, profile);
        System.out.println("Gemini Answer :" + answer);
    }
}