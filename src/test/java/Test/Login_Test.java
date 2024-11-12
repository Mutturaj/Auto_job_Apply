package Test;

import Pages.LoginPage;
import customEntities.GenericMethods;
import customEntities.RetryAnalyzer;
import customEntities.dataRead;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Login_Test {
    public static WebDriver driver;
    GenericMethods generic = new GenericMethods();
    RetryAnalyzer retryAnalyzer=new RetryAnalyzer();
    public LoginPage loginData;

    @BeforeClass
    @Parameters("baseURL")
    public void setUp(String baseURL) {
        generic.launchBrowser(baseURL);
        loginData = new LoginPage(driver);
    }

    @Test(priority = 1, dataProvider = "login_cred", dataProviderClass = dataRead.class)
    public void verifyLoginWithValidCredentials(String[] data) throws InterruptedException {
        loginData.ClickOnMyAccount(driver, data);
        loginData.ClickOpenAccount(driver, data);
        loginData.DigiLockerStep(driver, data);
}

}