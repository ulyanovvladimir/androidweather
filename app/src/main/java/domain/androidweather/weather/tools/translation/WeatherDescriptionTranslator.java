package domain.androidweather.weather.tools.translation;

import android.content.res.Resources;

import java.util.List;

import domain.androidweather.BuildConfig;
import domain.androidweather.weather.models.WeatherCondition;


public class WeatherDescriptionTranslator implements ITranslator<WeatherCondition> {

    private Resources _resources;


    public WeatherDescriptionTranslator(Resources resources) {
        _resources = resources;
    }

    public List<WeatherCondition> translate(List<WeatherCondition> weather) {
        return getWeatherDescriptionTranslation(weather);
    }

    private List<WeatherCondition> getWeatherDescriptionTranslation(List<WeatherCondition> descs) {
        for(WeatherCondition item : descs) {
            item.description = _resources.getString(_resources.getIdentifier("owm_" + item.id, "string", BuildConfig.APPLICATION_ID));
        }
        return descs;
    }
}
