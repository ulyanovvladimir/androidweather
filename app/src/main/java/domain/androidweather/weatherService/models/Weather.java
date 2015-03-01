package domain.androidweather.weatherService.models;


import java.util.List;

public class Weather {
    public String name;
    public int cod;
    public Coord coord;
    public List<WeatherDesc> weather;
    public Sys sys;
}
