import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



public class SignupPageTest {

    private static final String HOME_PAGE_URL = "https://www.facebook.com/";
    private static final By LOG_INTO_FACEBOOK_HEADER = By.xpath(("//*[text()='Log Into Facebook']"));
    private static final By CREATE_NEW_ACC_BTN = By.xpath("//a[text()='Create new account']");

    private static final By SIGNUP_HEADER = By.xpath("//*[text()='Create a new account']");

    private static final By FIRSTNAME_FIELD = By.xpath("//input[@name='firstname']");
    private static final By LASTNAME_FIELD = By.xpath("//input[@name='lastname']");
    private static final By MONTH_SELECT = By.xpath("//select[@id='month']");
    private static final By DAY_SELECT = By.xpath("//select[@id='day']");
    private static final By YEAR_SELECT = By.xpath("//select[@id='year']");

    private static final By FEMALE_LABEL = By.xpath("//*[text()='Female']");
    private static final By FEMALE_RADIO = By.xpath("//input[@name='sex' and @value='1']");
    private static final By MALE_LABEL = By.xpath("//*[text()='Male']");
    private static final By MALE_RADIO = By.xpath("//input[@name='sex' and @value='2']");
    private static final By CUSTOM_LABEL = By.xpath("//*[text()='Custom']");
    private static final By CUSTOM_RADIO = By.xpath("//input[@name='sex' and @value='-1']");
    private static final By SELECT_PRONOUN_DROPDOWN = By.xpath("//select[@id='preferred_pronoun']");
    private static final By GENDER_FIELD = By.xpath("//input[@id='custom_gender']");

    private static final By MOBILE_EMAIL_FIELD = By.xpath("//input[@name='reg_email__']");
    private static final By PASSWORD_FIELD = By.xpath("//input[@name='reg_passwd__']");
    private static final By SIGNUP_BTN = By.xpath("//button[@name='websubmit']");
    private static final By HAVE_ACCOUNT_LINK = By.xpath("//a[text()='Already have an account?']");

    private static final By MISSING_INFO_ICON = By.xpath("//i[contains(@class, '_5dbc')]");
    private static final By TERMS_LINK = By.xpath("//a[@id='terms-link']");
    private static final By PRIVACY_POLICIES_LINK = By.xpath("//a[@id='privacy-link']");

    private static WebDriver driver;

//    @BeforeAll
//    public static void classSetup() {
//        driver = SharedDriver.getWebDriver();
//    }
//
//    @AfterAll
//    public static void classTearDown() {
//        SharedDriver.closeDriver();
//        //would be nice to delete account after all as well for reusability
//    }

    @BeforeEach
    public void setupEach() {
        driver = SharedDriver.getWebDriver(); // fresh instance each time
        driver.get(HOME_PAGE_URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // most useful way of wait, specific to needs
        //WebElement createNewAccElement = wait.until(ExpectedConditions.visibilityOfElementLocated(CREATE_NEW_ACC_BTN)); - element is visible
        //WebElement createNewAccElement = wait.until(ExpectedConditions.presenceOfElementLocated(CREATE_NEW_ACC_BTN)); - element is in DOM
        WebElement createNewAccElement = wait.until(ExpectedConditions.elementToBeClickable(CREATE_NEW_ACC_BTN)); // element is clickable
        createNewAccElement.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(SIGNUP_HEADER));
    }

    @AfterEach
    public void testTeardown() {
        SharedDriver.closeDriver();
    }

