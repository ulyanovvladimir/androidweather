package domain.androidweather.weather.restServices;


import domain.androidweather.weather.models.forecast.Forecast;
import domain.androidweather.weather.models.current.CurrentWeather;
import retrofit.http.GET;
import retrofit.http.Query;

interface IOpenWeatherService {

    @GET("/data/2.5/weather")
    CurrentWeather getCityWeather(@Query("q") String town, @Query("units") String units);

    @GET("/data/2.5/forecast/daily")
    Forecast getCityDailyForecast(@Query("q") String city, @Query("units") String units, @Query("cnt") int daysCount);
}
