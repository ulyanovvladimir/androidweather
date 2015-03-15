package domain.androidweather.weather.services;


import domain.androidweather.weather.models.forecast.Forecast;
import domain.androidweather.weather.models.current.CurrentWeather;

public interface IWeatherService {

    CurrentWeather getCityWeather(String city);
    Forecast getCityDailyForecast(String city, int daysCount);
}
