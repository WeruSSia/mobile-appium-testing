package com.example.project.page_object;

import com.example.project.item.Item;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ListPage {

    private AndroidDriver<AndroidElement> driver;

    public ListPage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "editText1")
    private AndroidElement addItemEditText;
    @AndroidFindBy(xpath = "//android.widget.ListView/android.widget.RelativeLayout")
    private AndroidElement firstItemRelativeLayout;
    @AndroidFindBy(xpath = "//android.widget.ListView/android.widget.RelativeLayout//android.widget.TextView[1]")
    public AndroidElement firstItemNameTextView;
    @AndroidFindBy(xpath = "//android.widget.ListView/android.widget.RelativeLayout//android.widget.TextView[4]")
    public AndroidElement firstItemPriceTextView;
    @AndroidFindBy(xpath = "//android.widget.ListView/android.widget.RelativeLayout//android.widget.TextView[2]")
    public AndroidElement firstItemCommentTextView;
    @AndroidFindBy(xpath = "//android.widget.ListView/android.widget.LinearLayout[3]")
    private AndroidElement editItemLinearLayout;
    @AndroidFindBy(id = "editText2")
    private AndroidElement priceEditText;
    @AndroidFindBy(id = "editText3")
    private AndroidElement amountEditText;
    @AndroidFindBy(id = "spinner1")
    private AndroidElement unitSpinner;
    @AndroidFindBy(xpath = "//android.widget.FrameLayout")
    private AndroidElement unitChoiceFrameLayout;
    @AndroidFindBy(id = "spinner2")
    private AndroidElement categorySpinner;
    @AndroidFindBy(id = "button2")
    private AndroidElement addItemButton;
    @AndroidFindBy(xpath = "//android.widget.FrameLayout")
    private AndroidElement categoryChoiceFrameLayout;
    @AndroidFindBy(id = "editText4")
    private AndroidElement commentEditText;
    @AndroidFindBy(id = "button2")
    private AndroidElement saveItemButton;
    @AndroidFindBy(xpath = "//android.widget.ListView/android.widget.LinearLayout[4]")
    private AndroidElement removeItemLinearLayout;
    @AndroidFindBy(id = "android:id/button1")
    private AndroidElement confirmRemovalButton;

    public ListPage enterPrice(String price) {
        priceEditText.sendKeys(price);
        return this;
    }

    public ListPage enterAmount(String amount) {
        amountEditText.sendKeys(amount);
        return this;
    }

    public ListPage chooseUnit(String index) {
        unitSpinner.click();
        unitChoiceFrameLayout.findElementByXPath("//android.widget.CheckedTextView[" + index + "]").click();
        return this;
    }

    public ListPage chooseCategory(String index) {
        categorySpinner.click();

        categoryChoiceFrameLayout.findElementByXPath("//android.widget.CheckedTextView[" + index + "]").click();
        return this;
    }

    public ListPage clickAddItemButton() {
        addItemButton.click();
        return this;
    }


    public ListPage enterItemName(String itemName) {
        addItemEditText.sendKeys(itemName);
        return this;
    }

    public ListPage longPressOnFirstItem() {
        new TouchAction(driver)
                .longPress(new LongPressOptions()
                        .withElement(ElementOption.element(firstItemRelativeLayout))
                        .withDuration(Duration.ofMillis(2000)))
                .release()
                .perform();
        return this;
    }

    public ListPage clickOnEdit() {
        editItemLinearLayout.click();
        return this;
    }

    public ListPage enterComment(String comment) {
        commentEditText.sendKeys(comment);
        return this;
    }

    public ListPage saveItem() {
        saveItemButton.click();
        return this;
    }

    public ListPage clickOnRemove() {
        removeItemLinearLayout.click();
        return this;
    }

    public ListPage confirmRemoval() {
        confirmRemovalButton.click();
        return this;
    }

    public ListPage addItemToList(Item item) {
        return this
                .enterItemName(item.getName())
                .enterPrice(String.valueOf(item.getPrice()))
                .enterAmount(String.valueOf(item.getAmount()))
                .chooseUnit(String.valueOf(item.getUnitIndex()))
                .chooseCategory(String.valueOf(item.getCategoryIndex()))
                .clickAddItemButton();
    }

    public ListPage waitForListPageToLoad() {
        WebDriverWait wait = new WebDriverWait(this.driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editText1")));
        return this;
    }

}
