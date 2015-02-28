package domain.androidweather;

import android.app.Activity;
import android.widget.TextView;
import android.os.AsyncTask;
import domain.androidweather.weatherService.models.Weather;
import domain.androidweather.weatherService.IWeatherService;
import domain.androidweather.weatherService.OpenWeatherService;

public class TestActivity extends Activity{


    IWeatherService service;
    Weather info;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testactivity);

        TextView textView = (TextView)findViewById(R.id.test);


        class LongAndComplicatedTask extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... params) {


                service = new OpenWeatherService();
                info = service.getCityWeather("Irkutsk,ru");
                String s = info.name + " " + info.sys.country;
                return s;
            }

            @Override
            protected void onPostExecute(String result) {

                textView.setText(result);
            }
        }
        LongAndComplicatedTask longTask = new LongAndComplicatedTask();
        longTask.execute();

    }
}