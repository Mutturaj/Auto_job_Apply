package Test;

import Pages.NaukriPage;
import customEntities.GenericMethods;
import customEntities.dataRead;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.time.Duration;

public class NaukariTest {

    public static WebDriver driver;
    GenericMethods generic = new GenericMethods();
    public NaukriPage naukaridata;

    @BeforeClass
    @Parameters("baseURL2")
    public void setUp(String baseURL) throws FileNotFoundException {
        generic.launchBrowser(baseURL);
        driver = generic.driver;
        naukaridata = new NaukriPage(driver);
    }

    @Test(priority = 1, dataProvider = "login_cred", dataProviderClass = dataRead.class)
    public void verifyLoginAndUpdateProfile(String[] data, JavascriptExecutor js) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        naukaridata.NaukriLogin(driver, data);
        naukaridata.NaukriUpdate(driver);
        naukaridata.JobApplyFromSearch(driver);
        naukaridata.JobApplyFrom_Recommended(driver);


    }

}
