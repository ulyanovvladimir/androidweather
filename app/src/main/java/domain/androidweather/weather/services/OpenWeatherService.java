package domain.androidweather.weather.services;


import android.content.Context;
import domain.androidweather.weather.models.WeatherCondition;
import domain.androidweather.weather.models.forecast.DailyWeather;
import domain.androidweather.weather.models.forecast.Forecast;
import domain.androidweather.weather.models.current.CurrentWeather;
import retrofit.RestAdapter;

public class OpenWeatherService implements IWeatherService, ITranslatable<WeatherCondition>, IWeatherResourceAdjuster<WeatherCondition, Integer> {

    private IOpenWeatherService service;
    private ITranslator<WeatherCondition> translator;
    private IWeatherResourceProvider<WeatherCondition, Integer> resourceProvider;
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

        translator = new WeatherDescriptionTranslator(applicationContext.getResources());
        resourceProvider = new OWMImageResProvider(applicationContext.getResources());
        service = adapter.create(IOpenWeatherService.class);
    }

    @Override
    public CurrentWeather getCityWeather(String city) {
        CurrentWeather result = service.getCityWeather(city, API_UNITS);
        if(translator != null) result.weather = translator.translate(result.weather);

        for(WeatherCondition item : result.weather) {
            item.weatherImage = resourceProvider.getResource(item);
        }

        return result;
    }

    @Override
    public Forecast getCityDailyForecast(String city, int daysCount) {
        Forecast result = service.getCityDailyForecast(city, API_UNITS, daysCount);

        for(DailyWeather item : result.list) {

            if(translator != null) item.weather = translator.translate(item.weather);

            for (WeatherCondition desc : item.weather) {
                desc.weatherImage = resourceProvider.getResource(desc);
            }
        }

        return result;
    }

    @Override
    public void setTranslator(ITranslator<WeatherCondition> translator) {
        this.translator = translator;
    }

    @Override
    public void setWeatherImageProvider(IWeatherResourceProvider<WeatherCondition, Integer> provider) {
        this.resourceProvider = provider;
    }
}
