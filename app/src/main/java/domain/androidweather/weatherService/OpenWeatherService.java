package domain.androidweather.weatherService;


import android.content.Context;

import domain.androidweather.weatherService.models.Weather;
import domain.androidweather.weatherService.models.WeatherDesc;
import retrofit.RestAdapter;

public class OpenWeatherService implements IWeatherService {

    private IOpenWeatherService service;
    private ITranslator<Weather> translator;
    private IWeatherImageResourceProvider resourceProvider;
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
        resourceProvider = new WeatherImageResourceProvider(applicationContext.getResources());
        service = adapter.create(IOpenWeatherService.class);
    }

    @Override
    public Weather getCityWeather(String city) {
        Weather result;
        if(translator == null) result = service.getCityWeather(city, API_UNITS);
        else result = translator.translate(service.getCityWeather(city, API_UNITS));

        for(WeatherDesc item : result.weather) {
            item.weatherImage = resourceProvider.getWeatherResource(item.id);
        }

        return result;
    }

    @Override
    public void setTranslator(ITranslator<Weather> translator) {
        this.translator = translator;
    }

    @Override
    public void setWeatherImageResourceProvider(IWeatherImageResourceProvider provider) {
        this.resourceProvider = provider;
    }
}
