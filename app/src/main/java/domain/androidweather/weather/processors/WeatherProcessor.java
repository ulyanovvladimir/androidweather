package domain.androidweather.weather.processors;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.androidweather.weather.models.current.CurrentWeather;
import domain.androidweather.weather.restServices.IWeatherService;
import domain.androidweather.weather.restServices.OpenWeatherService;

public class WeatherProcessor {

    private final DbContext dbContext;
    private final IWeatherService restService;
    private final Gson gson;
    private SharedPreferences preferences;

    public WeatherProcessor(Context context) {
        dbContext = new DbContext(context);
        restService = new OpenWeatherService(context);
        gson = new GsonBuilder().create();
        preferences = context.getSharedPreferences("AndroidWeather", context.MODE_PRIVATE);
    }

    public boolean getCityWeather() {
        Log.v("Processor: ", "START!");
        String city = preferences.getString("City", null);
        CurrentWeather result = restService.getCityWeather(city);

        SQLiteDatabase db = dbContext.getWritableDatabase();

        Log.v("Proccessor", "With db all ok!");

        ContentValues cv = new ContentValues();
        cv.put(WeatherTable.TYPE, 1);
        cv.put(WeatherTable.WEATHER_OBJECT, gson.toJson(result, CurrentWeather.class));

        int returnresult = db.updateWithOnConflict(WeatherTable.TABLE_NAME, cv, WeatherTable.TYPE + " = ?", new String[] {1 + ""},SQLiteDatabase.CONFLICT_FAIL);

        Log.v("WeatherProccessor:result", returnresult + "");

        return returnresult == 1;
    }
}
