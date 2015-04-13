package domain.androidweather;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.AsyncTask;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.androidweather.weather.models.current.CurrentWeather;
import domain.androidweather.weather.processors.DbContext;
import domain.androidweather.weather.processors.WeatherTable;
import domain.androidweather.weather.providers.WeatherServiceProvider;
import domain.androidweather.weather.restServices.IWeatherService;
import domain.androidweather.weather.restServices.OpenWeatherService;
import domain.androidweather.weather.models.WeatherCondition;
import domain.androidweather.weather.services.Extras;
import domain.androidweather.weather.services.ProcessorService;
import domain.androidweather.weather.services.WeatherServiceHelper;

import java.lang.Void;

public class MainActivity extends Activity{

    String city;

    private final static String RETURN_ACTION = "domain.androidweather.MainActivity.ActionResult";
    private final IntentFilter intentFilter = new IntentFilter(RETURN_ACTION);
    private WeatherServiceHelper helper;
    private Gson gson;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            boolean success = extras.getBoolean(Extras.RESULT);

            int method = extras.getInt(Extras.METHOD);

            String text;
            if(success) loadWeather(method);
            else text = "FUCK!";

            //Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gson = new GsonBuilder().create();

        city = getSharedPreferences("AndroidWeather",MODE_PRIVATE).getString("City", "Москва");
        ((TextView) findViewById(R.id.main_textViewCity)).setText(city);
        //loadWeather(city);

        ImageButton refreshBtn = (ImageButton)findViewById(R.id.main_btnRefresh);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //loadWeather(city);
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
    public void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, intentFilter);
        helper = new WeatherServiceHelper(this, RETURN_ACTION);

        Log.v("onStart", "START!");

        helper.getCityWeather();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        city = getSharedPreferences("AndroidWeather",MODE_PRIVATE).getString("City", "Москва");
        ((TextView) findViewById(R.id.main_textViewCity)).setText(city);
        //loadWeather(city);
    }

    public void loadWeather(int method) {
        SQLiteDatabase db = new DbContext(this).getReadableDatabase();

        int arg = 0;

        switch (method) {
            case WeatherServiceProvider.Methods.GET_CITY_WEATHER_METHOD:
                arg = 1;
                break;
        }

        Cursor cursor = db.query(WeatherTable.TABLE_NAME, new String[]{WeatherTable.WEATHER_OBJECT}, WeatherTable.TYPE + " = ?", new String[]{arg + ""}, null, null, null);

        cursor.moveToFirst();
        CurrentWeather weather = gson.fromJson(cursor.getString(cursor.getColumnIndex(WeatherTable.WEATHER_OBJECT)), CurrentWeather.class);

        showWeather(weather);
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



