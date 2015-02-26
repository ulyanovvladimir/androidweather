package domain.androidweather.weatherService;


import domain.androidweather.weatherService.models.Weather;
import retrofit.RestAdapter;

public class OpenWeatherService implements IWeatherService {

    private IOpenWeatherService service;
    private String API_ENDPOINT = "http://api.openweathermap.org";

    public OpenWeatherService() {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(API_ENDPOINT).build();

        service = adapter.create(IOpenWeatherService.class);
    }

    @Override
    public Weather getCityWeather(String city) {
        return service.getCityWeather(city);
    }
}
