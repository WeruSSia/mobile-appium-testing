package com.example.project.test;

import com.example.project.item.Item;
import com.example.project.page_object.AllListsPage;
import com.example.project.page_object.ListPage;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ShoppingListTest extends BaseTest {

    @Test
    public void testCreateNewList() {
        final String listName = "New list";
        AllListsPage allListsPage = new AllListsPage(driver);
        allListsPage.createNewList(listName);

        ListPage listPage = new ListPage(driver);
        listPage.waitForListPageToLoad(wait);

        driver.navigate().back();

        allListsPage.waitForAllListsPageToLoad(wait);

        assertThat(allListsPage.firstListNameTextView.getText()).isEqualTo(listName);
    }

    @Test
    public void testAddItemToList() {
        AllListsPage allListsPage = new AllListsPage(driver);
        allListsPage.createNewList("New list");

        ListPage listPage = new ListPage(driver);
        listPage.waitForListPageToLoad(wait)
                .addItemToList(Item.builder().name("Milk").price(1.2).amount(2).unitIndex(4).categoryIndex(2).build(), wait);

        assertThat(listPage.firstItemNameTextView.getText()).isEqualTo("Milk");
    }

    @Test
    public void testEditItem() {
        AllListsPage allListsPage = new AllListsPage(driver);
        allListsPage.createNewList("New list");

        ListPage listPage = new ListPage(driver);
        listPage.waitForListPageToLoad(wait)
                .addItemToList(Item.builder().name("Milk").price(1.2).amount(2).unitIndex(4).categoryIndex(2).build(), wait)
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
        listPage.waitForListPageToLoad(wait)
                .addItemToList(Item.builder().name("Milk").price(1.2).amount(2).unitIndex(4).categoryIndex(2).build(), wait)
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
        listPage.waitForListPageToLoad(wait)
                .addItemToList(Item.builder().name("Milk").price(1.2).amount(2).unitIndex(4).categoryIndex(2).build(), wait)
                .addItemToList(Item.builder().name("Butter").price(3.0).amount(1).unitIndex(1).categoryIndex(2).build(), wait);

        driver.navigate().back();

        assertThat(allListsPage.firstListInformationTextView.getText()).contains("Sum: 5.4 £");
    }

}

