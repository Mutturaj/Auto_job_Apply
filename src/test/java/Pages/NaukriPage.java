package Pages;

import Locators.Naukri_Locators;
import customEntities.GenericMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NaukriPage extends GenericMethods {
    Naukri_Locators locators = new Naukri_Locators();

    public NaukriPage(WebDriver driver) throws FileNotFoundException {
        PageFactory.initElements(driver, this);
    }

    public void NaukriLogin(WebDriver driver, String[] data) {
        waitForPageLoad(driver);
        sendKeysToElement(driver, locators.EmailID, data[0]);
        sendKeysToElement(driver, locators.Password, data[1]);
        clickElement(driver, locators.LoginButton);
        waitForPageLoad(driver);
        if (isElementPresent(driver, locators.closeIcon)) {
            clickElement(driver, locators.closeIcon);
        }

    }

    public void NaukriUpdate(WebDriver driver) throws InterruptedException {
        waitForPageLoad(driver);
        clickElement(driver, locators.ViewProfile);
        waitForPageLoad(driver);
        clickElement(driver, locators.EditIcon);
        Thread.sleep(3000);
        clickElement(driver, locators.SaveButton);
    }

    public void JobApplyFrom_Recommended(WebDriver driver) throws InterruptedException {
        waitForPageLoad(driver);
        clickElement(driver, locators.JobButton);
        waitForPageLoad(driver);
        JobsApply(driver);

    }

    public void JobApplyFromSearch(WebDriver driver) throws InterruptedException {
        waitForPageLoad(driver);
        scrollToTop(driver);
        Thread.sleep(2000);
        clickElement(driver, locators.SearchIcon1);
        waitForPageLoad(driver);
        sendKeysToElement(driver, locators.Designation, "Automation Test Engineer, Selenium Testing, Selenium Java, Software Test Engineer, QA Automation");
        clickElement(driver, locators.Experience);
        clickElement(driver, locators.ExperienceButton);
        sendKeysToElement(driver, locators.Location, "Bangalore, Hyderabad");
        clickElement(driver, locators.SearchIcon2);
        waitForPageLoad(driver);
        JobsApply(driver);

    }

    private void JobsApply(WebDriver driver) throws InterruptedException {
        Thread.sleep(4000);
        List<WebElement> jobList = new ArrayList<>();
        if (isElementPresent(driver, locators.listOfJobs)) {
            jobList.addAll(findElements(driver, locators.listOfJobs));
        }
        if (isElementPresent(driver, locators.listOfJobsFromSearch)) {
            jobList.addAll(findElements(driver, locators.listOfJobsFromSearch));
        }
        String originalWindow = driver.getWindowHandle();
        for (WebElement job : jobList) {
            job.click();
            Thread.sleep(3000);
            Set<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
            if (!findElements(driver, locators.ApplyButton).isEmpty()) {
                clickElement(driver, locators.ApplyButton);
                Thread.sleep(2000);
                driver.close();
                driver.switchTo().window(originalWindow);
            } else if (!findElements(driver, locators.ApplyCompanySite).isEmpty()) {
                driver.close();
                driver.switchTo().window(originalWindow);
            } else {
                driver.close();
                driver.switchTo().window(originalWindow);
            }

        }
    }

}

