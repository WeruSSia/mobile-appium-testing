package com.example.project.test;

import com.example.project.item.Item;
import com.example.project.page_object.AllListsPage;
import com.example.project.page_object.ListPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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

        cap.setCapability("unicodeKeyboard", true);
        cap.setCapability("resetKeyboard", true);

        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AndroidDriver<>(url, cap);

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @BeforeMethod
    public void resetApp() {
        driver.resetApp();
        driver.findElementById("com.android.permissioncontroller:id/continue_button").click();
        driver.findElementById("android:id/button1").click();
    }

    @Test
    public void testCreateNewList() {
        final String listName = "New list";
        AllListsPage allListsPage = new AllListsPage(driver);
        allListsPage.createNewList(listName);

        driver.navigate().back();

        assertThat(allListsPage.firstListNameTextView.getText()).isEqualTo(listName);
    }

    @Test
    public void testAddItemToList() {
        AllListsPage allListsPage = new AllListsPage(driver);
        allListsPage.createNewList("New list");

        ListPage listPage = new ListPage(driver);
        listPage.waitForListPageToLoad()
                .addItemToList(Item.builder().name("Milk").price(1.2).amount(2).unitIndex(4).categoryIndex(2).build());

        assertThat(listPage.firstItemNameTextView.getText()).isEqualTo("Milk");
    }

    @Test
    public void testEditItem() {
        AllListsPage allListsPage = new AllListsPage(driver);
        allListsPage.createNewList("New list");

        ListPage listPage = new ListPage(driver);
        listPage.waitForListPageToLoad()
                .addItemToList(Item.builder().name("Milk").price(1.2).amount(2).unitIndex(4).categoryIndex(2).build())
                .longPressOnFirstItem()
                .clickOnEdit()
                .enterComment("3% fat")
                .enterPrice("1.3")
                .saveItem();

        assertThat(listPage.firstItemPriceTextView.getText()).isEqualTo("1.3 £");
        assertThat(listPage.firstItemCommentTextView.getText()).isEqualTo("3% fat");
    }

    @Test
    public void testRemoveItem() {
        AllListsPage allListsPage = new AllListsPage(driver);
        allListsPage.createNewList("New list");

        ListPage listPage = new ListPage(driver);
        listPage.waitForListPageToLoad()
                .addItemToList(Item.builder().name("Milk").price(1.2).amount(2).unitIndex(4).categoryIndex(2).build())
                .longPressOnFirstItem()
                .clickOnRemove().
                confirmRemoval();

        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> driver.findElementByXPath("//android.widget.ListView/android.widget.RelativeLayout"));
    }

    @Test
    public void testCheckTotal() {
        AllListsPage allListsPage = new AllListsPage(driver);
        allListsPage.createNewList("New list");

        ListPage listPage = new ListPage(driver);
        listPage.waitForListPageToLoad()
                .addItemToList(Item.builder().name("Milk").price(1.2).amount(2).unitIndex(4).categoryIndex(2).build())
                .addItemToList(Item.builder().name("Butter").price(3).amount(1).unitIndex(1).categoryIndex(2).build());

        driver.hideKeyboard();
        driver.navigate().back();

        assertThat(allListsPage.firstListInformationTextView.getText()).contains("Sum: 5.4 £");
    }

}

