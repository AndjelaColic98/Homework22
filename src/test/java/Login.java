/*
Testirati na stranici https://vue-demo.daniel-avellaneda.com login stranicu.
Test 1: Verifikovati da se u url-u stranice javlja ruta "/login". Verifikovati da atribut type u polju za unos email ima vrednost "email" i za password da ima atribut type "password.
Test 2: Koristeci Faker uneti nasumicno generisan email i password i verifikovati da se pojavljuje poruka "User does not exist".
Test 3: Verifikovati da kad se unese admin@admin.com (sto je dobar email) i pogresan password (generisan faker-om), da se pojavljuje poruka "Wrong password"
Koristiti TestNG i dodajte before i after class metode.
Domaci se kači na github.
*/

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class Login {
    private WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\colic\\OneDrive\\Radna površina\\QA\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get("https://vue-demo.daniel-avellaneda.com");
        driver.manage().window().maximize();
    }

    @Test
    public void test1() {
        WebElement loginBtn = driver.findElement(By.className("btnLogin"));
        loginBtn.click();

        String actualLink = driver.getCurrentUrl();
        Assert.assertTrue(actualLink.contains("/login"));

        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));

        WebElement email = driver.findElement(By.id("email"));
        String emailType = email.getAttribute("type");
        Assert.assertEquals(emailType, "email");

        WebElement password = driver.findElement(By.id("password"));
        String passwordType = password.getAttribute("type");
        Assert.assertEquals(passwordType, "password");
    }

    @Test
    public void test2(){
        Faker faker = new Faker();
        WebElement loginBtn = driver.findElement(By.className("btnLogin"));
        loginBtn.click();

        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));

        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys(faker.internet().emailAddress());

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(faker.internet().password());

        WebElement login = driver.findElement(By.xpath("//*[@id=\"app\"]/div/main/div/div[2]/div/div/div[3]/span/form/div/div[3]/button"));
        login.click();

        String expectedMessage = "User does not exists";

        WebDriverWait webDriverWait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
        webDriverWait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li")));
        String actualMessage = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li")).getText();

        Assert.assertEquals(actualMessage,expectedMessage);

    }

    @Test
    public void test3(){
        Faker faker = new Faker();
        WebElement loginBtn = driver.findElement(By.className("btnLogin"));
        loginBtn.click();

        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));

        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("admin@admin.com");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(faker.internet().password());

        WebElement login = driver.findElement(By.xpath("//*[@id=\"app\"]/div/main/div/div[2]/div/div/div[3]/span/form/div/div[3]/button"));
        login.click();

        String expectedMessage = "Wrong password";

        WebDriverWait webDriverWait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
        webDriverWait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li")));
        String actualMessage = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li")).getText();

        Assert.assertEquals(actualMessage,expectedMessage);
    }
    @AfterClass
    public void afterClass(){
        driver.quit();
    }
}
