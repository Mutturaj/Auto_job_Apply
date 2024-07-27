package customEntities;

import AppConfg.DataConfg;
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

import static Test.LinkedinTest.driver;

public class dataRead {


    @DataProvider(name = "login_cred")
    public Object[][] Jsonreader() throws FileNotFoundException {
        return provideLoginCredentials("data", DataConfg.getInstance().getDatasetName());
    }

    public Object[][] provideLoginCredentials(String dataKey, String name) throws FileNotFoundException {
        JsonParser jsonParser = new JsonParser();
        FileReader reader = new FileReader("C:\\Users\\Welcome\\Downloads\\testrail_poc-master\\AutoJob_Apply\\test.json");
        Object obj = jsonParser.parse(reader);
        JsonObject userlogin = (JsonObject) obj;
        JsonArray userloginsarray = (JsonArray) userlogin.get(dataKey);

        JsonObject selectedUser = null;
        for (int i = 0; i < userloginsarray.size(); i++) {
            JsonObject user = (JsonObject) userloginsarray.get(i);
            if (user.has("name") && user.get("name").getAsString().equals(name)) {
                selectedUser = user.getAsJsonObject("data");
                break;
            }
        }

        if (selectedUser == null) {
            throw new IllegalArgumentException("No user found with name: " + name);
        }

        Object[][] data = new Object[1][2];
        List<String> keys = Arrays.asList("EmailID", "Password", "JobTitle", "JobLocation", "Hiring Manager Message");
        List<String> values = getValues(selectedUser, keys);
        data[0][0] = values.toArray(new String[0]);
        data[0][1] = (JavascriptExecutor) driver;

        return data;
    }

    private List<String> getValues(JsonObject jsonObject, List<String> keys) {
        List<String> values = new ArrayList<>();
        for (String key : keys) {
            if (jsonObject.has(key)) {
                values.add(jsonObject.get(key).getAsString());
            } else {
                values.add("");
            }
        }
        return values;
    }
}
