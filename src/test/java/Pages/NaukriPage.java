package Pages;

import AppConfg.DataConfg;
import Locators.Naukri_Locators;
import customEntities.GenericMethods;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NaukriPage extends GenericMethods {
    Naukri_Locators locators = new Naukri_Locators();
    String datasetName = DataConfg.getInstance().getDatasetName();
    QuestionAnswerHandler questionAnswerHandler = new QuestionAnswerHandler(datasetName);

    public NaukriPage(WebDriver driver) throws FileNotFoundException {
        PageFactory.initElements(driver, this);
        questionAnswerHandler.initializeQuestionAnswerMap(datasetName);

    }

    public void NaukriLogin(WebDriver driver, String[] data) throws InterruptedException {
        waitForPageLoad(driver);
        sendKeysToElement(driver, locators.EmailID, data[0]);
        sendKeysToElement(driver, locators.Password, data[1]);
        clickElement(driver, locators.LoginButton);
        Thread.sleep(3000);
        if (!findElements(driver, locators.closeIcon).isEmpty()) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement closeIcon = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'crossIcon') and contains(@class, 'chatBot-ic-cross')]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeIcon);
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
            String newWindow = null;

            for (String windowHandle : windowHandles) {
                if (!windowHandle.equals(originalWindow)) {
                    newWindow = windowHandle;
                    break;
                }
            }

            if (newWindow != null) {
                driver.switchTo().window(newWindow);

                if (!findElements(driver, locators.ApplyButton).isEmpty()) {
                    clickElement(driver, locators.ApplyButton);
                    waitForPageLoad(driver);
                    Thread.sleep(2000);

                    handleChatbot(driver);
                    if (driver.getWindowHandles().contains(newWindow)) {
                        driver.close();
                    }
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

    public void handleChatbot(WebDriver driver) throws InterruptedException {
        waitForPageLoad(driver);
        if (!isElementPresent(driver, locators.chatBotPage)) {
            System.out.println("Chatbot is not present");
            return;
        }
        boolean isChatbotActive = true;
        while (isChatbotActive) {
            waitForPageLoad(driver);
            if (isElementPresent(driver, locators.skipThisQues)) {
                clickElement(driver, locators.skipThisQues);
                Thread.sleep(2000);
                continue;
            }
            boolean answerProvided = false;
            if (findElement(driver, locators.answerTextFiled) != null) {
                WebElement answerInput = findElement(driver, locators.answerTextFiled);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", answerInput);
                if (answerInput.isDisplayed() && answerInput.isEnabled()) {
                    Thread.sleep(1000);
                    List<WebElement> questionElement = findElements(driver, locators.chatBotQuestion);
                    WebElement latestQuestionElement = questionElement.get(questionElement.size() - 1); // Get the last question
                    String latestQuestionText = latestQuestionElement.getText();
                    String answers = questionAnswerHandler.getAnswer(latestQuestionText);
                    sendKeysToElement(driver, locators.answerTextFiled, answers);
                    Thread.sleep(2000);
                    answerProvided = true;
                } else {
                    System.out.println("Answer input field is not interactable.");
                }
            } else {
                System.out.println("Answer input field not found.");
            }
            if (!findElements(driver, locators.RadioButtons).isEmpty()) {
                List<WebElement> radioButtons = findElements(driver, locators.RadioButtons);
                WebElement firstRadioButton = radioButtons.get(0);
                try {
                    if (firstRadioButton.isDisplayed() && firstRadioButton.isEnabled()) {
                        firstRadioButton.click();
                    } else {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstRadioButton);
                    }
                    Thread.sleep(2000);
                    answerProvided = true;
                } catch (Exception e) {
                    System.out.println("Error clicking radio button: " + e.getMessage());
                }
            }
            List<WebElement> checkboxes = findElements(driver, locators.checkbox);
            for (WebElement checkbox : checkboxes) {
                if (checkbox.isDisplayed() && checkbox.isEnabled()) {
                    if (!checkbox.isSelected()) {
                        checkbox.click();
                        Thread.sleep(1000);
                    } else {
                        System.out.println("Checkbox already selected.");
                    }
                    answerProvided = true;
                } else {
                    System.out.println("Checkbox is not interactable.");
                }
            }
            if (!findElements(driver, locators.yesButton).isEmpty()) {
                clickElement(driver, locators.yesButton);
            } else {
                System.out.println("Yes button is not present.");
            }

            if (!findElements(driver, locators.SearchFiled).isEmpty()) {
                List<WebElement> questionElement1 = findElements(driver, locators.chatBotQuestion);
                WebElement latestQuestionElement1 = questionElement1.get(questionElement1.size() - 1); // Get the last question
                String latestQuestionText1 = latestQuestionElement1.getText();
                String answers = questionAnswerHandler.getAnswer(latestQuestionText1);
                sendKeysToElement(driver, locators.SearchFiled, answers);
                Thread.sleep(1000);
                WebElement search = findElement(driver, locators.SearchFiled);
                Actions actions = new Actions(driver);
                actions.sendKeys(search, Keys.ENTER).perform();
            }

            if (answerProvided && findElement(driver, locators.saveButtonContainer) != null) {
                WebElement saveButtonContainer = findElement(driver, locators.saveButtonContainer);
                WebElement saveButton = saveButtonContainer.findElement(By.className("sendMsg"));
                saveButton.click();
                waitForPageLoad(driver);
            } else {
                System.out.println("Save button is not enabled or visible.");
            }
            Thread.sleep(2000);
            if (!isElementPresent(driver, locators.chatBotPage)) {
                isChatbotActive = false;
            } else {
                System.out.println("Chatbot is still active, processing next question.");
            }
        }
        System.out.println("Chatbot handling completed");
    }

}
