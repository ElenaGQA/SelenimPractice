import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    private static WebDriver driver;

    @BeforeAll
    public static void classSetup() {
        driver = SharedDriver.getWebDriver();
    }

    @AfterAll
    public static void classTearDown() {
        SharedDriver.closeDriver();
        //would be nice to delete account after all as well for reusability
    }

    @BeforeEach
    public void setupEach() {
        driver.get(HOME_PAGE_URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement createNewAccElement = wait.until(ExpectedConditions.elementToBeClickable(CREATE_NEW_ACC_BTN));
        createNewAccElement.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(SIGNUP_HEADER));
    }

    @AfterEach
    public void testTeardown() {
        driver.get(HOME_PAGE_URL);
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
    @CsvSource({"'', '','','','','',''",
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
    public void customGenderFieldVerificationTest(){
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


}
