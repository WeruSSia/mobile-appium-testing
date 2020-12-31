package com.example.project.page_object;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class AllListsPage {

    private AndroidDriver<AndroidElement> driver;

    public AllListsPage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "editText1")
    private AndroidElement newListNameEditText;
    @AndroidFindBy(id = "button2")
    private AndroidElement createListButton;

    public AllListsPage enterNewListName(String newListName) {
        newListNameEditText.sendKeys(newListName);
        return this;
    }

    public AllListsPage clickCreateNewListButton() {
        createListButton.click();
        return this;
    }
}
