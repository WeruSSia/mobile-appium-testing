package com.example.project.page_object;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class ItemPage {

    private AndroidDriver<AndroidElement> driver;

    public ItemPage(AndroidDriver<AndroidElement> driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
    }

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

    public ItemPage enterPrice(String price) {
        priceEditText.sendKeys(price);
        return this;
    }

    public ItemPage enterAmount(String amount) {
        amountEditText.sendKeys(amount);
        return this;
    }

    public ItemPage chooseUnit(String index) {
        unitSpinner.click();
        unitChoiceFrameLayout.findElementByXPath("//android.widget.CheckedTextView[" + index + "]").click();
        return this;
    }

    public ItemPage chooseCategory(String index) {
        categorySpinner.click();

        categoryChoiceFrameLayout.findElementByXPath("//android.widget.CheckedTextView[" + index + "]").click();
        return this;
    }

    public ItemPage clickAddItemButton() {
        addItemButton.click();
        return this;
    }
}
