package domain.androidweather.weather.services;


public interface IWeatherResourceProvider<TIn, TOut> {
    TOut getResource(TIn code);
}