    @Test
    public void signupScreenUITest() {
        assertNotNull(driver.findElement(SIGNUP_HEADER));
        assertNotNull(driver.findElement(FIRSTNAME_FIELD));
        assertNotNull(driver.findElement(LASTNAME_FIELD));
        assertNotNull(driver.findElement(MONTH_SELECT));
        assertNotNull(driver.findElement(DAY_SELECT));
        assertNotNull(driver.findElement(YEAR_SELECT));

        assertNotNull(driver.findElement(FEMALE_LABEL));
        assertNotNull(driver.findElement(FEMALE_RADIO));
        assertNotNull(driver.findElement(MALE_LABEL));
        assertNotNull(driver.findElement(MALE_RADIO));
        assertNotNull(driver.findElement(CUSTOM_LABEL));
        assertNotNull(driver.findElement(CUSTOM_RADIO));

        assertNotNull(driver.findElement(MOBILE_EMAIL_FIELD));
        assertNotNull(driver.findElement(PASSWORD_FIELD));
        assertNotNull(driver.findElement(SIGNUP_BTN));
        assertNotNull(driver.findElement(HAVE_ACCOUNT_LINK));
    }


    @ParameterizedTest
    @CsvSource({"John, Smith, john.smith@test.com, Password123!",
            "Alexandria, Montgomery-Williams, alex.mw@test.com, StrongPass!23@#$%^&*()",
            "Michael, Jordan, 1234567890,MyPass2024!"
    })
    public void validSignUpTest(String a, String b, String c, String d) {
        WebElement firstNameFieldElement = driver.findElement(FIRSTNAME_FIELD);
        firstNameFieldElement.sendKeys(a);
        WebElement lastNameFieldElement = driver.findElement(LASTNAME_FIELD);
        lastNameFieldElement.sendKeys(b);
        WebElement mobileEmailFieldElement = driver.findElement(MOBILE_EMAIL_FIELD);
        mobileEmailFieldElement.sendKeys(c);
        WebElement passwordFieldElement = driver.findElement(PASSWORD_FIELD);
        passwordFieldElement.sendKeys(d);
        WebElement signupBtnElement = driver.findElement(SIGNUP_BTN);
        assertNotNull(signupBtnElement);
        signupBtnElement.click();
        assertNotNull(driver.findElement(SIGNUP_HEADER));
        assertNotNull(driver.findElement(MISSING_INFO_ICON));
    }

    @ParameterizedTest
    @CsvSource({"'', '',May,15,1990, valid@test.com, ValidPass123!",
            "' ', ' ',May,15,1990, valid@test.com, ValidPass123!",
            "'', Smith,May,15,1990, valid@test.com, ValidPass123!",
            "' ', Smith,May,15,1990, valid@test.com, ValidPass123!",
            "John, '',May,15,1990, valid@test.com, ValidPass123!",
            "John, ' ',May,15,1990, valid@test.com, ValidPass123!",
            "Jo@hn, Smith,Jan,1,2000,valid@test.com, ValidPass123!",
            "John, Smi@th,Feb,28,1980,valid@test.com,ValidPass123! ",
            "Aaaaaaaaaaaaaaaaaaaa, Smith,Jan,1,2000,valid@test.com, ValidPass123!",
            "John, Aaaaaaaaaaaaaaaaaaaa,Feb,28,1980,valid@test.com,ValidPass123! "
    })
    public void negativeNameFieldыTests(String name, String lastname, String month, String day, String year, String email, String password) {
        WebElement firstNameFieldElement = driver.findElement(FIRSTNAME_FIELD);
        firstNameFieldElement.sendKeys(name);
        WebElement lastNameFieldElement = driver.findElement(LASTNAME_FIELD);
        lastNameFieldElement.sendKeys(lastname);
        Select monthSelect = new Select(driver.findElement(MONTH_SELECT));
        monthSelect.selectByVisibleText(month);
        Select daySelect = new Select(driver.findElement(DAY_SELECT));
        daySelect.selectByVisibleText(day);
        Select yearSelect = new Select(driver.findElement(YEAR_SELECT));
        yearSelect.selectByVisibleText(year);
        driver.findElement(FEMALE_RADIO).click();
        String isFemaleChecked = driver.findElement(FEMALE_RADIO).getAttribute("checked");
        assertNotNull(isFemaleChecked);
        assertEquals("true", isFemaleChecked);
        WebElement mobileEmailFieldElement = driver.findElement(MOBILE_EMAIL_FIELD);
        mobileEmailFieldElement.sendKeys(email);
        WebElement passwordFieldElement = driver.findElement(PASSWORD_FIELD);
        passwordFieldElement.sendKeys(password);
        WebElement signupBtnElement = driver.findElement(SIGNUP_BTN);
        assertNotNull(signupBtnElement);
        signupBtnElement.click();
        assertNotNull(driver.findElement(SIGNUP_HEADER));
    }

