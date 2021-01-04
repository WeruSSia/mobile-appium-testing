package com.example.project.page_object;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
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
    @AndroidFindBy(xpath = "//android.widget.ListView/android.widget.RelativeLayout")
    private AndroidElement firstItem;
    @AndroidFindBy(xpath = "//android.widget.ListView/android.widget.LinearLayout[3]")
    private AndroidElement editItemLinearLayout;


    public ListPage enterItemName(String itemName) {
        addItemEditText.sendKeys(itemName);
        return this;
    }

    public ListPage editFirstItem() {
        new TouchAction(driver)
                .longPress(new LongPressOptions()
                        .withElement(ElementOption.element(firstItem))
                        .withDuration(Duration.ofMillis(2000)))
                .release()
                .perform();
        editItemLinearLayout.click();
        return this;
    }

}
