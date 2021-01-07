package com.example.project.page_object;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

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
    @AndroidFindBy(id = "title")
    public AndroidElement firstListNameTextView;
    @AndroidFindBy(id = "str1")
    public AndroidElement firstListInformationTextView;

    public AllListsPage enterNewListName(String newListName) {
        newListNameEditText.sendKeys(newListName);
        return this;
    }

    public AllListsPage clickCreateNewListButton() {
        createListButton.click();
        return this;
    }

    public AllListsPage createNewList(String listName) {
        return this.enterNewListName(listName).clickCreateNewListButton();
    }

    public AllListsPage waitForAllListsPageToLoad(Wait wait){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title")));
        return this;
    }
}
