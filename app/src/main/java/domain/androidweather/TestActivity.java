package domain.androidweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.AsyncTask;
import domain.androidweather.weatherService.models.Weather;
import domain.androidweather.weatherService.IWeatherService;
import domain.androidweather.weatherService.OpenWeatherService;
import domain.androidweather.weatherService.models.WeatherDesc;

import java.lang.Void;

public class TestActivity extends Activity{

    TextView textView;
    TextView textView1;
    TextView textView2;

    IWeatherService service;
    Weather info;
    ServiceTask serviceTask;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testactivity);

        textView = (TextView) findViewById(R.id.test);
        textView1 = (TextView) findViewById(R.id.test1);
        textView2 = (TextView) findViewById(R.id.test2);

        Button btn = (Button)findViewById(R.id.btnInfo);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                serviceTask = new ServiceTask();
                serviceTask.execute();
            }
        });
        Button btnSettings = (Button)findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }


    class ServiceTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Intent intent = getIntent();
            String city = intent.getStringExtra("Town");
            service = new OpenWeatherService();
            info = service.getCityWeather(city);
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