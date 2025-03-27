package customEntities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotListener implements ITestListener {
    private WebDriver driver;

    @Override
    public void onStart(ITestContext context) {
        this.driver = (WebDriver) context.getAttribute("WebDriver");

        if (this.driver == null) {
            System.out.println("WebDriver is NULL in ScreenshotListener's onStart()! Check if setUp() stored it correctly.");
        } else {
            System.out.println("WebDriver is successfully retrieved in ScreenshotListener.");
        }
    }


    @Override
    public void onTestFailure(ITestResult result) {
        if (this.driver == null) {
            System.out.println("WebDriver is NULL inside onTestFailure! Attempting to retrieve again from context...");
            this.driver = (WebDriver) result.getTestContext().getAttribute("WebDriver");
        }

        if (this.driver != null) {
            System.out.println("Driver retrieved, taking screenshot for failure in " + result.getMethod().getMethodName());
            takeScreenshot(result.getMethod().getMethodName());
        } else {
            System.out.println("WebDriver is still NULL after re-attempt. Cannot take screenshot.");
        }
    }


    private void takeScreenshot(String methodName) {
        File screenshotDir = new File(System.getProperty("user.dir") + "/screenshots/");
        if (!screenshotDir.exists() && !screenshotDir.mkdir()) {
            System.out.println("Failed to create screenshot directory: " + screenshotDir.getAbsolutePath());
            return;
        }

        try {
            File srcFile = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String filePath = screenshotDir.getAbsolutePath() + "/" + methodName + "_" + timestamp + ".jpg";
            FileUtils.copyFile(srcFile, new File(filePath));
            System.out.println("Screenshot saved at: " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to save screenshot.");
            e.printStackTrace();
        }
    }
}
