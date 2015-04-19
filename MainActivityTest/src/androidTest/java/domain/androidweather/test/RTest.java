package domain.androidweather.test;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


@SuppressWarnings("rawtypes")
public class RTest extends ActivityInstrumentationTestCase2 {
  	private Solo solo;
  	
  	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "domain.androidweather.MainActivity";

    private static Class<?> launcherActivityClass;
    static{
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
        }
    }
  	
  	@SuppressWarnings("unchecked")
    public RTest() throws ClassNotFoundException {
        super(launcherActivityClass);
    }

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}
  
	public void testRun() {
        //Wait for activity: 'domain.androidweather.MainActivity'
		solo.waitForActivity("MainActivity", 2000);
        //Click on ImageView
		solo.clickOnView(solo.getView("main_btnSettings"));
        //Wait for activity: 'domain.androidweather.SettingsActivity'
		assertTrue("SettingsActivity is not found!", solo.waitForActivity("SettingsActivity"));
        //Click on иркутск
		solo.clickOnView(solo.getView("settings_town"));
        //Enter the text: 'бодайбо'
		solo.clearEditText((android.widget.EditText) solo.getView("settings_town"));
		solo.enterText((android.widget.EditText) solo.getView("settings_town"), "бодайбо");
        //Click on Сохранить
		solo.clickOnView(solo.getView("saveSettingsButton"));
        //Click on Назад
		solo.clickOnView(solo.getView("returnButton"));
        //Wait for activity: 'domain.androidweather.MainActivity'
		assertTrue("MainActivity is not found!", solo.waitForActivity("MainActivity"));
	}
}
