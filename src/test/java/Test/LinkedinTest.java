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

import java.awt.*;
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
        System.out.println("Launching browser...");
        generic.launchBrowser(baseURL);
        driver = generic.driver;  // Ensure WebDriver instance is assigned
        linkedinData = new LinkedinPage(driver);

        if (driver == null) {
            System.out.println("WebDriver is NULL in setUp! Cannot set context attribute.");
        } else {
            System.out.println("WebDriver is successfully set in setUp. Storing in context...");
            context.setAttribute("WebDriver", driver);
        }
    }

    @Test(priority = 1, dataProvider = "login_cred", dataProviderClass = dataRead.class)
    public void applyJobFromSearchJob(String[] data, JavascriptExecutor js) throws InterruptedException, AWTException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        linkedinData.loginToLinkedIn(driver, data);
        linkedinData.navigateToJobs(driver);
        linkedinData.searchForJobs(driver, data);
        linkedinData.ZoomOut(driver);
        linkedinData.applyForJobs(driver, wait, js, data);


    }

    @Test(priority = 2, dataProvider = "login_cred", dataProviderClass = dataRead.class)
    public void applyJobFromCollection(String[] data, JavascriptExecutor js) throws InterruptedException, AWTException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("https://www.linkedin.com/jobs/collections/recommended/");
        linkedinData.applyForJobs(driver, wait, js, data);

    }

    @Test(priority = 3, dataProvider = "login_cred", dataProviderClass = dataRead.class)
    public void applyJobFromNotifications(String[] data, JavascriptExecutor js) throws InterruptedException, AWTException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        linkedinData.navigateToNotification(driver);
        linkedinData.applyForJobs(driver, wait, js, data);

    }

    @Test(priority = 4, dataProvider = "login_cred", dataProviderClass = dataRead.class)
    public void SignOut(String[] data, JavascriptExecutor js) throws InterruptedException, AWTException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        linkedinData.SignedOut(driver, wait);

    }
}

