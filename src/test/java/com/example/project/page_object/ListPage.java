package com.example.project.page_object;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class ListPage {

    private AndroidDriver<AndroidElement> driver;

    public ListPage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "com.slava.buylist:id/editText1")
    private AndroidElement addItemEditText;
    @AndroidFindBy(id = "com.slava.buylist:id/editText2")
    private AndroidElement priceEditText;
    @AndroidFindBy(id = "com.slava.buylist:id/editText3")
    private AndroidElement amountEditText;
    @AndroidFindBy(id = "com.slava.buylist:id/spinner1")
    private AndroidElement unitSpinner;
    @AndroidFindBy(xpath = "//android.widget.FrameLayout")
    private AndroidElement unitChoiceFrameLayout;
    @AndroidFindBy(id = "com.slava.buylist:id/spinner2")
    private AndroidElement categorySpinner;
    @AndroidFindBy(id = "com.slava.buylist:id/button2")
    private AndroidElement addItemButton;
    @AndroidFindBy(xpath = "//android.widget.FrameLayout")
    private AndroidElement categoryChoiceFrameLayout;
    @AndroidFindBy(xpath = "//android.widget.RelativeLayout[1]//android.widget.RelativeLayout")
    private AndroidElement firstItem;


    public ListPage enterItemName(String itemName) {
        addItemEditText.sendKeys(itemName);
        return this;
    }

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

    public ListPage editFirstItem() {
        new TouchAction(driver)
                .longPress(new LongPressOptions()
                        .withElement(ElementOption.element(firstItem))
                        .withDuration(Duration.ofMillis(3000)))
                .release()
                .perform();
        return this;
    }

}
