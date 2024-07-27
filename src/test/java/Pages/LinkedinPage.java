package Pages;

import AppConfg.DataConfg;
import Locators.Linkedin_Locators;
import customEntities.GenericMethods;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.*;


public class LinkedinPage extends GenericMethods {
    Linkedin_Locators locators = new Linkedin_Locators();

    String datasetName = DataConfg.getInstance().getDatasetName();
    QuestionAnswerHandler questionAnswerHandler = new QuestionAnswerHandler(datasetName);


    public LinkedinPage(WebDriver driver) throws FileNotFoundException {
        PageFactory.initElements(driver, this);
        questionAnswerHandler.initializeQuestionAnswerMap(datasetName);
    }

    public void loginToLinkedIn(WebDriver driver, String[] data) throws InterruptedException {
        waitForPageLoad(driver);
        sendKeysToElement(driver, locators.EmailID, data[0]);
        sendKeysToElement(driver, locators.Password, data[1]);
        clickElement(driver, locators.SignInButton);
    }

    public void navigateToJobs(WebDriver driver) throws InterruptedException {
        waitForPageLoad(driver);
        clickElement(driver, locators.JobIcon);
        waitForElement(driver, locators.ShowAllButton);
        clickElement(driver, locators.ShowAllButton);
        // clickElement(driver, locators.SearchIcon);

    }

    public void searchForJobs(WebDriver driver, String[] data) throws InterruptedException {
        waitForPageLoad(driver);
        Actions actions = new Actions(driver);
        executeJavaScript(driver, "arguments[0].removeAttribute('disabled');", locators.TitleOfJob);
        executeJavaScript(driver, "arguments[0].removeAttribute('aria-hidden');", locators.TitleOfJob);
        sendKeysToElement(driver, locators.TitleOfJob, data[2]);
        waitForPageLoad(driver);
        WebElement titleElement = findElement(driver, locators.TitleOfJob);
        actions.sendKeys(titleElement, Keys.ENTER).perform();
        waitForPageLoad(driver);
        executeJavaScript(driver, "arguments[0].removeAttribute('disabled');", locators.LocationOfJob);
        executeJavaScript(driver, "arguments[0].removeAttribute('aria-hidden');", locators.LocationOfJob);
        sendKeysToElement(driver, locators.LocationOfJob, data[3]);
        waitForPageLoad(driver);
        WebElement locationElement = findElement(driver, locators.LocationOfJob);
        actions.sendKeys(locationElement, Keys.ENTER).perform();
        //clickElement(driver, locators.SearchBox);
        waitForPageLoad(driver);
        clickElement(driver, locators.EasyApplyFilter);
        Thread.sleep(4000);
    }

