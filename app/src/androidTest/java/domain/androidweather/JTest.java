package domain.androidweather;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


@SuppressWarnings("rawtypes")
public class JTest extends ActivityInstrumentationTestCase2 {
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
    public JTest() throws ClassNotFoundException {
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
        /// Открываем MainActivity
        solo.waitForActivity("MainActivity", 2000);
// Нажимаем на кнопку Настройки
        solo.clickOnView(solo.getView("main_btnSettings"));
// Проверяем, что открылось SettingsActivity
        assertTrue("SettingsActivity fail", solo.waitForActivity("SettingsActivity"));
// Ждём немного
        solo.sleep(100);
// Проверяем, что отображено название города
        assertNotEquals("", ((android.widget.EditText) solo.getView("main_textViewCity")).getText());
// Очищаем поле ввода названия города
        solo.clearEditText((android.widget.EditText) solo.getView("settings_town"));
// Выходим из настроек и возвращаемся
        solo.clickOnView(solo.getView("saveSettingsButton"));
        solo.clickOnView(solo.getView("returnButton"));
        assertTrue("MainActivity fail", solo.waitForActivity("MainActivity"), 100);
        solo.clickOnView(solo.getView("main_btnSettings"));
        assertTrue("SettingsActivity fail", solo.waitForActivity("SettingsActivity"));
        solo.sleep(100);
// Проверям, что выбран город по умолчанию
        assertEquals("Москва", ((android.widget.EditText) solo.getView("main_textViewCity")).getText());
    }
}