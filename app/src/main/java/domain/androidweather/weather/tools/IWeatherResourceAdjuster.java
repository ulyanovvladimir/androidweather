package domain.androidweather.weather.tools;


public interface IWeatherResourceAdjuster<TIn, TOut> {
    void setWeatherImageProvider(IWeatherResourceProvider<TIn, TOut> provider);
}
