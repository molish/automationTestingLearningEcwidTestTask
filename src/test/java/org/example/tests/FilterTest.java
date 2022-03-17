package org.example.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.example.util.ConfProperties;
import org.example.pages.StartPage;
import org.junit.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;


@Epic("Ecwid test task")
@Feature("Testing filters")
public class FilterTest {

    public static StartPage startPage;

    public static WebDriver driver;

    @Before
    public void start() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(ConfProperties.getProperty("startpage"));

        startPage = new StartPage(driver);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("User tries to select sale items")
    @Description("Press sale check box and check if displayed items have sale label")
    public void saleFilterTest() {
        startPage.clickSaleCheckBox();
        startPage.waitRefreshing();
        int countElementsDisplayed = startPage.getCountItems();
        int countElementsWithSaleLAbel = startPage.getCountSaleLabels();
        startPage.clickSaleCheckBox();
        Assert.assertEquals(countElementsDisplayed, countElementsWithSaleLAbel);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("User tries select keyword items")
    @Description("Inpt keyword in search text field and check if all items have keyword in their names")
    public void keywordFilterTest() {
        startPage.inputKeywordTextField(ConfProperties.getProperty("keyword"));
        startPage.inputKeywordTextField(String.valueOf(Keys.ENTER));
        startPage.waitRefreshing();
        Assert.assertTrue(startPage.checkAllItemsNameContainsKeyword(ConfProperties.getProperty("keyword")));
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
