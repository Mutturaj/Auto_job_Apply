package Locators;

import customEntities.GenericMethods;

public class Linkedin_Locators extends GenericMethods {

    public GenericMethods.LocatorsDetails EmailID = new GenericMethods.LocatorsDetails("xpath", "//input[@id='username' or @id='email-or-phone']", "EmailID Input");
    public GenericMethods.LocatorsDetails Password = new LocatorsDetails("xpath", "//input[@id='password']", "Password Input");

    public GenericMethods.LocatorsDetails SignInButton = new LocatorsDetails("xpath", "//button[@type='submit' or @value='Agree & Join']", "Sign in Button");
    public GenericMethods.LocatorsDetails JobIcon = new LocatorsDetails("xpath", "//li-icon[@type='job']//*[name()='svg' and @class='mercado-match']", "Job Icon");
    public GenericMethods.LocatorsDetails SearchIcon = new LocatorsDetails("xpath", "//*[name()='svg' and @class='jobs-search-box__search-icon--custom']", "Search Icon");
    public GenericMethods.LocatorsDetails ShowAllButton = new LocatorsDetails("xpath", "//div[@class='discovery-templates-vertical-list__footer']/a[@href='https://www.linkedin.com/jobs/collections/recommended?discover=recommended&discoveryOrigin=JOBS_HOME_JYMBII']", "Show All Button");
    public GenericMethods.LocatorsDetails TitleOfJob = new LocatorsDetails("xpath", "//input[contains(@class, 'jobs-search-box__text-input') and contains(@class, 'jobs-search-global-typeahead__input')]", "Title of the Job Input");
    public GenericMethods.LocatorsDetails LocationOfJob = new LocatorsDetails("xpath", "//input[@aria-label=\"City, state, or zip code\" and contains(@id, 'jobs-search-box-location-id-')]", "Location of the Job");
    public GenericMethods.LocatorsDetails SearchBox = new LocatorsDetails("xpath", "//button[text()='Search']", "Search Box");
    public GenericMethods.LocatorsDetails listOfJobs = new LocatorsDetails("xpath", "//li[contains(@class, 'job-card-container__job-insight-flavor-pill')]//div[contains(@class, 'job-card-container__job-insight-text') and contains(., 'Easy Apply')] | //li[contains(@class, 'job-card-container__footer-item')]//span[contains(., 'Easy Apply')]", "List of Jobs");
    public GenericMethods.LocatorsDetails EasyApplyFilter = new LocatorsDetails("xpath", "//button[@aria-label='Easy Apply filter.']", "Easy Apply Filter");
    public GenericMethods.LocatorsDetails listOfJobsFrame = new LocatorsDetails("cssSelector", ".scaffold-layout__list .jobs-search-results-list", "List of Jobs Frame");
    public GenericMethods.LocatorsDetails continueButton = new LocatorsDetails("xpath", "//div[@class='display-flex']//button//span[text()='Continue']", "Continue button");
    public GenericMethods.LocatorsDetails easyApplyButton = new LocatorsDetails("xpath", "//button[contains(normalize-space(@class), 'jobs-apply-button artdeco-button artdeco-button--3 artdeco-button--primary ember-view')]//span[text()='Easy Apply']", "Easy Apply button");
    public GenericMethods.LocatorsDetails JobTitle = new LocatorsDetails("xpath", "//h2[@id='jobs-apply-header']", "Job Name");
    public GenericMethods.LocatorsDetails discard = new LocatorsDetails("xpath", "//button[@data-control-name='discard_application_confirm_btn']//span[text()='Discard']", "Discard Button");
    public GenericMethods.LocatorsDetails listOfPages = new LocatorsDetails("xpath", "//ul[@class='artdeco-pagination__pages artdeco-pagination__pages--number']//li//button[contains(@aria-label, 'Page')]", "List of Pages");
    public GenericMethods.LocatorsDetails dot = new LocatorsDetails("xpath", "//ul[@class='artdeco-pagination__pages artdeco-pagination__pages--number']//li//button[contains(@aria-label, 'Page')]", "Dots in the list of the pages");
    public GenericMethods.LocatorsDetails submitApplicationButton = new LocatorsDetails("xpath", "//span[text()='Submit application']", "Submit Application button");
    public GenericMethods.LocatorsDetails continueApplyingButton = new LocatorsDetails("xpath", "//span[@class='artdeco-button__text' and text()='Continue applying']", "Continue applying button");
    public GenericMethods.LocatorsDetails nextButton = new LocatorsDetails("xpath", "//button[@aria-label='Continue to next step']//span[text()='Next']", "Next button");
    public GenericMethods.LocatorsDetails radioButtons1 = new LocatorsDetails("xpath", "//div[@class='fb-text-selectable__option display-flex']//input[@type='radio']", "List of Radio buttons");
    public GenericMethods.LocatorsDetails radioButtons2 = new LocatorsDetails("xpath", "//div[@class='fb-text-selectable__option display-flex']//input[@type='radio']", "Radio buttons");
    public GenericMethods.LocatorsDetails dropdownButtons = new LocatorsDetails("xpath", "//span[@class='visually-hidden']//following::select", "List of DropDowns");
    public GenericMethods.LocatorsDetails checkBox = new LocatorsDetails("xpath", "//input[@type='checkbox' and contains(@class, 'fb-form-element__checkbox')]", "List of Checkbox");
    public GenericMethods.LocatorsDetails reviewButton = new LocatorsDetails("xpath", "//span[text()='Review']", "Review Button");
    public GenericMethods.LocatorsDetails AnswerTextfield = new LocatorsDetails("xpath", "//div[@class='artdeco-text-input--container ember-view']//input[@type='text']", "List of answers Textfield");
    public GenericMethods.LocatorsDetails Questions1 = new LocatorsDetails("xpath", "//div[@class='artdeco-text-input--container ember-view']//label[@class='artdeco-text-input--label']", "All Questions");
    public GenericMethods.LocatorsDetails Question2 = new LocatorsDetails("xpath", "//span[@class='jobs-easy-apply-form-section__group-title t-14']", "All Questions");
    public GenericMethods.LocatorsDetails Question3 = new LocatorsDetails("xpath", "//label[contains(@class, 'fb-dash-form-element__label')]//span[contains(., 'Location (city)')][1]", "Email or Location text");
    public GenericMethods.LocatorsDetails cityName = new LocatorsDetails("xpath", "//input[contains(@id, 'location-GEO-LOCATION') or contains(@id, 'city-HOME-CITY')]", "Enter City Name");
    public GenericMethods.LocatorsDetails radioButtons = new LocatorsDetails("xpath", ".//input[@type='radio' and @value='Yes']", "Radio button");
    public GenericMethods.LocatorsDetails radioGroups = new LocatorsDetails("xpath", "//fieldset[@data-test-form-builder-radio-button-form-component='true']", "Radio Groups");
    public GenericMethods.LocatorsDetails DoneButton = new LocatorsDetails("xpath", "//span[text()='Done']", "Done button");
    public GenericMethods.LocatorsDetails textAreaFiled = new LocatorsDetails("xpath", "//textarea[contains(@class, 'artdeco-text-input__textarea') and @required]", "Text Area Filed");
    public GenericMethods.LocatorsDetails viewJobsButton = new LocatorsDetails("xpath", "//span[@class='artdeco-button__text']//span[1]", "View Jobs Button");
    public GenericMethods.LocatorsDetails NotificationIcon = new LocatorsDetails("xpath", "//a[contains(@href, '/notifications/')]//li-icon[@type='bell-fill']", "Notification Icon");
    public GenericMethods.LocatorsDetails nextPage1 = new LocatorsDetails("xpath", "//button[@aria-label='View next page']", "Next Button");
    public GenericMethods.LocatorsDetails closeIcon = new LocatorsDetails("xpath", "//div[@aria-labelledby=\"post-apply-modal\"]//*[name()='svg'and @data-test-icon=\"close-medium\"]", "Close Icon");
    public GenericMethods.LocatorsDetails profileIcon = new LocatorsDetails("xpath", "//span[@class='t-12 global-nav__primary-link-text' and text()='Me']", "Profile Icon");
    public GenericMethods.LocatorsDetails LoginWithEmail = new LocatorsDetails("xpath", "//a[@class=\"sign-in-form__sign-in-cta my-2 py-1 btn-md btn-secondary block min-h-[40px] babybear:w-full\"]", "Login with Email button");
    public GenericMethods.LocatorsDetails AgreeAndReJoinButton = new LocatorsDetails("xpath", "//button[@value='Agree & Join']", "Agree and Re-Join Button");
    public GenericMethods.LocatorsDetails AppliedText = new LocatorsDetails("xpath", "//li[contains(@class, 'job-card-container__footer-item') and contains(@class, 'job-card-container__footer-job-state') and contains(., 'Applied')]", "Applied Text");
    public GenericMethods.LocatorsDetails DateTextField = new LocatorsDetails("xpath", "//input[@placeholder='mm/dd/yyyy']", "Date Text field");
    public GenericMethods.LocatorsDetails reachedMaxLimit=new LocatorsDetails("xpath","//span[text()='You’ve reached the Easy Apply application limit for today. Save this job and come back tomorrow to continue applying.']","Reached Max limit");
}
