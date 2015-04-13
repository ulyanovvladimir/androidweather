package domain.androidweather.weather.processors;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbContext extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "androidweather.db";


    public DbContext(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append("create table " + WeatherTable.TABLE_NAME + " (");
        sqlQuery.append(WeatherTable.ID + " integer primary key autoincrement,");
        sqlQuery.append(WeatherTable.TYPE + " integer,");
        sqlQuery.append(WeatherTable.WEATHER_OBJECT + " text");
        sqlQuery.append(")");

        db.execSQL(sqlQuery.toString());

        ContentValues cv = new ContentValues();
        cv.put(WeatherTable.TYPE, 1);
        cv.put(WeatherTable.WEATHER_OBJECT, "{}");

        db.insert(WeatherTable.TABLE_NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + WeatherTable.TABLE_NAME);

        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append("create table " + WeatherTable.TABLE_NAME + " (");
        sqlQuery.append(WeatherTable.ID + " integer primary key autoincrement,");
        sqlQuery.append(WeatherTable.TYPE + " integer,");
        sqlQuery.append(WeatherTable.WEATHER_OBJECT + " text");
        sqlQuery.append(")");

        db.execSQL(sqlQuery.toString());

        ContentValues cv = new ContentValues();
        cv.put(WeatherTable.TYPE, 1);
        cv.put(WeatherTable.WEATHER_OBJECT, "{}");

        db.insert(WeatherTable.TABLE_NAME, null, cv);
    }
}
