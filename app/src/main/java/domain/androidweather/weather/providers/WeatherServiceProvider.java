package domain.androidweather.weather.providers;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import domain.androidweather.weather.processors.WeatherProcessor;

public class WeatherServiceProvider implements IServiceProvider{

    private final Context context;

    public WeatherServiceProvider(Context context) {
        this.context = context;
    }

    @Override
    public boolean RunTask(int methodId, Bundle extras) {
        Log.v("SERVICEPROVIDER", "RunTask");
        switch (methodId) {
            case Methods.GET_CITY_WEATHER_METHOD:
                return new WeatherProcessor(context).getCityWeather();

            case Methods.GET_CITY_FORECAST_METHOD:
                break;
        }

        return false;
    }

    public static class Methods {
        public static final int GET_CITY_WEATHER_METHOD = 1;
        public static final int GET_CITY_FORECAST_METHOD = 2;
    }
}
