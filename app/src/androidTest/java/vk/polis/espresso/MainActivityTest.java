package vk.polis.espresso;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private MainPage mainPage;
    private View decorView;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        mainPage = new MainPage();
        activityRule.getScenario().onActivity(activity -> decorView = activity.getWindow().getDecorView());
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

    @Test  // Zelenkina Ekaterina test
    public void addItemWithSpacesTest() {
        mainPage.enterNewItemText("   ")
                .clickAddButton()
                .checkListCount(0);
    }

    @Test  // Zelenkina Ekaterina test
    public void testEmptyItemErrorMessage() {
        mainPage.clickAddButton()
                .checkErrorMessage(decorView);
    }

    @Test  // Zelenkina Ekaterina test
    public void testWithSpacesItemErrorMessage() {
        mainPage.enterNewItemText("   ")
                .clickAddButton()
                .checkErrorMessage(decorView);
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
                .clickRemoveBtn()
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
                .enterEditDialogText("Some item", "New item")
                .clickEditDialogOkButton()
                .checkListItem("New item");
    }

    @Test  // Zelenkina Ekaterina test
    public void cancelChangeItemTextTest() {
        mainPage.enterNewItemText("Some item")
                .clickAddButton()
                .clickListItem(0)
                .enterEditDialogText("Some item", "New item")
                .clickEditDialogCancelButton()
                .checkListItem("Some item");
    }

    @Test  // Polshin Vladimir test
    public void checkItemDoneTest() {
        mainPage.enterNewItemText("New item")
                .clickAddButton()
                .checkListItem("New item")
                .clickItemCheckbox(0)
                .checkItemDone(0);
    }

    @Test  // Zelenkina Ekaterina test
    public void checkItemNotDoneTest() {
        int position = 0;
        mainPage.enterNewItemText("New item")
                .clickAddButton()
                .checkListItem("New item")
                .checkItemNotDone(position);
    }

    @Test  // Polshin Vladimir test
    public void checkDoubleClickItemNotDoneTest() {
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
