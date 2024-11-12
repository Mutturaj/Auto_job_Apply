package Test;

import Pages.LinkedinPage;
import customEntities.GenericMethods;
import customEntities.ScreenshotListener;
import customEntities.dataRead;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.time.Duration;

@Listeners({ScreenshotListener.class})
public class LinkedinTest {

    public static WebDriver driver;
    GenericMethods generic = new GenericMethods();
    public LinkedinPage linkedinData;

    @BeforeClass
    @Parameters("baseURL1")
    public void setUp(String baseURL, ITestContext context) throws FileNotFoundException {
        generic.launchBrowser(baseURL);
        driver = generic.driver;
        context.setAttribute("WebDriver", driver);
        linkedinData = new LinkedinPage(driver);
    }

    @Test(priority = 1, dataProvider = "login_cred", dataProviderClass = dataRead.class)
    public void applyJobFromSearchJob(String[] data, JavascriptExecutor js) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        linkedinData.loginToLinkedIn(driver, data);
        linkedinData.navigateToJobs(driver);
        linkedinData.searchForJobs(driver, data);
        linkedinData.applyForJobs(driver, wait, js, data);

    }

    @Test(priority = 3, dataProvider = "login_cred", dataProviderClass = dataRead.class)
    public void applyJobFromNotifications(String[] data, JavascriptExecutor js) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        linkedinData.navigateToNotification(driver);
        linkedinData.applyForJobs(driver, wait, js, data);

    }

    @Test(priority = 2, dataProvider = "login_cred", dataProviderClass = dataRead.class)
    public void applyJobFromCollection(String[] data, JavascriptExecutor js) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("https://www.linkedin.com/jobs/collections/recommended/");
        linkedinData.applyForJobs(driver, wait, js, data);

    }
}