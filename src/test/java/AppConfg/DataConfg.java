package AppConfg;

public class DataConfg {
    private static DataConfg instance;
    private String datasetName;

    private DataConfg() {
        this.datasetName = "Jayanth"; // Set your default value here

    }

    public static DataConfg getInstance() {
        if (instance == null) {
            instance = new DataConfg();
        }
        return instance;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }
}
