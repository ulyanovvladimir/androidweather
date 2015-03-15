package domain.androidweather.weather.models.current;


import java.util.List;

import domain.androidweather.weather.models.Coordinates;
import domain.androidweather.weather.models.Sys;
import domain.androidweather.weather.models.WeatherCondition;

public class CurrentWeather {
    public String name;
    public int cod;
    public Coordinates coord;
    public List<WeatherCondition> weather;
    public Sys sys;
    public Wind wind;
    public AtmosphereCondition main;
}
