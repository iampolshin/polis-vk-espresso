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
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.not;

import android.view.View;

import androidx.test.espresso.assertion.ViewAssertions;

import org.hamcrest.Matcher;

public class MainPage {
    private static final Matcher<View> INPUT_TEXT_MATCHER = withId(R.id.input_text);
    private static final Matcher<View> ADD_BTN_MATCHER = withId(R.id.add_item);
    private static final Matcher<View> LIST_VIEW_MATCHER = withId(R.id.listView);
    private static final Matcher<View> REMOVE_BTN_MATCHER = withText(R.string.remove_button_text);
    private static final Matcher<View> REMOVE_ALL_BTN_MATCHER = withId(R.id.remove_all_items);
    private static final Matcher<View> EDIT_DIALOG_TITLE_MATCHER = withText(R.string.edit_dialog_title);
    private static final Matcher<View> EDIT_DIALOG_OK_BTN_MATCHER = withText(R.string.edit_dialog_ok_button);

    public MainPage enterNewItemText(String text) {
        onView(INPUT_TEXT_MATCHER).perform(typeText(text), closeSoftKeyboard());
        return this;
    }

    public MainPage clickAddButton() {
        onView(ADD_BTN_MATCHER).perform(click());
        return this;
    }

    public MainPage clickListItem(int position) {
        onData(anything()).inAdapterView(LIST_VIEW_MATCHER).atPosition(position).perform(click());
        return this;
    }

    public MainPage clickRemoveBtn(String text) {
        onView(REMOVE_BTN_MATCHER).perform(click());
        return this;
    }

    public MainPage checkListItem(String text) {
        onView(LIST_VIEW_MATCHER).check(matches(hasDescendant(withText(text))));
        return this;
    }

    public MainPage clickRemoveAllButton() {
        onView(REMOVE_ALL_BTN_MATCHER).perform(click());
        return this;
    }

    public MainPage checkListCount(int count) {
        onView(LIST_VIEW_MATCHER).check(ViewAssertions.matches(hasChildCount(count)));
        return this;
    }

    public MainPage checkEditDialogDisplayed() {
        onView(EDIT_DIALOG_TITLE_MATCHER).inRoot(isDialog()).check(matches(isDisplayed()));
        return this;
    }

    public MainPage enterEditDialogText(String oldText, String newText) {
        onView(withText(oldText)).inRoot(isDialog()).perform(replaceText(newText));
        return this;
    }

    public MainPage clickEditDialogPositiveButton() {
        onView(EDIT_DIALOG_OK_BTN_MATCHER).inRoot(isDialog()).perform(click());
        return this;
    }

    public void checkListDoesNotContain(String text) {
        onView(LIST_VIEW_MATCHER).check(matches(not(hasDescendant(withText(text)))));
    }

    public MainPage clickItemCheckbox(int position) {
        onView(LIST_VIEW_MATCHER).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(LIST_VIEW_MATCHER).atPosition(position)
                .onChildView(withId(R.id.listItemCheckBox)).perform(click());
        return this;
    }

    public MainPage checkItemDone(int position) {
        onView(LIST_VIEW_MATCHER).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(LIST_VIEW_MATCHER).atPosition(position)
                .onChildView(withId(R.id.listItemCheckBox)).check(matches(isChecked()));
        return this;
    }

    public void checkItemNotDone(int position) {
        onView(LIST_VIEW_MATCHER).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(LIST_VIEW_MATCHER).atPosition(position)
                .onChildView(withId(R.id.listItemCheckBox)).check(matches(isNotChecked()));
    }
}

