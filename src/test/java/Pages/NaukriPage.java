package Pages;

import AppConfg.DataConfig;
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

    QuestionAnswerHandler questionAnswerHandler;

    public NaukriPage(WebDriver driver) throws FileNotFoundException {
        PageFactory.initElements(driver, this);
        String currentDatasetName = DataConfig.getInstance().getDatasetName();

        if (currentDatasetName != null) {
            questionAnswerHandler = new QuestionAnswerHandler(currentDatasetName);
            questionAnswerHandler.initializeQuestionAnswerMap(currentDatasetName);
        }
    }

    public void NaukriLogin(WebDriver driver, String[] data) throws InterruptedException {
        waitForPageLoad(driver);
        sendKeysToElement(driver, locators.EmailID, data[0]);
        sendKeysToElement(driver, locators.Password, data[1]);
        clickElement(driver, locators.LoginButton);
        waitForPageLoad(driver);
        Thread.sleep(3000);
    }

    public void NaukriUpdate(WebDriver driver) throws InterruptedException {
        waitForPageLoad(driver);
        driver.get("https://www.naukri.com/mnjuser/profile");
        driver.navigate().refresh();
        Thread.sleep(2000);

        if (!isElementPresent(driver, locators.chatBotPage)) {
            System.out.println("Chatbot is not present");
            return;
        }

        boolean isChatbotActive = true;

        while (isChatbotActive) {
            waitForPageLoad(driver);

            if (isElementPresent(driver, locators.chatBotPage)) {
                WebElement closeIcon = driver.findElement(By.xpath("//div[@class='crossIcon chatBot chatBot-ic-cross']"));
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.elementToBeClickable(closeIcon));

                Actions actions = new Actions(driver);
                actions.moveToElement(closeIcon).click().perform();

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeIcon);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!isElementPresent(driver, locators.chatBotPage)) {
                    isChatbotActive = false;
                    System.out.println("Chatbot closed successfully");
                }
            } else {
                isChatbotActive = false;
            }
        }
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

    public void JobApplyFromSearch(WebDriver driver, String data[]) throws InterruptedException {
        waitForPageLoad(driver);
        scrollToTop(driver);
        Thread.sleep(2000);
        clickElement(driver, locators.SearchIcon1);
        waitForPageLoad(driver);
        sendKeysToElement(driver, locators.Designation, data[3]);
        clickElement(driver, locators.ExperienceButton);
        String experience = data[5].replace("\"", "");
        String xpath = "//span[.='" + experience + " years']";
        WebElement exper = driver.findElement(By.xpath(xpath));
        exper.click();
        sendKeysToElement(driver, locators.Location, data[4]);
        clickElement(driver, locators.SearchIcon2);
        waitForPageLoad(driver);
        JobsApply(driver);

    }

    private void JobsApply(WebDriver driver) throws InterruptedException {
        applyJobsOnCurrentPage(driver);

        while (true) {
            List<WebElement> pageNumbers = findElements(driver, locators.pageNum);

            boolean nextPageFound = false;

            for (int i = 0; i < pageNumbers.size(); i++) {
                Thread.sleep(2000);
                pageNumbers = findElements(driver, locators.pageNum);
                if (pageNumbers.get(i).getAttribute("class").contains("styles_selected__")) {
                    if (i + 1 < pageNumbers.size()) {
                        pageNumbers.get(i + 1).click();
                        Thread.sleep(2000);
                        nextPageFound = true;
                        applyJobsOnCurrentPage(driver);
                        break;
                    }
                }
            }

            if (!nextPageFound) {
                System.out.println("No more pages to navigate.");
                break;
            }
        }
    }

    private void applyJobsOnCurrentPage(WebDriver driver) throws InterruptedException {
        Thread.sleep(2000);
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
                    JobName(driver);
                    waitForPageLoad(driver);
                    Thread.sleep(2000);
                    handleChatbot(driver);

                    if (driver.getWindowHandles().contains(newWindow)) {
                        System.out.println("Closing the chatbot window.");
                        driver.close();
                    }
                    driver.switchTo().window(originalWindow);
                } else if (!findElements(driver, locators.ApplyCompanySite).isEmpty()) {
                    System.out.println("Closing the company site window.");
                    driver.close();
                    driver.switchTo().window(originalWindow);
                } else {
                    System.out.println("Closing the window as no relevant buttons were found.");
                    driver.close();
                    driver.switchTo().window(originalWindow);
                }
            } else {
                System.out.println("No new window found.");
            }
        }
    }

