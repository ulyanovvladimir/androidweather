package domain.androidweather;


import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.TextView;

public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {
    public MainActivityTest(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    private TextView temperature;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent launchIntent = new Intent(getInstrumentation().getTargetContext(), MainActivity.class);
        startActivity(launchIntent, null, null);

        temperature = (TextView)getActivity().findViewById(R.id.main_textViewTemperature);
    }

    @MediumTest
    public void setTemperatureTest() {
        String actual = temperature.getText().toString();
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
        assertNotSame("", "-3°C", actual);
    }
}
