package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Main {
    private WebDriver driver;
    private static final String BASE_URL = "https://candyshop.by";

    @FindBy(xpath = "//*[@id=\"menu\"]/ul[2]")
    private WebElement menuElement;

    @FindBy(xpath = "//*[@id=\"menu\"]/ul[2]/li/ul/li[1]/ul/li[1]/a")
    private WebElement currentMenuItem;

    @FindBy(xpath = "//*[@id=\"page\"]/div[4]/div/div[2]/div[3]/div/div[1]/div/div/button")
    private WebElement product;

    @FindBy(xpath = "//*[@id=\"page\"]/div[4]/div/div[2]/span/div/div[3]/div/div[2]/a")
    private WebElement addToCartButton;

    @FindBy(xpath = "//*[@id=\"simplecheckout_cart\"]/div[1]")
    private WebElement cartNotEmpty;

    @FindBy(xpath = "//*[@id=\"search\"]/span/input")
    private WebElement searchInput;

    @FindBy(xpath = "//*[@id=\"search\"]/span/div/div")
    private WebElement resultOfSearch;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "C:\\msedgedriver.exe");
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        PageFactory.initElements(driver, this);
    }

    @Test
    public void testAddToCart() throws InterruptedException {
        Actions actions = new Actions(driver);
        actions.moveToElement(menuElement).perform();
        currentMenuItem.click();
        product.click();
        Thread.sleep(5000);
        addToCartButton.click();
        Thread.sleep(2000); // Подождите, чтобы корзина успела обновиться
        Assert.assertTrue(cartNotEmpty.isDisplayed());
    }

    @Test
    public void testSearchItemOnTheSite() throws InterruptedException {
        Thread.sleep(5000);
        searchInput.sendKeys("Перезаряжаемый вибратор Pretty Love Chris с клиторальной стимуляцией, бирюзовый");
        Thread.sleep(2000); // Подождите, чтобы результаты поиска успели обновиться
        System.out.println(resultOfSearch.getText());
        Assert.assertTrue(resultOfSearch.getText().contains("Показать все результаты"));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