//    public void handleChatbot(WebDriver driver) throws InterruptedException {
//        waitForPageLoad(driver);
//        if (!isElementPresent(driver, locators.chatBotPage)) {
//            System.out.println("Chatbot is not present");
//            return;
//        }
//
//        boolean isChatbotActive = true;
//        while (isChatbotActive) {
//            waitForPageLoad(driver);
//
//            boolean skipButtonClicked = false;
//            boolean answerProvided = false;
//
//            if (isElementPresent(driver, locators.skipThisQues)) {
//                clickElement(driver, locators.skipThisQues);
//                skipButtonClicked = true;
//                Thread.sleep(2000);
//                continue;
//            }
//
//            if (!findElements(driver, locators.DOBfield).isEmpty()) {
//                handleDOB(driver);
//                answerProvided = true;
//            }
//
//            if (!answerProvided && isElementPresent(driver, locators.answerTextFiled)) {
//                WebElement answerInput = findElement(driver, locators.answerTextFiled);
//                if (answerInput != null && answerInput.isDisplayed() && answerInput.isEnabled()) {
//                    Thread.sleep(1000);
//                    List<WebElement> questionElement = findElements(driver, locators.chatBotQuestion);
//                    WebElement latestQuestionElement = questionElement.get(questionElement.size() - 1); // Get the last question
//                    String latestQuestionText = latestQuestionElement.getText();
//                    String answers = questionAnswerHandler.getAnswer(latestQuestionText);
//                    sendKeysToElement(driver, locators.answerTextFiled, answers);
//                    Thread.sleep(2000);
//                    answerProvided = true;
//                } else {
//                    System.out.println("Answer input field is not interactable or not found.");
//                }
//            }
//
//            if (!answerProvided && !findElements(driver, locators.RadioButtons).isEmpty()) {
//                List<WebElement> radioButtons = findElements(driver, locators.RadioButtons);
//                WebElement firstRadioButton = radioButtons.get(0);
//                try {
//                    if (firstRadioButton.isDisplayed() && firstRadioButton.isEnabled()) {
//                        firstRadioButton.click();
//                    } else {
//                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstRadioButton);
//                    }
//                    Thread.sleep(2000);
//                    answerProvided = true;
//                } catch (Exception e) {
//                    System.out.println("Error clicking radio button: " + e.getMessage());
//                }
//            }
//
//            if (!answerProvided) {
//                List<WebElement> checkboxes = findElements(driver, locators.checkbox);
//                for (WebElement checkbox : checkboxes) {
//                    if (checkbox.isDisplayed() && checkbox.isEnabled()) {
//                        if (!checkbox.isSelected()) {
//                            checkbox.click();
//                            Thread.sleep(1000);
//                        } else {
//                            System.out.println("Checkbox already selected.");
//                        }
//                        answerProvided = true;
//                    } else {
//                        System.out.println("Checkbox is not interactable.");
//                    }
//                }
//            }
//            if (!answerProvided && !findElements(driver, locators.yesButton).isEmpty()) {
//                clickElement(driver, locators.yesButton);
//                answerProvided = true;
//            }
//
//            if (!answerProvided && !findElements(driver, locators.SearchFiled).isEmpty()) {
//                List<WebElement> questionElement1 = findElements(driver, locators.chatBotQuestion);
//                WebElement latestQuestionElement1 = questionElement1.get(questionElement1.size() - 1);
//                String latestQuestionText1 = latestQuestionElement1.getText();
//                String answers = questionAnswerHandler.getAnswer(latestQuestionText1);
//                sendKeysToElement(driver, locators.SearchFiled, answers);
//                Thread.sleep(2000);
//                clickElement(driver, locators.suggestionList);
//                answerProvided = true;
//            }
//
//            if (answerProvided && !skipButtonClicked && findElement(driver, locators.saveButtonContainer) != null) {
//                WebElement saveButtonContainer = findElement(driver, locators.saveButtonContainer);
//                WebElement saveButton = saveButtonContainer.findElement(By.className("sendMsg"));
//                if (saveButton.isEnabled()) {
//                    saveButton.click();
//                    waitForPageLoad(driver);
//                } else {
//                    System.out.println("Save button is present but not enabled.");
//                }
//            } else {
//                if (skipButtonClicked) {
//                    System.out.println("Skip button was clicked, not processing save button.");
//                } else {
//                    System.out.println("Save button is not enabled, visible, or answer not provided.");
//                }
//            }
//            Thread.sleep(2000);
//            if (!isElementPresent(driver, locators.chatBotPage)) {
//                isChatbotActive = false;
//            } else {
//                System.out.println("Chatbot is still active, processing next question.");
//            }
//        }
//        System.out.println("Chatbot handling completed");
//    }

    public void handleDOB(WebDriver driver) throws InterruptedException {
        List<WebElement> dobFields = findElements(driver, locators.DOBfield);

        if (dobFields.isEmpty()) {
            System.out.println("No DOB fields found on the page.");
            return;
        }
        String questionText = findElement(driver, locators.chatBotQuestion).getText();
        String dateValue = questionAnswerHandler.getAnswer(questionText);
        if (dateValue != null && !dateValue.isEmpty()) {
            String[] dateParts = dateValue.split("/");
            String day = dateParts[0];
            String month = dateParts[1];
            String year = dateParts[2];

            for (WebElement dobField : dobFields) {
                String placeholder = dobField.getAttribute("placeholder");
                String ariaLabel = dobField.getAttribute("aria-label");
                String fieldType = (placeholder != null && !placeholder.isEmpty()) ? placeholder :
                        (ariaLabel != null && !ariaLabel.isEmpty()) ? ariaLabel : "";

                String answer = "";

                if (fieldType.contains("Day") || fieldType.equalsIgnoreCase("DD")) {
                    answer = day;
                } else if (fieldType.contains("Month") || fieldType.equalsIgnoreCase("MM")) {
                    answer = month;
                } else if (fieldType.contains("Year") || fieldType.equalsIgnoreCase("YYYY")) {
                    answer = year;
                }

                if (answer != null && !answer.isEmpty()) {
                    dobField.sendKeys(answer);
                } else {
                    System.out.println("Unrecognized DOB field type, using generic input: " + fieldType);
                    dobField.sendKeys("01");  // Default value for unrecognized fields
                }
            }
        } else {
            System.out.println("No valid date found for the question: " + questionText);
        }
    }

    private int appliedJobsCount = 0;

    private void JobName(WebDriver driver) {
        waitForPageLoad(driver);
        String jobTitle = findElement(driver, locators.JobTitle).getText();
        String cleanJobTitle = jobTitle.replace("Apply to ", "");
        appliedJobsCount++;
        System.out.println("Applied to  Job from Naukri: " + cleanJobTitle);
        System.out.println("Total Jobs Applied on Naukri: " + appliedJobsCount);
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

            boolean skipButtonClicked = handleSkipQuestion(driver);
            boolean answerProvided = handleAnswers(driver);

            handleSaveButton(driver, skipButtonClicked, answerProvided);

            Thread.sleep(2000);
            isChatbotActive = isElementPresent(driver, locators.chatBotPage);
            if (isChatbotActive) {
                System.out.println("Chatbot is still active, processing next question.");
            }
        }
        System.out.println("Chatbot handling completed");
    }

    private boolean handleSkipQuestion(WebDriver driver) throws InterruptedException {
        if (isElementPresent(driver, locators.skipThisQues)) {
            clickElement(driver, locators.skipThisQues);
            Thread.sleep(2000);
            return true;
        }
        return false;
    }

    private boolean handleAnswers(WebDriver driver) throws InterruptedException {
        if (!findElements(driver, locators.DOBfield).isEmpty()) {
            handleDOB(driver);
            return true;
        }

        if (handleTextFieldAnswer(driver)) {
            return true;
        }

        if (handleRadioButtons(driver)) {
            return true;
        }

        if (handleCheckboxes(driver)) {
            return true;
        }

        if (handleYesButton(driver)) {
            return true;
        }

        if (handleSearchField(driver)) {
            return true;
        }

        return false;
    }

    private boolean handleTextFieldAnswer(WebDriver driver) throws InterruptedException {
        if (isElementPresent(driver, locators.answerTextFiled)) {
            WebElement answerInput = findElement(driver, locators.answerTextFiled);
            if (answerInput != null && answerInput.isDisplayed() && answerInput.isEnabled()) {
                Thread.sleep(1000);
                List<WebElement> questionElement = findElements(driver, locators.chatBotQuestion);
                WebElement latestQuestionElement = questionElement.get(questionElement.size() - 1);
                String latestQuestionText = latestQuestionElement.getText();
                String answers = questionAnswerHandler.getAnswer(latestQuestionText);
                sendKeysToElement(driver, locators.answerTextFiled, answers);
                Thread.sleep(2000);
                return true;
            }
        }
        return false;
    }

    private boolean handleRadioButtons(WebDriver driver) throws InterruptedException {
        List<WebElement> radioButtons = findElements(driver, locators.RadioButtons);
        if (!radioButtons.isEmpty()) {
            WebElement firstRadioButton = radioButtons.get(0);
            try {
                if (firstRadioButton.isDisplayed() && firstRadioButton.isEnabled()) {
                    firstRadioButton.click();
                } else {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstRadioButton);
                }
                Thread.sleep(2000);
                return true;
            } catch (Exception e) {
                System.out.println("Error clicking radio button: " + e.getMessage());
            }
        }
        return false;
    }

    private boolean handleCheckboxes(WebDriver driver) throws InterruptedException {
        List<WebElement> checkboxes = findElements(driver, locators.checkbox);
        for (WebElement checkbox : checkboxes) {
            if (checkbox.isDisplayed() && checkbox.isEnabled()) {
                if (!checkbox.isSelected()) {
                    checkbox.click();
                    Thread.sleep(1000);
                } else {
                    System.out.println("Checkbox already selected.");
                }
                return true;
            }
        }
        return false;
    }

    private boolean handleYesButton(WebDriver driver) {
        if (!findElements(driver, locators.yesButton).isEmpty()) {
            clickElement(driver, locators.yesButton);
            return true;
        }
        return false;
    }

    private boolean handleSearchField(WebDriver driver) throws InterruptedException {
        if (!findElements(driver, locators.SearchFiled).isEmpty()) {
            List<WebElement> questionElement = findElements(driver, locators.chatBotQuestion);
            WebElement latestQuestionElement = questionElement.get(questionElement.size() - 1);
            String latestQuestionText = latestQuestionElement.getText();
            String answers = questionAnswerHandler.getAnswer(latestQuestionText);
            sendKeysToElement(driver, locators.SearchFiled, answers);
            Thread.sleep(2000);
            clickElement(driver, locators.suggestionList);
            return true;
        }
        return false;
    }

    private void handleSaveButton(WebDriver driver, boolean skipButtonClicked, boolean answerProvided) throws InterruptedException {
        if (answerProvided && !skipButtonClicked && findElement(driver, locators.saveButtonContainer) != null) {
            WebElement saveButtonContainer = findElement(driver, locators.saveButtonContainer);
            WebElement saveButton = saveButtonContainer.findElement(By.className("sendMsg"));
            if (saveButton.isEnabled()) {
                saveButton.click();
                waitForPageLoad(driver);
            } else {
                System.out.println("Save button is present but not enabled.");
            }
        } else {
            if (skipButtonClicked) {
                System.out.println("Skip button was clicked, not processing save button.");
            } else {
                System.out.println("Save button is not enabled, visible, or answer not provided.");
            }
        }
    }

}

