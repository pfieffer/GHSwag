package np.com.ravi.ghswag.activity;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import np.com.ravi.ghswag.R;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by ravi on 1/18/18.
 */
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> mainActivityActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private  String userNameToBeTyped;

    @Before
    public void setUp() throws Exception {
        //the string to be typed
        userNameToBeTyped = "pfieffer";
    }

    @Test
    public  void testUserInputScenario(){
        //input user name
        Espresso.onView(withId(R.id.edit_text_username)).perform(typeText(userNameToBeTyped));
        // close soft keyboard
        Espresso.closeSoftKeyboard();
        //click swag button
        Espresso.onView(withId(R.id.btn_show_swag)).perform(click());
        //check if validation passed
    }

    @After
    public void tearDown() throws Exception {
    }

}