    @ParameterizedTest
    @CsvSource({"John, Smith,Jan,1,2000,'', ValidPass123!",
            "John, Smith,Jan,1,2000,' ', ValidPass123!",
            "John, Smith,Jan,1,2000,invalid@test, ValidPass123!",
            "John, Smith,Jan,1,2000,invalid, ValidPass123!",
            "John, Smith,Jan,1,2000,123, ValidPass123!",
            "John, Smith,Jan,1,2000,12345678901234567890, ValidPass123!"
    })
    public void negativeMobileEmailFieldTest(String name, String lastname, String month, String day, String year, String email, String password) {
        WebElement firstNameFieldElement = driver.findElement(FIRSTNAME_FIELD);
        firstNameFieldElement.sendKeys(name);
        WebElement lastNameFieldElement = driver.findElement(LASTNAME_FIELD);
        lastNameFieldElement.sendKeys(lastname);
        Select monthSelect = new Select(driver.findElement(MONTH_SELECT));
        monthSelect.selectByVisibleText(month);
        Select daySelect = new Select(driver.findElement(DAY_SELECT));
        daySelect.selectByVisibleText(day);
        Select yearSelect = new Select(driver.findElement(YEAR_SELECT));
        yearSelect.selectByVisibleText(year);
        driver.findElement(FEMALE_RADIO).click();
        WebElement mobileEmailFieldElement = driver.findElement(MOBILE_EMAIL_FIELD);
        mobileEmailFieldElement.sendKeys(email);
        WebElement passwordFieldElement = driver.findElement(PASSWORD_FIELD);
        passwordFieldElement.sendKeys(password);
        WebElement signupBtnElement = driver.findElement(SIGNUP_BTN);
        assertNotNull(signupBtnElement);
        signupBtnElement.click();
        assertNotNull(driver.findElement(SIGNUP_HEADER));
    }

    @ParameterizedTest
    @CsvSource({"John, Smith,Jan,1,2000,valid@test.com, ''",
            "John, Smith,Jan,1,2000,valid@test.com, ' '",
            "John, Smith,Jan,1,2000,valid@test.com, ab",
            "John, Smith,Jan,1,2000,valid@test.com, aaaaaaaaaaaaa",
            "John, Smith,Jan,1,2000,valid@test.com, 1111111111111",
            "John, Smith,Jan,1,2000,valid@test.com, ' OR '1'='1",
            "John, Smith,Jan,1,2000,valid@test.com, Password\n123!",
//            "John, Smith,Jan,1,2000,valid@test.com, aaaaaa123",
//            "John, Smith,Jan,1,2000,valid@test.com, aaaaaa!%123",
//            "John, Smith,Jan,1,2000,valid@test.com, aaaaaa123!",
//            "John, Smith,Jan,1,2000,valid@test.com, aaaaaa123!"
// FB accepts without uppercase, without special characters and very long passwords, verified manually
    })
    public void negativePasswordFieldTest(String name, String lastname, String month, String day, String year, String email, String password) {
        WebElement firstNameFieldElement = driver.findElement(FIRSTNAME_FIELD);
        firstNameFieldElement.sendKeys(name);
        WebElement lastNameFieldElement = driver.findElement(LASTNAME_FIELD);
        lastNameFieldElement.sendKeys(lastname);
        Select monthSelect = new Select(driver.findElement(MONTH_SELECT));
        monthSelect.selectByVisibleText(month);
        Select daySelect = new Select(driver.findElement(DAY_SELECT));
        daySelect.selectByVisibleText(day);
        Select yearSelect = new Select(driver.findElement(YEAR_SELECT));
        yearSelect.selectByVisibleText(year);
        driver.findElement(FEMALE_RADIO).click();
        String isFemaleChecked = driver.findElement(FEMALE_RADIO).getAttribute("checked");
        assertNotNull(isFemaleChecked);
        assertEquals("true", isFemaleChecked);
        WebElement mobileEmailFieldElement = driver.findElement(MOBILE_EMAIL_FIELD);
        mobileEmailFieldElement.sendKeys(email);
        WebElement passwordFieldElement = driver.findElement(PASSWORD_FIELD);
        passwordFieldElement.sendKeys(password);
        WebElement signupBtnElement = driver.findElement(SIGNUP_BTN);
        assertNotNull(signupBtnElement);
        signupBtnElement.click();
        assertNotNull(driver.findElement(SIGNUP_HEADER));
    }

