package AppConfg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dataset {
    @JsonProperty("name")
    private String name;
    @JsonProperty("data")
    private UserData data;

}
