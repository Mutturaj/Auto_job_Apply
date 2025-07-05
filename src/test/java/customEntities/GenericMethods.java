package customEntities;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class GenericMethods {
    public WebDriver driver;

    public WebDriver launchBrowser(String baseURL) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        this.driver = new ChromeDriver(options);
        driver.get(baseURL);
        System.setProperty("webdriver.chrome.logfile", "path/to/chromedriver.log");
        System.setProperty("webdriver.chrome.verboseLogging", "true");

        if (this.driver == null) {
            System.out.println("ERROR: WebDriver is NULL after launchBrowser()!");
        } else {
            System.out.println("SUCCESS: WebDriver initialized correctly.");
        }
        return driver;
    }

    public static WebElement findElement(WebDriver driver, LocatorsDetails locatorsDetails) {
        try {
            By locator = getByFromDetails(locatorsDetails);
            return driver.findElement(locator);
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<WebElement> findElements(WebDriver driver, LocatorsDetails locatorsDetails) {
        try {
            By locator = getByFromDetails(locatorsDetails);
            return driver.findElements(locator);
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static boolean isElementListEmpty(List<WebElement> elements) {
        return elements == null || elements.isEmpty();
    }

    public void waitForElementToBeClickable(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementToBeClickable(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForElementToBeClickable(WebDriver driver, LocatorsDetails locatorsDetails) {
        By locator = getByFromDetails(locatorsDetails);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NullPointerException | NoSuchElementException | StaleElementReferenceException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isElementEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (NullPointerException | NoSuchElementException | StaleElementReferenceException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sendKeysToElement(WebDriver driver, LocatorsDetails locatorsDetails, String text) {
        WebElement element = waitForElement(driver, locatorsDetails);
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    public static WebElement waitForElement(WebDriver driver, LocatorsDetails locatorsDetails) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver instance must not be null");
        }

        if (locatorsDetails == null) {
            throw new IllegalArgumentException("Locator details must not be null");
        }

        By locator = getByFromDetails(locatorsDetails);
        if (locator == null) {
            throw new IllegalArgumentException("Locator must not be null");
        }

        System.out.println("Waiting for element: " + locatorsDetails.getValue());

        try {
            FluentWait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(50))
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(NoSuchElementException.class);

            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean isElementPresent(WebDriver driver, LocatorsDetails locatorsDetails) {
        try {
            By locator = getByFromDetails(locatorsDetails);
            return !driver.findElements(locator).isEmpty();
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    public void clickElement(WebDriver driver, LocatorsDetails locatorsDetails) {
        WebElement element = waitForElement(driver, locatorsDetails);
        element.click();

    }

    public static By getByFromDetails(LocatorsDetails locatorsDetails) {
        switch (locatorsDetails.getType().toLowerCase()) {
            case "id":
                return By.id(locatorsDetails.getValue());
            case "name":
                return By.name(locatorsDetails.getValue());
            case "xpath":
                return By.xpath(locatorsDetails.getValue());
            case "classname":
                return By.className(locatorsDetails.getValue());
            case "cssSelector":
                return By.cssSelector(locatorsDetails.getValue());
            // Add more cases for other locator types as needed
            default:
                throw new IllegalArgumentException("Unsupported locator type: " + locatorsDetails.getType());
        }
    }

    public static void switchToTabByIndex(WebDriver driver, int tabIndex) {
        Set<String> handles = driver.getWindowHandles();
        if (tabIndex >= 0 && tabIndex < handles.size()) {
            String[] tabs = handles.toArray(new String[0]);
            driver.switchTo().window(tabs[tabIndex]);
            waitForPageLoad(driver);
        } else {
            throw new IllegalArgumentException("Invalid tab index: " + tabIndex);
        }
    }

    public static void waitForPageLoad(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
        wait.until(webDriver -> {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
            return jsExecutor.executeScript("return document.readyState").equals("complete");
        });
    }

    public void switchToIframe(WebDriver driver, LocatorsDetails iframeLocatorsDetails, long waitTimeSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeSeconds));
        WebElement iframeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(getByFromDetails(iframeLocatorsDetails)));
        driver.switchTo().frame(iframeElement);
    }

    public void switchToChildWindow(WebDriver driver, String expectedWindowTitle) {
        Set<String> windowHandles = driver.getWindowHandles();
        for (String win : windowHandles) {
            driver.switchTo().window(win);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            wait.until(ExpectedConditions.titleIs(expectedWindowTitle));
            if (driver.getTitle().equals(expectedWindowTitle)) {
                break;
            }
        }
    }

    public void selectByIndex(WebDriver driver, LocatorsDetails locatorsDetails, int index) {
        WebElement element = waitForElement(driver, locatorsDetails);
        if (element != null) {
            Select select = new Select(element);
            select.selectByIndex(index);
        }
    }

    public void selectByValue(WebDriver driver, LocatorsDetails locatorsDetails, String value) {
        WebElement element = waitForElement(driver, locatorsDetails);
        if (element != null) {
            Select select = new Select(element);
            select.selectByValue(value);
        }
    }

    public void scrollElementBy(WebDriver driver, LocatorsDetails locatorsDetails, int xOffset, int yOffset) {
        WebElement element = waitForElement(driver, locatorsDetails);
        if (element != null) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].scrollTop += arguments[1]; arguments[0].scrollLeft += arguments[2];", element, yOffset, xOffset);
        }
    }

    public void scrollElementBy(WebDriver driver, LocatorsDetails locatorsDetails, int yOffset) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollTop += arguments[1];", locatorsDetails, yOffset);
    }

    public void scrollToElement(WebDriver driver, LocatorsDetails locatorsDetails) {
        WebElement element = waitForElement(driver, locatorsDetails);
        if (element != null) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
            System.out.println("Scrolled to element: " + element);
        } else {
            System.out.println("Element not found: " + locatorsDetails);
        }
    }

    public void scrollToTop(WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollTo(0, 0);");
        System.out.println("Scrolled to the top of the page.");
    }

    public static void executeJavaScript(WebDriver driver, String script, LocatorsDetails locatorsDetails) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        if (locatorsDetails != null) {
            WebElement element = waitForElement(driver, locatorsDetails);
            if (element != null) {
                jsExecutor.executeScript(script, element);
            } else {
                throw new NoSuchElementException("Element not found to execute JavaScript");
            }
        } else {
            throw new IllegalArgumentException("LocatorsDetails must not be null");
        }
    }

    public static void executeJavaScript(WebDriver driver, String script, WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        if (element != null) {
            jsExecutor.executeScript(script, element);
        } else {
            throw new NoSuchElementException("WebElement must not be null");
        }
    }


    public static class LocatorsDetails {
        private final String type;
        private final String value;
        private final String name;

        public LocatorsDetails(String type, String value, String name) {
            this.type = type;
            this.value = value;
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }
}
