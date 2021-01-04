package com.example.project.test;

import com.example.project.page_object.AllListsPage;
import com.example.project.page_object.ListPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListTest {

    AndroidDriver<AndroidElement> driver;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("deviceName", "sdk_gphone_x86_arm");
        cap.setCapability("udid", "emulator-5554");
        cap.setCapability("platformName", "Android");
        cap.setCapability("platformVersion", "11");

        cap.setCapability("appPackage", "com.slava.buylist");
        cap.setCapability("appActivity", "com.slava.buylist.MainActivity");

        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AndroidDriver<AndroidElement>(url, cap);

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @BeforeMethod
    public void resetApp() {
        driver.resetApp();
        driver.findElementById("com.android.permissioncontroller:id/continue_button").click();
        driver.findElementById("android:id/button1").click();
    }

    @Test
    public void createNewList_successful() {
        AllListsPage allListsPage = new AllListsPage(driver);
        allListsPage.enterNewListName("New list")
                .clickCreateNewListButton();
        driver.navigate().back();
        driver.navigate().back();
        assertThat(allListsPage.firstListName.getText()).isEqualTo("New list");
    }

    @Test
    public void addItemToList() {
        AllListsPage allListsPage = new AllListsPage(driver);
        allListsPage.enterNewListName("New list")
                .clickCreateNewListButton();

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.slava.buylist:id/editText1")));

        ListPage listPage = new ListPage(driver);
        listPage.enterItemName("Milk")
                .enterPrice("1.2")
                .enterAmount("2")
                .chooseUnit("4")
                .chooseCategory("2")
                .clickAddItemButton();
    }

    @Test
    public void editItem() {
        AllListsPage allListsPage = new AllListsPage(driver);
        allListsPage.enterNewListName("New list")
                .clickCreateNewListButton();

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.slava.buylist:id/editText1")));

        ListPage listPage = new ListPage(driver);
        listPage.enterItemName("Milk")
                .enterPrice("1.2")
                .enterAmount("2")
                .chooseUnit("4")
                .chooseCategory("2")
                .clickAddItemButton()
                .longPressOnFirstItem()
                .clickOnEdit()
                .enterComment("3% fat")
                .enterPrice("1.3")
                .saveItem();
    }

    @Test
    public void removeItem() {
        AllListsPage allListsPage = new AllListsPage(driver);
        allListsPage.enterNewListName("New list")
                .clickCreateNewListButton();

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.slava.buylist:id/editText1")));

        ListPage listPage = new ListPage(driver);
        listPage.enterItemName("Milk")
                .enterPrice("1.2")
                .enterAmount("2")
                .chooseUnit("4")
                .chooseCategory("2")
                .clickAddItemButton()
                .longPressOnFirstItem()
                .clickOnRemove().
                confirmRemoval();
    }


}

