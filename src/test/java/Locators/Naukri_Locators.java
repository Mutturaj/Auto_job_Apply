package Locators;

import customEntities.GenericMethods;

public class Naukri_Locators {
    public GenericMethods.LocatorsDetails EmailID = new GenericMethods.LocatorsDetails("xpath", "//input[@id='usernameField']", "Enter Email");
    public GenericMethods.LocatorsDetails Password = new GenericMethods.LocatorsDetails("xpath", "//input[@id='passwordField']", "Password");
    public GenericMethods.LocatorsDetails LoginButton = new GenericMethods.LocatorsDetails("xpath", "//button[.='Login']", "Login Button");
    public GenericMethods.LocatorsDetails ViewProfile = new GenericMethods.LocatorsDetails("xpath", "//a[text()='View']", "View Profile button");
    public GenericMethods.LocatorsDetails EditIcon = new GenericMethods.LocatorsDetails("xpath", "//div[@class='widgetHead']//span[@class='edit icon' and .='editOneTheme']", "Edit Icon");
    public GenericMethods.LocatorsDetails SaveButton = new GenericMethods.LocatorsDetails("xpath", "//button[.='Save']", "Save button");
    public GenericMethods.LocatorsDetails JobTitle=new GenericMethods.LocatorsDetails("xpath","//h1[@class=\"styles_jd-header-title__rZwM1\"]","Job Name");
    public GenericMethods.LocatorsDetails SuccessMessage = new GenericMethods.LocatorsDetails("xpath", "//p[.='Success']", "Success Message");
    public GenericMethods.LocatorsDetails JobButton = new GenericMethods.LocatorsDetails("xpath", "//div[.='Jobs']", "Job Button");
    public GenericMethods.LocatorsDetails listOfJobs = new GenericMethods.LocatorsDetails("xpath", "//span[@class='ellipsis dspIB valignM    ' and contains(text(),'Yrs')]", "List of Jobs");
    public GenericMethods.LocatorsDetails listOfJobsFromSearch = new GenericMethods.LocatorsDetails("xpath", "//span[@class='ni-job-tuple-icon ni-job-tuple-icon-srp-experience exp']//span", "Job List from Search");
    public GenericMethods.LocatorsDetails ApplyButton = new GenericMethods.LocatorsDetails("xpath", "//div[@class='styles_jhc__apply-button-container__5Bqnb']/button[.='Apply']", "Apply Button");
    public GenericMethods.LocatorsDetails ApplyCompanySite = new GenericMethods.LocatorsDetails("xpath", "//div[@class='styles_jhc__apply-button-container__5Bqnb']/button[.='Apply on company site']", "Apply On Company Site Button");
    public GenericMethods.LocatorsDetails Designation = new GenericMethods.LocatorsDetails("xpath", "//input[@placeholder=\"Enter keyword / designation / companies\"]", "Job Search");
    public GenericMethods.LocatorsDetails ExperienceButton = new GenericMethods.LocatorsDetails("xpath", "//input[@id='experienceDD']", "Experience ");
    public GenericMethods.LocatorsDetails Location = new GenericMethods.LocatorsDetails("xpath", "//input[@placeholder=\"Enter location\"]", "Location Text Filed");
    public GenericMethods.LocatorsDetails SearchIcon2 = new GenericMethods.LocatorsDetails("xpath", "//span[.='Search']", "Search Icon");
    public GenericMethods.LocatorsDetails SearchIcon1 = new GenericMethods.LocatorsDetails("xpath", "//button[@class='nI-gNb-sb__icon-wrapper']//span[@class=\"ni-gnb-icn ni-gnb-icn-search\"]", "Search Icon");
    public GenericMethods.LocatorsDetails pageNum=new GenericMethods.LocatorsDetails("xpath","//div[@class='styles_pages__v1rAK']/a","Page nums");
    public GenericMethods.LocatorsDetails closeIcon = new GenericMethods.LocatorsDetails("xpath", "//div[@class='crossIcon chatBot chatBot-ic-cross']", "Cross Icon");
    public GenericMethods.LocatorsDetails chatBotPage = new GenericMethods.LocatorsDetails("xpath", "//div[@class='chatbot_Drawer chatbot_right']", "Chat bot page");
    public GenericMethods.LocatorsDetails answerTextFiled = new GenericMethods.LocatorsDetails("xpath", "//div[@class='textArea' and @data-placeholder='Type message here...']", "Input text filed");
    public GenericMethods.LocatorsDetails skipThisQues = new GenericMethods.LocatorsDetails("xpath", "//span[text()='Skip this question']", "Skip this button");
    public GenericMethods.LocatorsDetails chatBotQuestion = new GenericMethods.LocatorsDetails("xpath", "//div[@class='botMsg msg ']//span", "Chat Bot Question");
    public GenericMethods.LocatorsDetails RadioButtons = new GenericMethods.LocatorsDetails("xpath", "//div[@class='ssrc__radio-btn-container']/label", "Radio buttons");
    public GenericMethods.LocatorsDetails saveButtonContainer = new GenericMethods.LocatorsDetails("xpath", "//div[starts-with(@id, 'sendMsg__') and contains(@class, 'send') and not(contains(@class, 'disabled'))]", "Save Button");
    public GenericMethods.LocatorsDetails checkbox = new GenericMethods.LocatorsDetails("xpath", "//div[@class='multicheckboxes-container']//label[1]", "Check box");
    public GenericMethods.LocatorsDetails yesButton = new GenericMethods.LocatorsDetails("xpath", "//div[@class='chatbot_Chip chipInRow chipItem']//span[text()='Yes']", "Yes button");
    public GenericMethods.LocatorsDetails SearchFiled = new GenericMethods.LocatorsDetails("xpath", "//div[@class='ssc__input-container']/input", "Search Textfield");
    public GenericMethods.LocatorsDetails DOBfield = new GenericMethods.LocatorsDetails("xpath", "//div[@class='dob__input-container']//input[contains(@id, 'dobInput')]", "DOB Filed");
    public GenericMethods.LocatorsDetails suggestionList = new GenericMethods.LocatorsDetails("xpath", "//div[@id='ssc__option_0']//div[@class='ssc__heading']", "Suggestion list");
    public GenericMethods.LocatorsDetails ReachedMaxLimit=new GenericMethods.LocatorsDetails("xpath","//span[.='There was an error while processing your request, please try again later']","Reached Max Limit");
}
