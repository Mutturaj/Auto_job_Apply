package AppConfg;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class ResponseWrapper {
    @JsonProperty("datasets")
    private List<Dataset> datasets;
}