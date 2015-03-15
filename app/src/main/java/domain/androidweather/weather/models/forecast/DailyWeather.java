package domain.androidweather.weather.models.forecast;

import java.util.List;

import domain.androidweather.weather.models.WeatherCondition;

public class DailyWeather {
    public DailyTemperature temp;
    public List<WeatherCondition> weather;
}
