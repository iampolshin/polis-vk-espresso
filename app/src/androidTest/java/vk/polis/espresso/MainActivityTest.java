package vk.polis.espresso;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.not;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void addItemTest() {
        onView(withId(R.id.inputText)).perform(typeText("New item"), closeSoftKeyboard());
        onView(withId(R.id.addTODO)).perform(click());
        onView(withId(R.id.listView)).check(matches(hasDescendant(withText("New item"))));
    }

    @Test
    public void addItemWithEmptyTextTest() {
        onView(withId(R.id.addTODO)).perform(click());
        onView(withId(R.id.listView)).check(matches(hasChildCount(0)));
    }

    @Test
    public void addMultipleItemsTest() {
        onView(withId(R.id.inputText)).perform(typeText("Item 1"), closeSoftKeyboard());
        onView(withId(R.id.addTODO)).perform(click());
        onView(withId(R.id.inputText)).perform(typeText("Item 2"), closeSoftKeyboard());
        onView(withId(R.id.addTODO)).perform(click());
        onView(withId(R.id.inputText)).perform(typeText("Item 3"), closeSoftKeyboard());
        onView(withId(R.id.addTODO)).perform(click());
        onView(withId(R.id.listView)).check(matches(hasChildCount(3)));
    }

    @Test
    public void removeBtnTest() {
        onView(withId(R.id.inputText)).perform(typeText("Item with delete button"), closeSoftKeyboard());
        onView(withId(R.id.addTODO)).perform(click());
        onView(withText("Item with Delete button")).check(matches(isDisplayed()));
        onView(withText(R.string.delete_button_text)).perform(click());
        onView(withId(R.id.listView)).check(matches(not(hasDescendant(withText("Item with delete button")))));
    }

    @Test
    public void removeAllBtnTest() {
        onView(withId(R.id.inputText)).perform(typeText("Item 1"), closeSoftKeyboard());
        onView(withId(R.id.addTODO)).perform(click());
        onView(withId(R.id.inputText)).perform(typeText("Item 2"), closeSoftKeyboard());
        onView(withId(R.id.addTODO)).perform(click());
        onView(withId(R.id.inputText)).perform(typeText("Item 3"), closeSoftKeyboard());
        onView(withId(R.id.addTODO)).perform(click());
        onView(withId(R.id.listView)).check(matches(hasChildCount(3)));
        onView(withId(R.id.removeAllTODO)).perform(click());
        onView(withId(R.id.listView)).check(matches(hasChildCount(0)));
    }

    @Test
    public void changeItemTextTest() {
        onView(withId(R.id.inputText)).perform(typeText("Some item"), closeSoftKeyboard());
        onView(withId(R.id.addTODO)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
        onView(withText(R.string.edit_dialog_title)).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Some item")).inRoot(isDialog()).perform(replaceText("New Text"));
        onView(withText(R.string.edit_dialog_positive_button)).inRoot(isDialog()).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0)
                .onChildView(withText("New Text")).check(matches(isDisplayed()));
    }
}
