package Locators;

import customEntities.GenericMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class Login_Locators extends GenericMethods {

    public LocatorsDetails ContinueWithPhonePe = new LocatorsDetails("xpath", "//span[.='Continue with PhonePe']", "Mobile num input");
    public LocatorsDetails mobileNum = new LocatorsDetails("Xpath", "//input[@id='mobileNumber']", "Mobile num Input");
    public LocatorsDetails proceedButton=new LocatorsDetails("Xpath","//span[.='PROCEED']","Proceed button");
    public LocatorsDetails verifyButton = new LocatorsDetails("xpath", "//span[.='VERIFY']", "Verify button");
    public LocatorsDetails OtpBox = new LocatorsDetails("Xpath", "//input[@role='textbox' and @type='text']", "All otp box");
    public LocatorsDetails sorryWrongOTP = new LocatorsDetails("Xpath", "//h1[.='Sorry, Wrong OTP']", "Wrong otp screen");
    public LocatorsDetails getNewOtpButton = new LocatorsDetails("Xpath", "//span[.='GET NEW OTP']", "Get new Otp button");
    public LocatorsDetails PinBox = new LocatorsDetails("Xpath", "//input[@class=\"f9JHr esohj\" and @type='text']", "Pin box");
    public LocatorsDetails ProceedButton = new LocatorsDetails("Xpath", "//span[.='PROCEED']", "Verify button");
    public LocatorsDetails KYCResumeButton = new LocatorsDetails("Xpath", "//button[@type='button']//span[.='RESUME']", "KYC Resume button");
    public LocatorsDetails viewKYCButton = new LocatorsDetails("Xpath", "//span[.='VIEW DETAILS']", "Veiw Details");
    public LocatorsDetails fixNowButton = new LocatorsDetails("Xpath", "//span[.='FIX NOW']", "Fix now button");
    public LocatorsDetails panTextField = new LocatorsDetails("Xpath", "//input[@id='ENTER_PAN_FIELD_ID']", "Enter Pan Text");
    public LocatorsDetails allowPhonepeToSharePanButton = new LocatorsDetails("Xpath", "//button[.='Allow Phonepe to share PAN']", "To share PAN button");
    public LocatorsDetails Checkbox = new LocatorsDetails("Xpath", "//input[@type='checkbox']/following-sibling::span[@class='-YT65BhqC1XkUxkOB4WIKg==']", "Check box");
    public LocatorsDetails PanProceedButton = new LocatorsDetails("xpath", "//button[.='PROCEED']", "Pan Proceed button");
    public LocatorsDetails Iframe = new LocatorsDetails("Xpath", "//iframe[@class='PsZD3']", "iframe");
    public LocatorsDetails ProceedAs = new LocatorsDetails("Xpath", "//button[@class=\"MYWSkJt0KgzbH5G2gi1CqA== b7a4y9hznRh1FfO2Q8K2aQ== _2HMJ3mvv74IpM2wiJI+Dcw==\"]/span[contains(text(), 'Proceed')]", "Proceed as button");
    public LocatorsDetails digiLockerButton = new LocatorsDetails("Xpath", "//button[.='PROCEED TO DIGILOCKER']", "Digilocker button");
    public LocatorsDetails adharNumInput = new LocatorsDetails("Xpath", "//input[@id=\"aadhaar_1\"]", "Adhar num input");
    public LocatorsDetails inputCaptcha = new LocatorsDetails("Xpath", "//input[@id=\"enter_captcha\"]", "Input Captcha");
    public LocatorsDetails NextButton = new LocatorsDetails("Xpath", "//button[.=' Next ']", "Next button");


    @FindAll({
            @FindBy(xpath = "//div[@class='list-group']")
    })
    public List<WebElement> listofdestops;

}