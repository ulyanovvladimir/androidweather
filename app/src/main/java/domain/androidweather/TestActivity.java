package domain.androidweather;

import android.app.Activity;
import android.widget.TextView;
import android.os.AsyncTask;
import domain.androidweather.weatherService.models.Weather;
import domain.androidweather.weatherService.IWeatherService;
import domain.androidweather.weatherService.OpenWeatherService;
import domain.androidweather.weatherService.models.WeatherDesc;

import java.lang.Void;

public class TestActivity extends Activity{


    IWeatherService service;
    Weather info;
    ServiceTask serviceTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testactivity);

        TextView textView = (TextView) findViewById(R.id.test);
        TextView textView1 = (TextView) findViewById(R.id.test1);
        TextView textView2 = (TextView) findViewById(R.id.test2);

        Button btn = (Button)findViewById(R.id.btnInfo);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                serviceTask = new ServiceTask();
                serviceTask.execute();
            }
        });
    }


    class ServiceTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... params) {
                service = new OpenWeatherService();
                info = service.getCityWeather("Irkutsk,ru");
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                textView.setText("City " + info.name + "  Country: " + info.sys.country);
                for(WeatherDesc weathers : info.weather){
                    textView1.setText("Description: " + weathers.description + "   Main: " + weathers.main);
                }
                textView2.setText("Wind speed: " + info.wind.speed);
            }
        }



}