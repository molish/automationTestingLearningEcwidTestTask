package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class FilterTest {

    public static StartPage startPage;

    public static WebDriver driver;

    @BeforeClass
    public static void start() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(ConfProperties.getProperty("startpage"));

        startPage = new StartPage(driver);
    }

    @Test
    public void saleFilterTest() {
        startPage.clickSaleCheckBox();
        startPage.waitRefreshing();
        int countElementsDisplayed = startPage.getCountItems();
        int countElementsWithSaleLAbel = startPage.getCountSaleLabels();
        startPage.clickSaleCheckBox();
        Assert.assertEquals(countElementsDisplayed, countElementsWithSaleLAbel);
    }

    @Test
    public void keywordFilterTest() {
        startPage.inputKeywordTextField(ConfProperties.getProperty("keyword"));
        startPage.inputKeywordTextField(String.valueOf(Keys.ENTER));
        startPage.waitRefreshing();
        Assert.assertTrue(startPage.checkAllItemsNameContainsKeyword(ConfProperties.getProperty("keyword")));
        startPage.clearInputKeywordTextField();
        startPage.inputKeywordTextField(String.valueOf(Keys.ENTER));
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

}
