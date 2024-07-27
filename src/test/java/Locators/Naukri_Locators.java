package Locators;

import customEntities.GenericMethods;

public class Naukri_Locators {
    public GenericMethods.LocatorsDetails EmailID = new GenericMethods.LocatorsDetails("xpath", "//input[@id='usernameField']", "Enter Email");
    public GenericMethods.LocatorsDetails Password = new GenericMethods.LocatorsDetails("xpath", "//input[@id='passwordField']", "Password");
    public GenericMethods.LocatorsDetails LoginButton = new GenericMethods.LocatorsDetails("xpath", "//button[.='Login']", "Login Button");
    public GenericMethods.LocatorsDetails ViewProfile = new GenericMethods.LocatorsDetails("xpath", "//a[text()='View']", "View Profile button");

    public GenericMethods.LocatorsDetails EditIcon = new GenericMethods.LocatorsDetails("xpath", "//div[@class='widgetHead']//span[@class='edit icon' and .='editOneTheme']", "Edit Icon");
    public GenericMethods.LocatorsDetails SaveButton = new GenericMethods.LocatorsDetails("xpath", "//button[.='Save']", "Save button");
    public GenericMethods.LocatorsDetails SuccessMessage = new GenericMethods.LocatorsDetails("xpath", "//p[.='Success']", "Success Message");
    public GenericMethods.LocatorsDetails JobButton = new GenericMethods.LocatorsDetails("xpath", "//div[.='Jobs']", "Job Button");
    public GenericMethods.LocatorsDetails listOfJobs = new GenericMethods.LocatorsDetails("xpath", "//span[@class='ellipsis dspIB valignM    ' and contains(text(),'Yrs')]", "List of Jobs");
    public GenericMethods.LocatorsDetails listOfJobsFromSearch = new GenericMethods.LocatorsDetails("xpath", "//span[@class='ni-job-tuple-icon ni-job-tuple-icon-srp-experience exp']//span[contains(text(),'Yrs')]", "Job List from Search");
    public GenericMethods.LocatorsDetails ApplyButton = new GenericMethods.LocatorsDetails("xpath", "//div[@class='styles_jhc__apply-button-container__5Bqnb']/button[.='Apply']", "Apply Button");
    public GenericMethods.LocatorsDetails ApplyCompanySite = new GenericMethods.LocatorsDetails("xpath", "//div[@class='styles_jhc__apply-button-container__5Bqnb']/button[.='Apply on company site']", "Apply On Company Site Button");
    public GenericMethods.LocatorsDetails Designation = new GenericMethods.LocatorsDetails("xpath", "//input[@placeholder=\"Enter keyword / designation / companies\"]", "Job Search");
    public GenericMethods.LocatorsDetails Experience = new GenericMethods.LocatorsDetails("xpath", "//input[@id='experienceDD']", "Experience ");
    public GenericMethods.LocatorsDetails ExperienceButton = new GenericMethods.LocatorsDetails("xpath", "//span[.='2 years']", "Experience button");
    public GenericMethods.LocatorsDetails Location = new GenericMethods.LocatorsDetails("xpath", "//input[@placeholder=\"Enter location\"]", "Location Text Filed");
    public GenericMethods.LocatorsDetails SearchIcon2 = new GenericMethods.LocatorsDetails("xpath", "//span[.='Search']", "Search Icon");
    public GenericMethods.LocatorsDetails SearchIcon1 = new GenericMethods.LocatorsDetails("xpath", "//button[@class='nI-gNb-sb__icon-wrapper']//span[@class=\"ni-gnb-icn ni-gnb-icn-search\"]", "Search Icon");
    public GenericMethods.LocatorsDetails closeIcon = new GenericMethods.LocatorsDetails("xpath", "//div[@class='crossIcon chatBot chatBot-ic-cross']", "Cross Icon");

}
