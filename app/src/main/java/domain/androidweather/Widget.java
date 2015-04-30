package domain.androidweather;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.androidweather.weather.models.WeatherCondition;
import domain.androidweather.weather.models.current.CurrentWeather;
import domain.androidweather.weather.processors.DbContext;
import domain.androidweather.weather.processors.WeatherTable;
import domain.androidweather.weather.providers.WeatherServiceProvider;
import domain.androidweather.weather.services.Extras;
import domain.androidweather.weather.services.WeatherServiceHelper;

public class Widget extends AppWidgetProvider {

    private Gson gson = new GsonBuilder().create();


    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.v("Widget", "onEnabled");

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.v("Widget", "onUpdate");

        WeatherServiceHelper helper = new WeatherServiceHelper(context, AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        helper.getCityWeather();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.v("Widget", "onReceive");

        Bundle extras = intent.getExtras();

        if(extras != null) {
            boolean result = extras.getBoolean(Extras.RESULT);

            if(result) loadWeather(extras.getInt(Extras.METHOD), context);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    public void loadWeather(int method, Context context) {
        SQLiteDatabase db = new DbContext(context).getReadableDatabase();

        int arg = 0;

        switch (method) {
            case WeatherServiceProvider.Methods.GET_CITY_WEATHER_METHOD:
                arg = 1;
                break;
        }


        Cursor cursor = db.query(WeatherTable.TABLE_NAME, new String[]{WeatherTable.WEATHER_OBJECT}, WeatherTable.TYPE + " = ?", new String[]{arg + ""}, null, null, null);

        cursor.moveToFirst();
        CurrentWeather weather = gson.fromJson(cursor.getString(cursor.getColumnIndex(WeatherTable.WEATHER_OBJECT)), CurrentWeather.class);

        showWeather(weather, context);
    }

    public void showWeather(CurrentWeather weather, Context context) {
        WeatherCondition currentWeather = weather.weather.get(0);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        remoteViews.setImageViewResource(R.id.widget_weatherImage, currentWeather.weatherImage);
        remoteViews.setTextViewText(R.id.widget_temperature, Math.round(weather.main.temp) + " °C");

        AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, Widget.class), remoteViews);
    }
}
