package customEntities;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.DataProvider;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import AppConfg.DataConfig;
import static Test.LinkedinTest.driver;

public class dataRead {

    @DataProvider(name = "login_cred")
    public Object[][] Jsonreader() throws FileNotFoundException {
        List<String> keys = Arrays.asList("EmailID", "Password", "JobTitle", "JobLocation", "Experience", "Hiring Manager Message");
        return provideLoginCredentials("datasets", keys); // Pass keys dynamically
    }

    public Object[][] provideLoginCredentials(String dataKey, List<String> keys) throws FileNotFoundException {
        // Get dataset name from DataConfig
        String datasetName = DataConfig.getInstance().getDatasetName();

        JsonParser jsonParser = new JsonParser();
        FileReader reader = new FileReader("C:\\Users\\Welcome\\Downloads\\testrail_poc-master\\AutoJob_Apply\\dataset.json");
        Object obj = jsonParser.parse(reader);
        JsonObject userlogin = (JsonObject) obj;
        JsonArray userloginsarray = (JsonArray) userlogin.get(dataKey);

        List<Object[]> dataList = new ArrayList<>();
        for (int i = 0; i < userloginsarray.size(); i++) {
            JsonObject user = (JsonObject) userloginsarray.get(i);
            // Check if the user's name matches the dataset name from DataConfig
            if (user.has("name") && user.get("name").getAsString().equals(datasetName)) {
                JsonObject selectedUser = user.getAsJsonObject("data");

                Object[] data = new Object[2];
                List<String> values = getValues(selectedUser, keys);

                data[0] = values.toArray(new String[0]);
                data[1] = (JavascriptExecutor) driver;

                dataList.add(data);
                break; // Exit after finding the matching user
            }
        }

        if (dataList.isEmpty()) {
            throw new IllegalArgumentException("No user found with name: " + datasetName);
        }

        return dataList.toArray(new Object[0][2]);
    }

    private List<String> getValues(JsonObject jsonObject, List<String> keys) {
        List<String> values = new ArrayList<>();
        for (String key : keys) {
            if (jsonObject.has(key)) {
                values.add(jsonObject.get(key).getAsString());
            } else {
                values.add(""); // Add empty string if key is not found
            }
        }
        return values;
    }
}
