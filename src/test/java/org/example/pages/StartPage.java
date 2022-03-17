package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class StartPage {

    public WebDriver driver;

    public StartPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(css = "input#checkbox-on_sale")
    private WebElement saleCheckBox;

    @FindBy(css = "div.form-control--fieldset input.form-control__text")
    private WebElement keyWordInputText;

    @FindBy(css = "button.form-control__ico-btn")
    private WebElement searchBtn;

    //@FindAll({@FindBy(css = "div[class^=\"grid-product--id\"] div.grid-product__title-inner")})
    @FindAll({@FindBy(xpath = "//div[starts-with(@class, 'grid-product grid-product--id')]//div[@class='grid-product__title-inner']")})
    private List<WebElement> items;

    @FindBy(xpath = "//div[starts-with(@class, 'grid__products')]")
    private WebElement gridProducts;

    //@FindAll({@FindBy(css = "div[class^=\"grid-product--id\"] div.label--notice:Распродажа")})
    //@FindAll({@FindBy(xpath = "//div[starts-with(@class, 'grid-product grid-product--id')]//div[@class='label--notice' and text()='Распродажа']")})
    @FindAll({@FindBy(xpath = "//div[text()='Распродажа']")})
    private List<WebElement> saleLabels;

    public void waitRefreshing(){
        String oldListSize = gridProducts.getAttribute("data-items");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(gridProducts, "data-items", oldListSize)));
    }

    public void clickSaleCheckBox(){
        saleCheckBox.click();
    }

    public void clickSearchBtn(){
        searchBtn.click();
    }

    public void inputKeywordTextField(String keyword){
        keyWordInputText.sendKeys(keyword);
    }

    public void clearInputKeywordTextField(){
        keyWordInputText.clear();
    }

    public int getCountItems(){
        return items.size();
    }

    public int getCountSaleLabels(){
        return saleLabels.size();
    }

    public boolean checkAllItemsNameContainsKeyword(String keyword){
        for (WebElement itemName:items) {
            if(!itemName.getText().contains(keyword))
                return false;
        }
        return true;
    }

}
