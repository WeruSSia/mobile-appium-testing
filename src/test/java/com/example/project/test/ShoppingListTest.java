package com.example.project.test;

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
import static org.assertj.core.api.InstanceOfAssertFactories.list;

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
    public void testCreateNewList() throws InterruptedException {
        final String listName = "New list";
        AllListsPage allListsPage = new AllListsPage(driver);
        allListsPage.createNewList(listName);

        ListPage listPage = new ListPage(driver);
        listPage.goBackToAllListsPage();

        assertThat(allListsPage.firstListNameTextView.getText()).isEqualTo(listName);
    }

    @Test
    public void testAddItemToList() {
        AllListsPage allListsPage = new AllListsPage(driver);
        allListsPage.createNewList("New list");

        ListPage listPage = new ListPage(driver);
        listPage.waitForListPageToLoad()
                .addItemToList("Milk", "1.2", "2", "4", "2");

        assertThat(listPage.firstItemNameTextView.getText()).isEqualTo("Milk");
    }

    @Test
    public void testEditItem() {
        AllListsPage allListsPage = new AllListsPage(driver);
        allListsPage.createNewList("New list");

        ListPage listPage = new ListPage(driver);
        listPage.waitForListPageToLoad()
                .addItemToList("Milk", "1.2", "2", "4", "2")
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
                .addItemToList("Milk", "1.2", "2", "4", "2")
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
                .addItemToList("Milk", "1.2", "2", "4", "2")
                .addItemToList("Butter", "3", "1", "1", "2");

        driver.hideKeyboard();
        driver.navigate().back();

        assertThat(allListsPage.firstListInformationTextView.getText()).contains("Sum: 5.4 £");
    }

}

