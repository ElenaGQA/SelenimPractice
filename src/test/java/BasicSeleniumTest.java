import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BasicSeleniumTest {

    private static final String HOME_PAGE_URL = "https://www.facebook.com/";

    private static WebDriver driver;

    @BeforeAll
    public static void classSetup() {
        driver = SharedDriver.getWebDriver();
        driver.get(HOME_PAGE_URL);
    }

    @AfterAll
    public static void classTearDown() {
        SharedDriver.closeDriver();

    }

    @AfterEach
    public void testTeardown() {
        driver.get(HOME_PAGE_URL);
    }

    @Test
    public void homePageURLTest() {
        String actualURL = driver.getCurrentUrl();
        assertEquals(HOME_PAGE_URL, actualURL, "URLs do not match");
    }

    @Test
    public void findElementByAttributeTest() {

        //find the elements by different webdriver find BY methods
        WebElement elementEmailById = driver.findElement(By.id("email"));
        WebElement elementEmailByName = driver.findElement(By.name("email"));
        WebElement elementCreatePageByLinkText = driver.findElement(By.linkText("Create a Page"));
        WebElement elementCreatePageByPartitialLinkText = driver.findElement(By.partialLinkText("a Page"));
        WebElement elementEmailByCss = driver.findElement(By.cssSelector("#email"));
        assertNotNull(elementEmailById);

        //Make sure you have one element, and not many!
        List<WebElement> elementsByClassName = driver.findElements(By.className("inputtext"));
        System.out.println(elementsByClassName.size()); //2
    }

    @Test
    public void findElementsByXpathTest() {
        WebElement emailElement = driver.findElement(By.xpath("//input[@name='email']"));
        assertNotNull(emailElement);
        WebElement passwordElement = driver.findElement(By.xpath("//input[@data-testid='royal-pass']"));
        assertNotNull(passwordElement);
        WebElement loginButtonElement = driver.findElement(By.xpath("//button[@type='submit']"));
        assertNotNull(loginButtonElement);
        WebElement forgotPassElement = driver.findElement(By.xpath("//a[text()='Forgot password?']"));
        assertNotNull(forgotPassElement);
        WebElement createNewAccButton = driver.findElement(By.xpath("//*[text() = 'Create new account']"));
        assertNotNull(createNewAccButton);

    }

    @Test
    public void loginScreenTest() {

        WebElement emailElement = driver.findElement(By.xpath("//input[@name='email']"));
        assertNotNull(emailElement);
        // send text
        emailElement.sendKeys("sample@gmail.com");
        //get text from browser
        String emailValue = emailElement.getAttribute("value");
        assertEquals("sample@gmail.com", emailValue);

        //same with password
        WebElement passwordElement = driver.findElement(By.xpath("//input[@data-testid='royal-pass']"));
        assertNotNull(passwordElement);
        passwordElement.sendKeys("123456");
        String passValue = passwordElement.getAttribute("value");
        assertEquals("123456", passValue);

        //find login button and click on it
        WebElement loginButtonElement = driver.findElement(By.xpath("//button[@type='submit']"));
        assertNotNull(loginButtonElement);
        loginButtonElement.click();

    }



}