    @ParameterizedTest
    @CsvSource({"'','',Feb,31,2025,'',''",
            "Jo@hn, Sm@ith,Jan,1,2000,invalid@test, 11",
            "John, '',Jan,1,2000,'', ValidPass123!"
    })
    public void combinationNegativeTest(String name, String lastname, String month, String day, String year, String email, String password) {
        WebElement firstNameFieldElement = driver.findElement(FIRSTNAME_FIELD);
        firstNameFieldElement.sendKeys(name);
        WebElement lastNameFieldElement = driver.findElement(LASTNAME_FIELD);
        lastNameFieldElement.sendKeys(lastname);
        Select monthSelect = new Select(driver.findElement(MONTH_SELECT));
        monthSelect.selectByVisibleText(month);
        Select daySelect = new Select(driver.findElement(DAY_SELECT));
        daySelect.selectByVisibleText(day);
        Select yearSelect = new Select(driver.findElement(YEAR_SELECT));
        yearSelect.selectByVisibleText(year);
        driver.findElement(FEMALE_RADIO).click();
        WebElement mobileEmailFieldElement = driver.findElement(MOBILE_EMAIL_FIELD);
        mobileEmailFieldElement.sendKeys(email);
        WebElement passwordFieldElement = driver.findElement(PASSWORD_FIELD);
        passwordFieldElement.sendKeys(password);
        WebElement signupBtnElement = driver.findElement(SIGNUP_BTN);
        assertNotNull(signupBtnElement);
        signupBtnElement.click();
        assertNotNull(driver.findElement(SIGNUP_HEADER));
    }

    @Test
    public void customGenderFieldVerificationTest() {
        driver.findElement(CUSTOM_RADIO).click();
        WebElement selectPronounDropdownElement = driver.findElement(SELECT_PRONOUN_DROPDOWN);
        assertNotNull(selectPronounDropdownElement);
        WebElement genderFieldElement = driver.findElement(GENDER_FIELD);
        assertNotNull(genderFieldElement);
    }


