package Pages;

import AppConfg.DataConfig;
import Locators.Linkedin_Locators;
import customEntities.GenericMethods;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;


public class LinkedinPage extends GenericMethods {
    Linkedin_Locators locators = new Linkedin_Locators();
    String datasetName = DataConfig.getInstance().getDatasetName();
    QuestionAnswerHandler questionAnswerHandler = new QuestionAnswerHandler(datasetName);

    public LinkedinPage(WebDriver driver) throws FileNotFoundException {
        PageFactory.initElements(driver, this);
        questionAnswerHandler.initializeQuestionAnswerMap(datasetName);
    }

    public void loginToLinkedIn(WebDriver driver, String[] data) throws InterruptedException {
        waitForPageLoad(driver);
        sendKeysToElement(driver, locators.EmailID, data[0]);
        sendKeysToElement(driver, locators.Password, data[1]);
        if (!findElements(driver, locators.SignInButton).isEmpty()) {
            clickElement(driver, locators.SignInButton);
        } else {
            clickElement(driver, locators.AgreeAndReJoinButton);
        }
    }


    public void navigateToJobs(WebDriver driver) throws InterruptedException {
        waitForPageLoad(driver);
        executeJavaScript(driver, "arguments[0].removeAttribute('aria-hidden');", locators.JobIcon);
        Thread.sleep(1000);
        clickElement(driver, locators.JobIcon);
//        waitForElement(driver, locators.ShowAllButton);
//        clickElement(driver, locators.ShowAllButton);
    }

    public void navigateToNotification(WebDriver driver) throws InterruptedException {
        waitForPageLoad(driver);
        executeJavaScript(driver, "arguments[0].removeAttribute('disabled');", locators.NotificationIcon);
        executeJavaScript(driver, "arguments[0].removeAttribute('aria-hidden');", locators.NotificationIcon);
        clickElement(driver, locators.NotificationIcon);
        waitForPageLoad(driver);
        Thread.sleep(1000);
        executeJavaScript(driver, "arguments[0].removeAttribute('disabled');", locators.viewJobsButton);
        executeJavaScript(driver, "arguments[0].removeAttribute('aria-hidden');", locators.viewJobsButton);
        List<WebElement> viewJobs = findElements(driver, locators.viewJobsButton);
        for (int i = 0; i < viewJobs.size(); i++) {
            if (i == 0) {
                viewJobs.get(i).click();
                waitForPageLoad(driver);
                Thread.sleep(3000);
                break;
            }
        }
    }

    public void searchForJobs(WebDriver driver, String[] data) throws InterruptedException {
        waitForPageLoad(driver);
        Actions actions = new Actions(driver);
        executeJavaScript(driver, "arguments[0].removeAttribute('disabled');", locators.TitleOfJob);
        executeJavaScript(driver, "arguments[0].removeAttribute('aria-hidden');", locators.TitleOfJob);
        sendKeysToElement(driver, locators.TitleOfJob, data[2]);
        Thread.sleep(2000);
        WebElement titleElement = findElement(driver, locators.TitleOfJob);
        actions.sendKeys(titleElement, Keys.ENTER).perform();
        waitForPageLoad(driver);
        Thread.sleep(1000);
        executeJavaScript(driver, "arguments[0].removeAttribute('disabled');", locators.LocationOfJob);
        executeJavaScript(driver, "arguments[0].removeAttribute('aria-hidden');", locators.LocationOfJob);
        sendKeysToElement(driver, locators.LocationOfJob, data[4]);
        waitForPageLoad(driver);
        WebElement locationElement = findElement(driver, locators.LocationOfJob);
        Thread.sleep(1000);
        actions.sendKeys(locationElement, Keys.ENTER).perform();
        waitForPageLoad(driver);
        // executeJavaScript(driver, "arguments[0].removeAttribute('aria-checked');", locators.EasyApplyFilter);
        Thread.sleep(1000);
        clickElement(driver, locators.EasyApplyFilter);
        //ApplyFilters(driver);
        // DatePosted(driver);
    }

