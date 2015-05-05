package domain.androidweather.weather.restServices;

import android.content.Context;
import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import domain.androidweather.weather.models.current.CurrentWeather;
import domain.androidweather.weather.models.forecast.Forecast;

/**
 * Created by Irbis on 06.05.2015.
 */
public class VolleyWeatherService implements IWeatherService {

    private final String API_ENDPOINT = "http://api.openweathermap.org";
    private final String API_UNITS = "metric";
    private final String API_CITY_WEATHER_ENDPOINT = "/data/2.5/weather";
    private CurrentWeather weather;
    @Override
    public CurrentWeather getCityWeather(String city) {
        String url = API_ENDPOINT + API_CITY_WEATHER_ENDPOINT + "?q=" + city + "&units=" + API_UNITS;
        StringRequest request = new StringRequest(Request.Method.GET, url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                Gson gson = new GsonBuilder().create();
                weather = gson.fromJson(response,CurrentWeather.class);
                Log.d("Gorod",weather.name);
        }},new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){

            }
        });

        mRequestQueue.add(request);
        return weather;
    }

    @Override
    public Forecast getCityDailyForecast(String city, int daysCount) {
        return null;
    }

    private RequestQueue mRequestQueue;

    public VolleyWeatherService(Context context){
         mRequestQueue = Volley.newRequestQueue(context);
     }

}
