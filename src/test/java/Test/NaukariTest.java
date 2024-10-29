package Test;

import Pages.NaukriPage;
import customEntities.GenericMethods;
import customEntities.ScreenshotListener;
import customEntities.dataRead;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.io.FileNotFoundException;


@Listeners({ScreenshotListener.class})
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
        naukaridata.NaukriLogin(driver, data);
        naukaridata.NaukriUpdate(driver);
        naukaridata.JobApplyFromSearch(driver);
        naukaridata.JobApplyFrom_Recommended(driver);


    }
//    @AfterClass
//    public void tearDown() {
//        driver.quit();
//    }

}
