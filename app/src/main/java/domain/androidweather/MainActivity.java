package domain.androidweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.AsyncTask;
import domain.androidweather.weather.models.current.CurrentWeather;
import domain.androidweather.weather.services.IWeatherService;
import domain.androidweather.weather.services.OpenWeatherService;
import domain.androidweather.weather.models.WeatherCondition;

import java.lang.Void;

public class MainActivity extends Activity{

    String city;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = getSharedPreferences("AndroidWeather",MODE_PRIVATE).getString("City", "Москва");
        ((TextView) findViewById(R.id.main_textViewCity)).setText(city);
        loadWeather(city);

        ImageButton refreshBtn = (ImageButton)findViewById(R.id.main_btnRefresh);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadWeather(city);
            }
        });

        ImageView btnSettings = (ImageView)findViewById(R.id.main_btnSettings);
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
        super.onResume();
        city = getSharedPreferences("AndroidWeather",MODE_PRIVATE).getString("City", "Москва");
        ((TextView) findViewById(R.id.main_textViewCity)).setText(city);
        loadWeather(city);
    }

    public void loadWeather(String city) {
        AsyncTask<String, Void, CurrentWeather> task = new WeatherServiceTask();
        task.execute(city);
    }

    public void showWeather(CurrentWeather weather) {
        WeatherCondition currentWeather = weather.weather.get(0);
        ((ImageView) findViewById(R.id.main_weatherImage)).setImageResource(currentWeather.weatherImage);
        ((TextView) findViewById(R.id.main_textViewTemperature)).setText((Math.round(weather.main.temp)) + "°C");
        ((TextView) findViewById(R.id.main_textViewWeatherDescription)).setText(currentWeather.description);
        ((TextView) findViewById(R.id.main_textViewWind)).setText(weather.wind.speed+"м/с");
    }


    class WeatherServiceTask extends AsyncTask<String, Void, CurrentWeather> {

        private IWeatherService service;

        @Override
        protected void onPreExecute() {
            service = new OpenWeatherService(getApplicationContext());
        }

        @Override
        protected CurrentWeather doInBackground(String... params) {
            return service.getCityWeather(params[0]);
        }

        @Override
        protected void onPostExecute(CurrentWeather result) {
            showWeather(result);
        }
    }
}