    public void ApplyFilters(WebDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        clickElement(driver, locators.EasyApplyFilter);
        waitForPageLoad(driver);
        clickElement(driver, locators.AllFiltersButton);
        Thread.sleep(2000);
        scrollToElement(driver, locators.JobTitleFilter);
        List<WebElement> AllJobTitles = findElements(driver, locators.JobTitleCheckbox);
        for (WebElement checkbox : findElements(driver, locators.JobTitleCheckbox)) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
        }
        Thread.sleep(2000);
        clickElement(driver, locators.ShowAllResults);
        System.out.println("Applied advanced filters");
        Thread.sleep(4000);
    }

    public void DatePosted(WebDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        clickElement(driver, locators.EasyApplyFilter);
        waitForPageLoad(driver);
        Thread.sleep(1000);
        executeJavaScript(driver, "arguments[0].removeAttribute('aria-hidden');", locators.DatePostedButton);
        Thread.sleep(1000);
        clickElement(driver, locators.DatePostedButton);
        waitForPageLoad(driver);
        Thread.sleep(1000);
        clickElement(driver, locators.PastWeekButton);
        waitForPageLoad(driver);
        Thread.sleep(2000);
        List<WebElement> buttons = driver.findElements(By.xpath(
                "//button[contains(@class, 'artdeco-button') and contains(@class, 'artdeco-button--primary') and .//span[starts-with(normalize-space(.), 'Show ') and contains(normalize-space(.), 'results')]]"
        ));
        for (WebElement btn : buttons) {
            String text = btn.getText().trim();
            if (text.matches("Show \\d+ results")) {
                btn.click();
                break;
            }
        }

        Thread.sleep(4000);


    }

    private boolean navigatePagination(WebDriver driver, int currentPage) {
        try {
            try {
                WebElement nextPage1Button = driver.findElement(By.xpath("//button[@aria-label='View next page']"));
                if (nextPage1Button != null) {
                    System.out.println("Navigating to the next page using 'Next Page' button.");
                    executeJavaScript(driver, "arguments[0].scrollIntoView(true);", nextPage1Button);
                    waitForElementToBeClickable(driver, nextPage1Button);

                    try {
                        nextPage1Button.click();
                    } catch (ElementNotInteractableException e) {
                        System.out.println("ElementNotInteractableException occurred, attempting JavaScript click on 'Next Page' button.");
                        executeJavaScript(driver, "arguments[0].click();", nextPage1Button);
                    }
                    Thread.sleep(4000);
                    return true;
                }
            } catch (NoSuchElementException e) {
                System.out.println("'Next Page' button not found, checking for page number button.");
            }
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
            if (isElementPresent(driver, locators.closeIcon)) {
                waitForElement(driver, locators.closeIcon);
                executeJavaScript(driver, "arguments[0].removeAttribute('aria-hidden');", locators.closeIcon);
                clickElement(driver, locators.closeIcon);
                System.out.println("Clicked Close icon, exiting further actions.");
                return;
            }

            if (isElementPresent(driver, locators.DoneButton)) {
                waitForElement(driver, locators.DoneButton);
                clickElement(driver, locators.DoneButton);
                System.out.println("Clicked Done button");
            } else {
                waitForElement(driver, locators.DoneButton);
                System.out.println("Done button not found. Trying to find and click it once again.");
                clickElement(driver, locators.DoneButton);
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
        List<WebElement> answerFields = findElements(driver, locators.AnswerTextfield);
        List<WebElement> answerFields2 = findElements(driver, locators.textAreaFiled);
        List<WebElement> allAnswerFields = new ArrayList<>();
        allAnswerFields.addAll(answerFields);
        allAnswerFields.addAll(answerFields2);
        int answerCount = allAnswerFields.size();

        List<WebElement> questions1 = findElements(driver, locators.Questions1);
        List<WebElement> questions2 = findElements(driver, locators.Question2);
        List<WebElement> allQuestions = new ArrayList<>();
        allQuestions.addAll(questions1);
        allQuestions.addAll(questions2);
        if (allQuestions.size() > answerCount) {
            allQuestions = allQuestions.subList(0, answerCount);
        }
        for (int i = 0; i < allQuestions.size(); i++) {
            WebElement questionElement = allQuestions.get(i);
            WebElement answerField = allAnswerFields.get(i);
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
        JobName(driver);
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
            System.out.println("Processing group " + (i + 1));

            List<WebElement> radioButtons = group.findElements(By.xpath(".//input[@type='radio']"));
            System.out.println("Total radio buttons in group " + (i + 1) + ": " + radioButtons.size());

            if (!radioButtons.isEmpty()) {
                WebElement firstRadioButton = radioButtons.get(0);

                if (!firstRadioButton.isSelected()) {
                    String firstRadioId = firstRadioButton.getAttribute("id");
                    WebElement firstLabel = driver.findElement(By.xpath("//label[@for='" + firstRadioId + "']"));
                    firstLabel.click();
                    System.out.println("Selected first available option in group " + (i + 1) + ".");
                } else {
                    System.out.println("First option in group " + (i + 1) + " is already selected. Skipping.");
                }
            } else {
                System.out.println("No radio buttons found in group " + (i + 1) + ".");
            }

            Thread.sleep(2000);
        }
    }

    private void handleDropdowns(WebDriver driver, WebDriverWait wait) throws InterruptedException {
        List<WebElement> dropdowns = findElements(driver, locators.dropdownButtons);
        if (!dropdowns.isEmpty()) {
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
            JoiningDate(driver);
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
            JoiningDate(driver);
            textAreaFiled1(driver, data);
            scrollToElement(driver, locators.reviewButton);
            waitForElement(driver, locators.reviewButton);
            clickElement(driver, locators.reviewButton);
            Thread.sleep(5000);
        }
    }

    public void ZoomOut(WebDriver driver) throws AWTException, InterruptedException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_SUBTRACT);
        robot.keyRelease(KeyEvent.VK_SUBTRACT);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(1000);
    }

    public void SignedOut(WebDriver driver, WebDriverWait wait) throws InterruptedException {
        driver.manage().deleteAllCookies();
        driver.get("https://www.linkedin.com/m/logout/");
        waitForPageLoad(driver);
        driver.navigate().refresh();
    }

    private int appliedJobsCount = 0;

    private void JobName(WebDriver driver) {
        waitForPageLoad(driver);
        String jobTitle = findElement(driver, locators.JobTitle).getText();
        String cleanJobTitle = jobTitle.replace("Apply to ", "");
        appliedJobsCount++;
        System.out.println("Applied to this Job from LinkedIn: " + cleanJobTitle);
        System.out.println("Total Jobs Applied on LinkedIn: " + appliedJobsCount);
    }

    public void applyForJobs(WebDriver driver, WebDriverWait wait, JavascriptExecutor js, String[] data) throws InterruptedException {
        int currentPage = 2;
        int maxScrollAttempts = 10;

        while (true) {
            List<WebElement> jobs = findElements(driver, locators.listOfJobs);
            if (!isElementListEmpty(jobs)) {
                processJobList(driver, wait, js, jobs, data);
            } else {
                boolean jobsFound = scrollForJobs(driver, js, maxScrollAttempts);
                if (!jobsFound) {
                    if (navigateToNextPage(driver, currentPage)) {
                        currentPage++;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    private void processJobList(WebDriver driver, WebDriverWait wait, JavascriptExecutor js, List<WebElement> jobs, String[] data) throws InterruptedException {
        for (int i = 0; i < jobs.size(); i++) {
            WebElement easyApply = jobs.get(i);
            try {
                scrollToElement(driver, easyApply);
                waitUntilClickable(wait, easyApply);
                easyApply.click();
                Thread.sleep(2000);
                if (!findElements(driver, locators.reachedMaxLimit).isEmpty()) {
                    System.out.println("Reached Max Limit so stopped the JOB Application Process");
                    throw new RuntimeException("Easy Apply limit reached");
                }

                if (isEasyApplyOrContinuePresent(driver)) {
                    handleEasyApply(driver, wait, data);
                } else {
                    markJobAsApplied(driver, easyApply);
                    i--; // Recheck the updated list
                }
            } catch (ElementClickInterceptedException e) {
                System.out.println("ElementClickInterceptedException caught, moving to the next job.");
            }
        }
        Thread.sleep(4000);
    }

    private boolean scrollForJobs(WebDriver driver, JavascriptExecutor js, int maxScrollAttempts) {
        int scrollAttempts = 0;
        boolean jobsFound = false;
        boolean pagesFound = false;

        while (!jobsFound && !pagesFound && scrollAttempts < maxScrollAttempts) {
            js.executeScript("document.querySelector('.scaffold-layout__list').scrollTop += 450;");
            waitForPageLoad(driver);

            jobsFound = !findElements(driver, locators.listOfJobs).isEmpty();
            pagesFound = !findElements(driver, locators.listOfPages).isEmpty();
            scrollAttempts++;
        }

        return jobsFound;
    }

    private boolean navigateToNextPage(WebDriver driver, int currentPage) {
        System.out.println("Reached maximum scroll attempts, navigating to next page...");
        if (navigatePagination(driver, currentPage)) {
            waitForPageLoad(driver);
            return true;
        }
        return false;
    }

    private void scrollToElement(WebDriver driver, WebElement element) {
        executeJavaScript(driver, "arguments[0].scrollIntoView(true);", element);
    }

    private void waitUntilClickable(WebDriverWait wait, WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private boolean isEasyApplyOrContinuePresent(WebDriver driver) {
        return isElementPresent(driver, locators.easyApplyButton) || isElementPresent(driver, locators.continueButton);
    }

    private void handleEasyApply(WebDriver driver, WebDriverWait wait, String[] data) throws InterruptedException {
        executeJavaScript(driver, "arguments[0].removeAttribute('aria-hidden');", locators.easyApplyButton);
        Thread.sleep(1000);
        ClickEasyApplyButtonORContinueButton(driver);
        handleJobApplicationProcess(driver, wait, data);
    }

    private void markJobAsApplied(WebDriver driver, WebElement easyApply) throws InterruptedException {
        System.out.println("Easy Apply or Continue button not present on L2 page, marking this job as Applied and skipping.");
        driver.navigate().back();
        Thread.sleep(2000);
        executeJavaScript(driver, "arguments[0].textContent = 'Applied';", easyApply);
        Thread.sleep(2000);
    }

    public void JoiningDate(WebDriver driver) throws InterruptedException {
        waitForPageLoad(driver);
        if (!findElements(driver, locators.DateTextField).isEmpty()) {
            clickElement(driver, locators.DateTextField);
            Thread.sleep(2000);
            DynamicDateSelector(driver);
        }
    }

    public void DynamicDateSelector(WebDriver driver) {
        LocalDate today = LocalDate.now();
        LocalDate targetDate = today.plusDays(15);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
        String formattedDate = targetDate.format(formatter);
        String dynamicXPath = "//button[@aria-label='" + formattedDate + ".']";
        System.out.println("Dynamic XPath: " + dynamicXPath);

        try {
            WebElement dateButton = driver.findElement(By.xpath(dynamicXPath));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateButton);
            dateButton.click();
            System.out.println("Selected date: " + formattedDate);
        } catch (NoSuchElementException e) {
            System.out.println("Date button not found for: " + formattedDate);
        }
    }

}


