import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class ShoppingCart {

    private WebDriver driver;
    private String baseUrl;


    @Before
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        baseUrl = "http://automationpractice.com/index.php";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void addCart() throws InterruptedException {
        driver.get(baseUrl);
        driver.manage().window().maximize();

        //search for summer dress
        WebElement search = driver.findElement(new By.ByCssSelector("#search_query_top"));
        WebElement submit_search = driver.findElement(By.cssSelector(".btn.btn-default.button-search"));
        search.sendKeys("Summer Dress");

        submit_search.click();
        WebElement search_result = driver.findElement(By.xpath("//div[@class='product-container']/div[@class='right-block']/h5/a[@class='product-name']"));
        System.out.println(search_result.getText());

        //verifying the search returns the summer dresses
        Assert.assertTrue(search_result.getText().contains("Printed Summer Dress"));
        //hover over the product and add product to Cart
        Actions action = new Actions(driver);
        action.moveToElement(search_result).build().perform();
        driver.findElement(By.cssSelector("a.button.ajax_add_to_cart_button.btn.btn-default")).click();

        //proceed to shopping cart
        driver.findElement(By.cssSelector("a.btn.btn-default.button.button-medium")).click();
        //proceed to checkout
        driver.findElement(By.cssSelector("a.button.btn.btn-default.standard-checkout.button-medium")).click();

        //Verifying you are on sign in page
        String expected = driver.findElement(By.cssSelector("h1.page-heading")).getText();
        Assert.assertEquals(expected, "AUTHENTICATION");
    }

    @After
    public void closeBrowser() {
        driver.close();
        driver.quit();
    }
}
