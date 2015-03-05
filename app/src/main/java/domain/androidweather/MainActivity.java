package domain.androidweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.AsyncTask;
import domain.androidweather.weatherService.models.Weather;
import domain.androidweather.weatherService.IWeatherService;
import domain.androidweather.weatherService.OpenWeatherService;
import domain.androidweather.weatherService.models.WeatherDesc;

import java.lang.Void;

public class MainActivity extends Activity{

    TextView textView;
    TextView textView1;
    TextView textView2;

    IWeatherService service;
    Weather info;

    String city;

    /*enum Sky{
        Clouds;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.test);
        textView1 = (TextView) findViewById(R.id.test1);
        textView2 = (TextView) findViewById(R.id.test2);

        city = getSharedPreferences("AndroidWeather",MODE_PRIVATE).getString("City", "Москва");

        loadWeather(city);

        Button btn = (Button)findViewById(R.id.btnInfo);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                loadWeather(city);
            }
        });

        Button btnSettings = (Button)findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        city = getSharedPreferences("AndroidWeather",MODE_PRIVATE).getString("City", "Москва");
        loadWeather(city);
    }

    public void loadWeather(String city) {
        AsyncTask<String, Void, Weather> task = new WeatherServiceTask();
        task.execute(city);
    }


    class WeatherServiceTask extends AsyncTask<String, Void, Weather> {

        private IWeatherService service;

        @Override
        protected void onPreExecute() {
            service = new OpenWeatherService(getApplicationContext());
        }

        @Override
        protected Weather doInBackground(String... params) {
            return service.getCityWeather(params[0]);
        }

        @Override
        protected void onPostExecute(Weather result) {
            textView.setText("City " + result.name);
            for(WeatherDesc item : result.weather) {
                textView1.setText(item.description);
            }

            textView2.setText("Wind speed: " + result.wind.speed + " m/s");
        }
    }


    /*class ServiceTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... params) {
                Intent intent = getIntent();
                String city = intent.getStringExtra("Town");
                service = new OpenWeatherService(getApplicationContext());
                info = service.getCityWeather(city);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                String test = info.name;
                textView.setText("City " + test + "  Country: " + info.sys.country);
                //Dictionary dictionary = new Dictionary();
                for (WeatherDesc weathers : info.weather) {
                    /*String desc = weathers.description;
                    String mai = weathers.main;
                    dictionary.Cloud(desc,mai);
                    textView1.setText(weathers.description);
                }

                textView2.setText("Wind speed: " + info.wind.speed);
                }

            }*/
        }



