package domain.androidweather.weatherService;


import domain.androidweather.weatherService.models.Weather;

public interface IWeatherService {

    Weather getCityWeather(String city);

    void setTranslator(ITranslator<Weather> translator);
}
