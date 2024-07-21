package Test;

import Pages.LinkedinPage;
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

public class LinkedinTest {

    public static WebDriver driver;
    GenericMethods generic = new GenericMethods();
    public LinkedinPage linkedinData;

    @BeforeClass
    @Parameters("baseURL")
    public void setUp(String baseURL) throws FileNotFoundException {
        generic.launchBrowser(baseURL);
        driver = generic.driver;
        linkedinData = new LinkedinPage(driver);
    }

    @Test(priority = 1, dataProvider = "login_cred", dataProviderClass = dataRead.class)
    public void verifyLoginAndSearchJob(String[] data, JavascriptExecutor js) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        linkedinData.loginToLinkedIn(driver, data);
        linkedinData.navigateToJobs(driver);
        linkedinData.searchForJobs(driver, data);
        boolean jobApplicationSuccessful = false;
        int retryCount = 0;
        int maxRetries = 5;
        while (!jobApplicationSuccessful && retryCount < maxRetries) {
            try {
                linkedinData.applyForJobs(driver, wait, js,data);
                jobApplicationSuccessful = true;
            } catch (Exception e) {
                System.out.println("Apply for jobs failed. Retrying...");
                driver.navigate().refresh();
                Thread.sleep(5000);
                retryCount++;
            }
        }

        if (!jobApplicationSuccessful) {
            System.out.println("Maximum retries reached. Failed to apply for jobs.");
        }
    }
}

