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
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (driver != null) {
            System.out.println("Driver is set, taking screenshot for failure in " + result.getMethod().getMethodName());
            takeScreenshot(result.getMethod().getMethodName());
        } else {
            System.out.println("WebDriver is null. Cannot take screenshot.");
        }
    }

    private void takeScreenshot(String methodName) {
        File screenshotDir = new File(System.getProperty("user.dir") + "/screenshots/");
        if (!screenshotDir.exists() && !screenshotDir.mkdir()) {
            System.out.println("Failed to create directory: " + screenshotDir.getAbsolutePath());
            return;
        }
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String filePath = screenshotDir.getAbsolutePath() + "/" + methodName + "_" + timestamp + ".png";
            FileUtils.copyFile(srcFile, new File(filePath));
            System.out.println("Screenshot saved at: " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to save screenshot.");
            e.printStackTrace();
        }
    }
}

