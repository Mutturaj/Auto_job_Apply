package AppConfg;

public class DataConfig {
    private static DataConfig instance;
    private String datasetName;

    private DataConfig() {
        this.datasetName = "Bindu"; // Set your default value here

    }

    public static DataConfig getInstance() {
        if (instance == null) {
            instance = new DataConfig();
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
