package vk.polis.espresso;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private MainPage mainPage;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        mainPage = new MainPage();
    }

    @Test
    public void addItemTest() {
        mainPage.enterNewItemText("New item")
                .clickAddButton()
                .checkListItem("New item");
    }

    @Test
    public void addItemWithEmptyTextTest() {
        mainPage.clickAddButton()
                .checkListCount(0);
    }

    @Test
    public void addMultipleItemsTest() {
        mainPage.enterNewItemText("Item 1")
                .clickAddButton()
                .enterNewItemText("Item 2")
                .clickAddButton()
                .enterNewItemText("Item 3")
                .clickAddButton()
                .checkListCount(3);
    }

    @Test
    public void removeBtnTest() {
        String itemText = "Item with remove button";
        mainPage.enterNewItemText(itemText)
                .clickAddButton()
                .checkListItem(itemText)
                .clickRemoveBtn(itemText)
                .checkListDoesNotContain(itemText);
    }

    @Test
    public void removeAllBtnTest() {
        mainPage.enterNewItemText("Item 1")
                .clickAddButton()
                .enterNewItemText("Item 2")
                .clickAddButton()
                .enterNewItemText("Item 3")
                .clickAddButton()
                .checkListCount(3)
                .clickRemoveAllButton()
                .checkListCount(0);
    }

    @Test
    public void changeItemTextTest() {
        mainPage.enterNewItemText("Some item")
                .clickAddButton()
                .clickListItem(0)
                .checkEditDialogDisplayed()
                .enterEditDialogText("Some item", "New item")
                .clickEditDialogPositiveButton()
                .checkListItem("New item");
    }

    @Test
    public void checkItemDoneTest() {
        mainPage.enterNewItemText("New item")
                .clickAddButton()
                .checkListItem("New item")
                .clickItemCheckbox(0)
                .checkItemDone(0);
    }

    @Test
    public void checkItemNotDoneTest() {
        int position = 0;
        mainPage.enterNewItemText("New item")
                .clickAddButton()
                .checkListItem("New item")
                .clickItemCheckbox(position)
                .checkItemDone(position)
                .clickItemCheckbox(position)
                .checkItemNotDone(position);
    }
}
