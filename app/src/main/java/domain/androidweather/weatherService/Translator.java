package domain.androidweather.weatherService;

import android.content.res.Resources;

import java.util.List;

import domain.androidweather.BuildConfig;
import domain.androidweather.weatherService.models.Weather;
import domain.androidweather.weatherService.models.WeatherDesc;


public class Translator implements ITranslator<Weather> {

    private Resources _resources;


    public Translator(Resources resources) {
        _resources = resources;
    }

    public Weather translate(Weather weather) {
        weather.weather = getWeatherDescriptionTranslation(weather.weather);

        return weather;
    }

    private List<WeatherDesc> getWeatherDescriptionTranslation(List<WeatherDesc> descs) {
        for(WeatherDesc item : descs) {
            item.description = _resources.getString(_resources.getIdentifier("owm_" + item.id, "string", BuildConfig.APPLICATION_ID));
        }
        return descs;
    }
}
