package domain.androidweather.weather.services;


public interface IWeatherResourceAdjuster<TIn, TOut> {
    void setWeatherImageProvider(IWeatherResourceProvider<TIn, TOut> provider);
}
