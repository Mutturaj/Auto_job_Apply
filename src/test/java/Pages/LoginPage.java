package Pages;

import Locators.Login_Locators;
import customEntities.allApi;
import customEntities.GenericMethods;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.Set;

public class LoginPage extends GenericMethods {
    Login_Locators locators=new Login_Locators();
    allApi getApi = new allApi();

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void ClickOnMyAccount(WebDriver driver, String[] data) throws InterruptedException {
        // getApi.resetFlow();
        clickElement(driver, locators.ContinueWithPhonePe);
        sendKeysToElement(driver, locators.mobileNum, data[0]);
        clickElement(driver,locators.proceedButton);
        sendKeysToElement(driver, locators.OtpBox, String.valueOf(getApi.getOtp()));
        clickElement(driver, locators.verifyButton);
        Thread.sleep(2000);
        if (!isElementListEmpty(findElements(driver, locators.sorryWrongOTP))) {
            clickElement(driver, locators.getNewOtpButton);
            sendKeysToElement(driver, locators.OtpBox, String.valueOf(getApi.getOtp()));
            clickElement(driver, locators.verifyButton);
            sendKeysToElement(driver, locators.PinBox, data[1]);
            clickElement(driver, locators.ProceedButton);
        } else {
            sendKeysToElement(driver, locators.PinBox, data[1]);
            clickElement(driver, locators.ProceedButton);
        }
    }

    public void ClickOpenAccount(WebDriver driver, String[] data) throws InterruptedException {
        Thread.sleep(3000);
        if (!isElementListEmpty(findElements(driver, locators.viewKYCButton))) {
            clickElement(driver, locators.viewKYCButton);
            clickElement(driver, locators.fixNowButton);
        } else {
            clickElement(driver, locators.KYCResumeButton);
        }
        switchToTabByIndex(driver, 1);
        driver.navigate().refresh();
        switchToIframe(driver, locators.Iframe, 30);
        Thread.sleep(3000);
        if (!isElementListEmpty(findElements(driver, locators.allowPhonepeToSharePanButton))) {
            clickElement(driver, locators.allowPhonepeToSharePanButton);
            clickElement(driver, locators.Checkbox);
            Thread.sleep(3000);
            clickElement(driver, locators.PanProceedButton);
            clickElement(driver, locators.ProceedAs);
        } else {
            sendKeysToElement(driver, locators.panTextField, data[3].replaceAll("\"", ""));
            clickElement(driver, locators.Checkbox);
            clickElement(driver, locators.PanProceedButton);
            clickElement(driver, locators.ProceedAs);
        }
    }

    public void DigiLockerStep(WebDriver driver, String[] data) throws InterruptedException {
        Thread.sleep(2000);
        clickElement(driver, locators.digiLockerButton);
        Thread.sleep(4000);
        Set<String> windowHandles = driver.getWindowHandles();
        System.out.println("Number of windows: " + windowHandles.size());
        for (String windowHandle : windowHandles) {
            driver.switchTo().window(windowHandle);
            System.out.println("Window Handle: " + windowHandle + ", Title: " + driver.getTitle());
        }
        if (windowHandles.size() > 2) {
            int inc = 0;
            for (String win : windowHandles) {
                System.out.println("Title before switch: " + driver.getTitle());
                driver.switchTo().window(win);
                if (inc == 2) {
                    driver.switchTo().window(win);
                    driver.manage().window().maximize();
                    System.out.println("Switched to the window with title: " + driver.getTitle());
                    waitForElement(driver, locators.adharNumInput);
                    sendKeysToElement(driver, locators.adharNumInput, data[2]);
                    try {
                        System.load("/usr/local/Cellar/tesseract/5.3.3/lib/libtesseract.dylib");
                        Thread.sleep(2000);
                        sendKeysToElement(driver, locators.inputCaptcha, "1WFR4T");
                        WebElement captchaImage = driver.findElement(By.xpath("//img[@id='captcha_img']"));
                        File screenshotFile = captchaImage.getScreenshotAs(OutputType.FILE);
                        int dynamicNumber = 14;
                        String fileName = String.format("captcha%d.png", dynamicNumber);
                        String filePath = String.format("/Users/muttu.quali.con/Documents/Web_Framework/Captcaha/%s", fileName);
                        FileHandler.copy(screenshotFile, new File(filePath));
                        Thread.sleep(2000);
                        ITesseract image = new Tesseract();
                        String projectDirectory = System.getProperty("user.dir");
                        image.setDatapath(projectDirectory + "/tessdata");
                        String captchaText = image.doOCR(screenshotFile);
                        System.out.println("Captcha OCR result: " + captchaText);

                    } catch (Exception e) {
                        System.out.println("Error capturing or processing captcha: " + e.getMessage());
                    }
                    clickElement(driver, locators.NextButton);
                    break;
                }
                inc++;
            }
            System.out.println("Title after switch: " + driver.getTitle());
        } else {
            System.out.println("There is no second window");
    }
}
}