    public void applyForJobs(WebDriver driver, WebDriverWait wait, JavascriptExecutor js, String[] data) throws InterruptedException {
        int currentPage = 2;
        int maxScrollAttempts = 10;
        int scrollAttempts = 0;

        while (true) {
            List<WebElement> jobs = findElements(driver, locators.listOfJobs);
            if (!isElementListEmpty(jobs)) {
                for (int i = 0; i < jobs.size(); i++) {
                    WebElement easyApply = jobs.get(i);
                    try {
                        executeJavaScript(driver, "arguments[0].scrollIntoView(true);", easyApply);
                        wait.until(ExpectedConditions.elementToBeClickable(easyApply));
                        easyApply.click();
                        Thread.sleep(3000);

                        if (isElementPresent(driver, locators.easyApplyButton) || isElementPresent(driver, locators.continueButton)) {
                            ClickEasyApplyButtonORContinueButton(driver);
                            handleJobApplicationProcess(driver, wait, data);
                        } else {
                            System.out.println("Easy Apply or Continue button not present, Marking as a Applied");
                            executeJavaScript(driver, "arguments[0].textContent = 'Applied';", easyApply);
                            waitForPageLoad(driver);
                            jobs = findElements(driver, locators.listOfJobs);
                            i--;
                        }
                        handleJobApplicationProcess(driver, wait, data);
                    } catch (ElementClickInterceptedException e) {
                        System.out.println("ElementClickInterceptedException caught, moving to the next job.");
                    }
                }
                Thread.sleep(4000);
                scrollAttempts = 0;
            } else {
                boolean jobsFound = false;
                boolean pagesFound = false;

                while (!jobsFound && !pagesFound && scrollAttempts < maxScrollAttempts) {
                    js.executeScript("document.querySelector('.scaffold-layout__list .jobs-search-results-list').scrollTop += 450;");
                    waitForPageLoad(driver);
                    jobs = findElements(driver, locators.listOfJobs);
                    jobsFound = !jobs.isEmpty();
                    pagesFound = !findElements(driver, locators.listOfPages).isEmpty();
                    scrollAttempts++;
                }

                if (!jobsFound && scrollAttempts >= maxScrollAttempts) {
                    System.out.println("Reached maximum scroll attempts, navigating to next page...");
                    if (navigatePagination(driver, currentPage)) {
                        currentPage++;
                        waitForPageLoad(driver);
                        scrollAttempts = 0;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    private boolean navigatePagination(WebDriver driver, int currentPage) {
        try {
            while (true) {
                String nextPageXPath = "//ul[@class='artdeco-pagination__pages artdeco-pagination__pages--number']//li//button[@aria-label='Page " + currentPage + "']";
                WebElement nextPageButton = driver.findElement(By.xpath(nextPageXPath));

                if (nextPageButton != null) {
                    System.out.println("Navigating to page: " + currentPage);
                    executeJavaScript(driver, "arguments[0].scrollIntoView(true);", nextPageButton);
                    waitForElementToBeClickable(driver, nextPageButton);

                    try {
                        nextPageButton.click();
                    } catch (ElementNotInteractableException e) {
                        System.out.println("ElementNotInteractableException occurred, attempting JavaScript click on page: " + currentPage);
                        executeJavaScript(driver, "arguments[0].click();", nextPageButton);
                    }
                    Thread.sleep(4000);
                    currentPage++;

                    return true;
                } else {
                    System.out.println("No more pages to navigate.");
                    break;
                }
            }
        } catch (TimeoutException e) {
            System.out.println("TimeoutException occurred while waiting for the element to be clickable " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception occurred while navigating pagination " + e.getMessage());
        }
        return false;
    }

    private void handleJobApplicationProcess(WebDriver driver, WebDriverWait wait, String[] data) throws InterruptedException {
        boolean applicationSubmitted = false;
        waitForPageLoad(driver);
        while (!applicationSubmitted) {
            if (isElementPresent(driver, locators.submitApplicationButton)) {
                applicationSubmitted = clickSubmitApplication(driver);
                if (applicationSubmitted) {
                    System.out.println("Application submitted");
                    break;
                } else {
                    System.out.println("Failed to submit application");
                    break;
                }
            }

            if (isElementPresent(driver, locators.continueApplyingButton)) {
                clickElement(driver, locators.continueApplyingButton);
                waitForPageLoad(driver);
                continue;
            }
            if (nextButton(driver)) {
                waitForPageLoad(driver);
                handleNextSection(driver, wait, data);
                continue;
            }
            if (isElementPresent(driver, locators.reviewButton)) {
                waitForPageLoad(driver);
                handleReviewSection(driver, wait, data);
            }
            applicationSubmitted = clickSubmitApplication(driver);
            if (applicationSubmitted) {
                System.out.println("Application submitted");
            } else {
                System.out.println("Failed to submit application");
            }
            break;
        }
        waitForPageLoad(driver);
        if (applicationSubmitted) {
            Thread.sleep(3000);
            if (isElementPresent(driver, locators.DoneButton)) {
                waitForElement(driver, locators.DoneButton);
                clickElement(driver, locators.DoneButton);
                System.out.println("Clicked Done button");
            } else {
                waitForElement(driver, locators.DoneButton);
                System.out.println("Done button not found Truing to find and clicking once again");
                clickElement(driver, locators.DoneButton);

            }
        }
    }

    private void handleInterferingElements(WebDriver driver) {
        List<WebElement> overlays = driver.findElements(By.xpath("//div[@class='overlay-class']"));
        for (WebElement overlay : overlays) {
            if (overlay.isDisplayed()) {
                overlay.click();
            }
        }
    }

    public void ClickEasyApplyButtonORContinueButton(WebDriver driver) throws InterruptedException {
        if (!isElementListEmpty(findElements(driver, locators.continueButton))) {
            waitForElement(driver, locators.continueButton);
            clickElement(driver, locators.continueButton);
        } else {
            waitForElement(driver, locators.easyApplyButton);
            clickElement(driver, locators.easyApplyButton);
        }
        Thread.sleep(2000);
    }

    private void fillTextQuestions(WebDriver driver) throws InterruptedException {
        List<WebElement> questions1 = findElements(driver, locators.Questions1);
        List<WebElement> questions2 = findElements(driver, locators.Question2);
        List<WebElement> allQuestions = new ArrayList<>();
        allQuestions.addAll(questions1);
        allQuestions.addAll(questions2);
        List<WebElement> answerFields = findElements(driver, locators.AnswerTextfield);
        for (int i = 0; i < allQuestions.size(); i++) {
            WebElement questionElement = allQuestions.get(i);
            WebElement answerField = answerFields.get(i);
            String questionText = questionElement.getText();
            String answer = questionAnswerHandler.getAnswer(questionText);
            answerField.clear();
            answerField.sendKeys(answer);
        }
    }

    private void EnterCityName(WebDriver driver) throws InterruptedException {
        if (!findElements(driver, locators.cityName).isEmpty()) {
            List<WebElement> cityNameTextfield = findElements(driver, locators.cityName);
            for (WebElement send : cityNameTextfield) {
                send.sendKeys("Bengaluru, Karnataka, India");
                Thread.sleep(2000);
                WebElement ele = findElement(driver, locators.cityName);
                Actions actions = new Actions(driver);
                actions.sendKeys(ele, Keys.ENTER).perform();
            }
        }
    }

    private boolean clickContinueApplying(WebDriver driver) throws InterruptedException {
        List<WebElement> ContinueApplying = findElements(driver, locators.continueApplyingButton);
        if (!isElementListEmpty(ContinueApplying)) {
            waitForElementToBeClickable(driver, locators.continueApplyingButton);
            System.out.println("Continue Applying button is Present");
            return true;
        } else {
            System.out.println("Continue Applying button is not present.");
        }
        return false;
    }

    private boolean clickSubmitApplication(WebDriver driver) throws InterruptedException {
        boolean isSubmitted = false;
        List<WebElement> submitButtons = findElements(driver, locators.submitApplicationButton);
        if (!isElementListEmpty(submitButtons)) {
            scrollToElement(driver, locators.submitApplicationButton);
            clickElement(driver, locators.submitApplicationButton);
            isSubmitted = true;
        }
        return isSubmitted;
    }

    private void handleRadioButtons1(WebDriver driver) throws InterruptedException {
        List<WebElement> radioGroups = driver.findElements(By.xpath("//fieldset[@data-test-form-builder-radio-button-form-component='true']"));
        System.out.println("Total radio button groups found: " + radioGroups.size());

        for (int i = 0; i < radioGroups.size(); i++) {
            WebElement group = radioGroups.get(i);
            System.out.println("Processing group " + (i + 1) + ": " + group.getText());
            List<WebElement> radioButtons = group.findElements(By.xpath(".//input[@type='radio']"));
            System.out.println("Total radio buttons found in group " + (i + 1) + ": " + radioButtons.size());

            boolean yesFound = false;
            boolean anySelected = false;

            for (WebElement radioButton : radioButtons) {
                System.out.println("Radio button ID: " + radioButton.getAttribute("id"));
                if (radioButton.isSelected()) {
                    anySelected = true;
                    System.out.println("Radio button already selected in group " + (i + 1) + ".");
                    break;
                }
            }

            if (!anySelected) {
                for (WebElement radioButton : radioButtons) {
                    String radioId = radioButton.getAttribute("id");
                    WebElement label = driver.findElement(By.xpath("//label[@for='" + radioId + "']"));
                    System.out.println("Label text: " + label.getText());
                    if (label.getText().equalsIgnoreCase("Yes")) {
                        label.click();
                        System.out.println("Selected 'Yes' in group " + (i + 1) + ".");
                        yesFound = true;
                        break;
                    }
                }
            }

            if (!yesFound && !anySelected) {
                WebElement firstRadioButton = radioButtons.get(0);
                String firstRadioId = firstRadioButton.getAttribute("id");
                WebElement firstLabel = driver.findElement(By.xpath("//label[@for='" + firstRadioId + "']"));
                firstLabel.click();
                System.out.println("Selected first available option in group " + (i + 1) + ".");
            }
            Thread.sleep(2000);
        }
    }


    private void handleDropdowns(WebDriver driver, WebDriverWait wait) throws InterruptedException {
        List<WebElement> dropdowns = findElements(driver, locators.dropdownButtons);
        if (!isElementListEmpty(findElements(driver, locators.dropdownButtons))) {
            for (WebElement dropdown : dropdowns) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);
                wait.until(ExpectedConditions.visibilityOf(dropdown));
                wait.until(ExpectedConditions.elementToBeClickable(dropdown));

                Select select = new Select(dropdown);
                select.selectByIndex(1);
                System.out.println("Selected option: " + select.getFirstSelectedOption().getText());
            }
        } else {
            System.out.println("No Dropdowns found");
        }
    }

    private void handleCheckboxes(WebDriver driver, WebDriverWait wait) throws InterruptedException {
        List<WebElement> checkboxes = findElements(driver, locators.checkBox);

        if (!checkboxes.isEmpty()) {
            for (int i = 0; i < checkboxes.size(); i++) {
                WebElement checkbox = checkboxes.get(i);
                boolean isSelected = false;
                int attempts = 0;

                while (attempts < 3 && !isSelected) {
                    try {
                        if (!checkbox.isSelected()) {
                            WebElement label = driver.findElement(By.xpath("//label[@for='" + checkbox.getAttribute("id") + "']"));
                            label.click();
                            isSelected = checkbox.isSelected();
                        } else {
                            isSelected = true;
                        }
                    } catch (StaleElementReferenceException e) {
                        System.out.println("StaleElementReferenceException occurred, retrying...");
                        checkboxes = findElements(driver, locators.checkBox);
                        checkbox = checkboxes.get(i);
                    }
                    attempts++;
                }

                if (!isSelected) {
                    System.out.println("Failed to select checkbox after retries.");
                }
            }
        }
    }

    public void textAreaFiled1(WebDriver driver, String[] data) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            List<WebElement> textAreas = findElements(driver, locators.textAreaFiled);
            if (textAreas != null && !textAreas.isEmpty()) {
                for (WebElement textArea : textAreas) {
                    try {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", textArea);
                        wait.until(ExpectedConditions.visibilityOf(textArea));
                        wait.until(ExpectedConditions.elementToBeClickable(textArea));
                        textArea.clear();
                        textArea.sendKeys(data[4]);
                    } catch (NoSuchElementException e) {
                        System.out.println("Text area not found: " + textArea);
                    } catch (Exception e) {
                        System.out.println("Error interacting with text area: " + e.getMessage());
                    }
                }
            } else {
                System.out.println("No text area elements found.");
            }
        } catch (Exception e) {
            System.out.println("Error handling text area fields: " + e.getMessage());
        }
    }

    public boolean nextButton(WebDriver driver) {
        List<WebElement> nextButtons = findElements(driver, locators.nextButton);
        if (!isElementListEmpty(nextButtons)) {
            WebElement nextButton = nextButtons.get(0);
            System.out.println("Button Displayed: " + nextButton.isDisplayed());
            System.out.println("Button Enabled: " + nextButton.isEnabled());
            scrollToElement(driver, locators.nextButton);
            waitForElement(driver, locators.nextButton);
            clickElement(driver, locators.nextButton);
            return true;
        }
        return false;
    }

    public void handleNextSection(WebDriver driver, WebDriverWait wait, String[] data) throws InterruptedException {
        List<WebElement> nextButtons = findElements(driver, locators.nextButton);
        if (!isElementListEmpty(nextButtons)) {
            scrollToElement(driver, locators.nextButton);
            fillTextQuestions(driver);
            EnterCityName(driver);
            handleRadioButtons1(driver);
            handleDropdowns(driver, wait);
            handleCheckboxes(driver, wait);
            textAreaFiled1(driver, data);
            scrollToElement(driver, locators.nextButton);
        }
    }

    public void handleReviewSection(WebDriver driver, WebDriverWait wait, String[] data) throws InterruptedException {
        if (!isElementListEmpty(findElements(driver, locators.reviewButton))) {
            fillTextQuestions(driver);
            EnterCityName(driver);
            handleDropdowns(driver, wait);
            handleRadioButtons1(driver);
            handleCheckboxes(driver, wait);
            textAreaFiled1(driver, data);
            scrollToElement(driver, locators.reviewButton);
            waitForElement(driver, locators.reviewButton);
            clickElement(driver, locators.reviewButton);
            Thread.sleep(5000);
        }
    }

}

