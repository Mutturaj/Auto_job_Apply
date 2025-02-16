package AppConfg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {
    @JsonProperty("EmailID")
    private String emailID;
    @JsonProperty("Password")
    private String password;
    @JsonProperty("JobTitle")
    private String jobTitle;
}
