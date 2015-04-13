package domain.androidweather.weather.services;


import android.content.Context;
import android.util.Log;
import domain.androidweather.weather.providers.WeatherServiceProvider;

public class WeatherServiceHelper extends ServiceHelperBase {
    public WeatherServiceHelper(Context context, String resultAction) {
        super(context, ProcessorService.Providers.WEATHER_PROVIDER, resultAction);
    }

    public void getCityWeather() {
        Log.v("Helper!", "getCityWeather!");
        runMethod(WeatherServiceProvider.Methods.GET_CITY_WEATHER_METHOD);
    }
}