    @Test
    public void alreadyHaveAnAccLinkTest() {
        WebElement haveAccElement = driver.findElement(HAVE_ACCOUNT_LINK);
        assertNotNull(haveAccElement);
        haveAccElement.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement logIntoFBHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(LOG_INTO_FACEBOOK_HEADER));
        assertNotNull(logIntoFBHeader);
    }

    @Test
    public void sexRadioBtnTest() {
        //verify female gender is checked
        driver.findElement(FEMALE_RADIO).click();
        String isFemaleChecked = driver.findElement(FEMALE_RADIO).getAttribute("checked");
        assertNotNull(isFemaleChecked);
        assertEquals("true", isFemaleChecked);
        //verify male gender is checked
        driver.findElement(MALE_RADIO).click();
        String isMaleChecked = driver.findElement(MALE_RADIO).getAttribute("checked");
        assertNotNull(isMaleChecked);
        assertEquals("true", isMaleChecked);
        //verify custom gender is checked
        driver.findElement(CUSTOM_RADIO).click();
        String isCustomChecked = driver.findElement(CUSTOM_RADIO).getAttribute("checked");
        assertNotNull(isCustomChecked);
        assertEquals("true", isCustomChecked);
    }

    @Test
    public void errorMessageTest() {
        driver.findElement(SIGNUP_BTN).click();
        driver.findElement(MOBILE_EMAIL_FIELD).click();

        WebElement errorMsg = driver.findElement(By.xpath("//*[contains(text(),'to reset your password')]"));
        assertNotNull(errorMsg);
    }

    @ParameterizedTest
    @CsvSource({"Jan,1,2000",
            "Feb,20,1970"
    })
    public void dateDropDownTest(String month, String day, String year) {
        Select monthSelect = new Select(driver.findElement(MONTH_SELECT));
        monthSelect.selectByVisibleText(month);
        String monthValue = monthSelect.getFirstSelectedOption().getText();
        assertEquals(month, monthValue);

        Select daySelect = new Select(driver.findElement(DAY_SELECT));
        daySelect.selectByVisibleText(day);
        String dayValue = driver.findElement(DAY_SELECT).getAttribute("value");
        assertEquals(day, dayValue);

        Select yearSelect = new Select(driver.findElement(YEAR_SELECT));
        yearSelect.selectByVisibleText(year);
        String yearValue = driver.findElement(YEAR_SELECT).getAttribute("value");
        assertEquals(year, yearValue);

    }

    @Test
    public void errorMessagesTest() {
        String firstname = "John";
        String lastname = "Smith";
        String month = "May";
        String day = "20";
        String year = "1999";
        String validPhoneEmail = "johns@sample.com";
        String invalidPhoneEmail = "invalid@test";
        String password = "samplePassword";

        driver.findElement(SIGNUP_BTN).click();
        driver.findElement(LASTNAME_FIELD).click();
        WebElement lastnameErrorMsg = driver.findElement(By.xpath("//*[contains(text(),'your name')]"));
        assertNotNull(lastnameErrorMsg);
        driver.findElement(LASTNAME_FIELD).sendKeys(lastname);
        driver.findElement(FIRSTNAME_FIELD).click();
        WebElement firstnameErrorMsg = driver.findElement(By.xpath("//*[contains(text(),'your name')]"));
        assertNotNull(firstnameErrorMsg);
        driver.findElement(FIRSTNAME_FIELD).sendKeys(firstname);
        driver.findElement(MONTH_SELECT).click();
        WebElement monthErrorMsg = driver.findElement(By.xpath("//*[contains(text(),'use your real birthday')]"));
        assertNotNull(monthErrorMsg);
        Select monthSelect = new Select(driver.findElement(MONTH_SELECT));
        monthSelect.selectByVisibleText(month);
        driver.findElement(DAY_SELECT).click();
        WebElement dayErrorMsg = driver.findElement(By.xpath("//*[contains(text(), 'use your real birthday')]"));
        assertNotNull(dayErrorMsg);
        Select daySelect = new Select(driver.findElement(DAY_SELECT));
        daySelect.selectByVisibleText(day);
        driver.findElement(YEAR_SELECT).click();
        WebElement yearErrorMsg = driver.findElement(By.xpath("//*[contains(text(), 'use your real birthday')]"));
        assertNotNull(yearErrorMsg);
        Select yearSelect = new Select(driver.findElement(YEAR_SELECT));
        yearSelect.selectByVisibleText(year);
        driver.findElement(SIGNUP_BTN).click();
        WebElement genderErrorMsg = driver.findElement(By.xpath("//*[contains(text(), 'choose a gender')]"));
        assertNotNull(genderErrorMsg);
        driver.findElement(CUSTOM_RADIO).click();
        driver.findElement(SIGNUP_BTN).click();
        WebElement phoneEmailErrorMsg = driver.findElement(By.xpath("//*[contains(text(), 'reset your password')]"));
        assertNotNull(phoneEmailErrorMsg);
        driver.findElement(MOBILE_EMAIL_FIELD).sendKeys(invalidPhoneEmail);
        driver.findElement(SIGNUP_BTN).click();
        WebElement invalidPhoneEmailErrorMsg = driver.findElement(By.xpath("//*[contains(text(), 'valid mobile number')]"));
        assertNotNull(invalidPhoneEmailErrorMsg);
        driver.findElement(MOBILE_EMAIL_FIELD).sendKeys(validPhoneEmail);
        driver.findElement(PASSWORD_FIELD).click();
        WebElement passwordErrorMsg = driver.findElement(By.xpath("//*[contains(text(), 'Enter a combination')]"));
        assertNotNull(passwordErrorMsg);
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        driver.findElement(SIGNUP_BTN).click();
        WebElement customGenderErrorMsg = driver.findElement(By.xpath("//*[contains(text(), 'your pronoun')]"));
        assertNotNull(customGenderErrorMsg);
    }


    @ParameterizedTest
    @ValueSource(strings = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"})
    public void monthDropDownTest(String month) {
        driver.findElement(MONTH_SELECT).click();
        driver.findElement(By.xpath("//*[text()='" + month + "']")).click();
        Select monthSelect = new Select(driver.findElement(MONTH_SELECT));
        String monthValue = monthSelect.getFirstSelectedOption().getText();
        assertEquals(month, monthValue);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "15", "31"})
    public void dayDropDownTest(String day) {
        Select daySelect = new Select(driver.findElement(DAY_SELECT));
        daySelect.selectByVisibleText(day);
        String dayValue = driver.findElement(DAY_SELECT).getAttribute("value");
        assertEquals(day, dayValue);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1905", "1960", "2025"})
    public void yearDropDownTest(String year) {
        Select yearSelect = new Select(driver.findElement(YEAR_SELECT));
        yearSelect.selectByVisibleText(year);
        String yearValue = driver.findElement(YEAR_SELECT).getAttribute("value");
        assertEquals(year, yearValue);
    }

    @Test
    public void genderRadioButtonsSiblingsTest() {
        WebElement femaleRadio = driver.findElement(By.xpath("//label[text()='Female']/input"));
        WebElement maleRadio = driver.findElement(By.xpath("//label[text()='Male']/input"));
        WebElement customRadio = driver.findElement(By.xpath("//label[text()='Custom']/input"));

        femaleRadio.click();
        assertTrue(femaleRadio.isSelected(), "Female radio should be selected");
        maleRadio.click();
        assertTrue(maleRadio.isSelected(), "Male radio should be selected");
        customRadio.click();
        assertTrue(customRadio.isSelected(), "Custom radio should be selected");
    }

    @Test
    public void termsLinkTest() {
        String originalWindow = driver.getWindowHandle();
        driver.findElement(TERMS_LINK).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for(String str : driver.getWindowHandles()){
            driver.switchTo().window(str);
        }
        driver.getCurrentUrl();

        String expectedUrl = "https://www.facebook.com/legal/terms/update";
        assertEquals(expectedUrl, driver.getCurrentUrl());
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @Test
    public void PrivacyPoliciesLinkTest() {
        String originalWindow = driver.getWindowHandle();
        driver.findElement(PRIVACY_POLICIES_LINK).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for(String str : driver.getWindowHandles()){
            driver.switchTo().window(str);
        }
        driver.getCurrentUrl();

        String expectedUrl = "https://www.facebook.com/privacy/policy/?entry_point=data_policy_redirect&entry=0";
        assertEquals(expectedUrl, driver.getCurrentUrl());
        driver.close();
        driver.switchTo().window(originalWindow);

    }
}
