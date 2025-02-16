package AppConfg;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Jsonparse {

    public static void main(String[] args) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseWrapper jsonResponse = objectMapper.readValue(new File("C:\\Users\\Welcome\\Downloads\\testrail_poc-master\\AutoJob_Apply\\dataset.json"), ResponseWrapper.class);
        System.out.println(jsonResponse.getDatasets().get(2).getData().getEmailID());

    }

}
