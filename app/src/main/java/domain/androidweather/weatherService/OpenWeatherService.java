package domain.androidweather.weatherService;


import android.content.Context;

import domain.androidweather.weatherService.models.Weather;
import retrofit.RestAdapter;

public class OpenWeatherService implements IWeatherService {

    private IOpenWeatherService service;
    private ITranslator<Weather> translator;
    private final String API_ENDPOINT = "http://api.openweathermap.org";
    private final String API_UNITS = "metric";

    public OpenWeatherService() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(API_ENDPOINT)
                .build();

        service = adapter.create(IOpenWeatherService.class);
    }

    public OpenWeatherService(Context applicationContext) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(API_ENDPOINT)
                .build();

        translator = new Translator(applicationContext.getResources());
        service = adapter.create(IOpenWeatherService.class);
    }

    @Override
    public Weather getCityWeather(String city) {
        if(translator == null) return service.getCityWeather(city, API_UNITS);
        return translator.translate(service.getCityWeather(city, API_UNITS));
    }

    @Override
    public void setTranslator(ITranslator<Weather> translator) {
        this.translator = translator;
    }
}
