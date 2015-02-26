package domain.androidweather.weatherService;


import domain.androidweather.weatherService.models.Weather;
import retrofit.http.GET;
import retrofit.http.Query;

interface IOpenWeatherService {

    @GET("/data/2.5/weather")
    Weather getCityWeather(@Query("q") String town);
}
