package Test;

import Pages.NaukriPage;
import customEntities.GenericMethods;
import customEntities.ScreenshotListener;
import customEntities.dataRead;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.FileNotFoundException;


@Listeners({ScreenshotListener.class})
public class NaukariTest {

    public static WebDriver driver;
    GenericMethods generic = new GenericMethods();
    public NaukriPage naukaridata;

    @BeforeClass
    @Parameters("baseURL2")
    public void setUp(String baseURL, ITestContext context) throws FileNotFoundException {
        generic.launchBrowser(baseURL);
        driver = generic.driver;
        naukaridata = new NaukriPage(driver);
        if (driver == null) {
            System.out.println("WebDriver is NULL in setUp! Cannot set context attribute.");
        } else {
            System.out.println("WebDriver is successfully set in setUp. Storing in context...");
            context.setAttribute("WebDriver", driver);
        }
    }

    @Test(priority = 1, dataProvider = "login_cred", dataProviderClass = dataRead.class)
    public void verifyLoginAndUpdateProfile(String[] data, JavascriptExecutor js) throws InterruptedException {
        naukaridata.NaukriLogin(driver, data);
        naukaridata.NaukriUpdate(driver);
        naukaridata.JobApplyFromSearch(driver, data);
        // naukaridata.JobApplyFrom_Recommended(driver);


    }
//    @AfterClass
//    public void tearDown() {
//        driver.quit();
//    }

}
