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
    public void verifyLoginAndSearchJob(String[] data, JavascriptExecutor js) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        linkedinData.loginToLinkedIn(driver, data);
        System.out.println("This is new code changes");
        driver.get("https://www.linkedin.com/jobs/search/?currentJobId=3959737275&f_AL=true&keywords=%22Software%20Test%20Engineer%22%20OR%20%22Software%20Tester%22%20OR%20%22QA%20Engineer%22%20AND%20(%22Manual%20Testing%22%20OR%20%22Automation%20Testing%22%20OR%20%22QA%22)&origin=JOB_SEARCH_PAGE_JOB_FILTER&refresh=true&start=100");

//        driver.get("https://www.linkedin.com/jobs/collections/recommended/");
//        linkedinData.navigateToJobs(driver);
//        linkedinData.searchForJobs(driver, data);
//        linkedinData.navigateToNotification(driver);
        boolean jobApplicationSuccessful = false;
        int retryCount = 0;
        int maxRetries = 5;
        while (!jobApplicationSuccessful && retryCount < maxRetries) {
            try {
                linkedinData.applyForJobs(driver, wait, js, data);
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


