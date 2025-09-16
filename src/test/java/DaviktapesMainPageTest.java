import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.stream.Stream;


public class DaviktapesMainPageTest {

    private static final String HOME_PAGE_URL = "https://daviktapes.com/";
    public static final By HOME_BUTTON = By.xpath("//a[text()='Home']");
    public static final By COMPANY_BUTTON = By.xpath("//a[text()='Company']");
    public static final By PRODUCTS_BUTTON = By.xpath("//a[text()='Products']");
    public static final By INDUSTRIES_BUTTON = By.xpath("//a[text()='Industries']");
    public static final By KNOWLEDGE_CENTER_BUTTON = By.xpath("//a[text()='Knowledge Center']");
    public static final By CONTACT_BUTTON = By.xpath("//a[text()='CONTACT']");


    private static WebDriver driver;

    @BeforeEach
    public void setupEach() {
        driver = SharedDriver.getWebDriver(); // fresh instance each time
        driver.navigate().to(HOME_PAGE_URL);

    }

    @AfterEach
    public void testTeardown() {
        SharedDriver.closeDriver();
    }



    private static Stream<Arguments> menuOptions() {
        return Stream.of(
                Arguments.of(COMPANY_BUTTON, "About us"),
                Arguments.of(PRODUCTS_BUTTON, "Eco-Friendly Solutions"),
                Arguments.of(INDUSTRIES_BUTTON, "Retail"),
                Arguments.of(KNOWLEDGE_CENTER_BUTTON, "Events")
        );
    }
    @ParameterizedTest
    @MethodSource("menuOptions")
    public void actionTest(By locator, String expectedText) {
        WebElement element = driver.findElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        String actualText = wait.until(ExpectedConditions.visibilityOf(element.findElement(By.xpath("//li[a[text()='" + expectedText + "']]")))).getText();

        Assertions.assertTrue(actualText.contains(expectedText), "The submenu text does not contain expected text!");
    }

    @Test
    public void contactAndHomeButtonFunctionalityTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        driver.findElement(CONTACT_BUTTON).click();
        wait.until(ExpectedConditions.urlContains("https://daviktapes.com/contact-us-new/"));

        String contactUrl = driver.getCurrentUrl();
        Assertions.assertTrue(contactUrl.contains("https://daviktapes.com/contact-us-new/"), "New URL does not contain the expected path");

        driver.findElement(HOME_BUTTON).click();
        wait.until(ExpectedConditions.urlToBe(HOME_PAGE_URL));

        String homeUrl = driver.getCurrentUrl();
        Assertions.assertEquals(HOME_PAGE_URL, homeUrl, "New URL does not contain the expected path");
    }



}
