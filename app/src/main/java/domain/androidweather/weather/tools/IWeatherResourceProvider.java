package domain.androidweather.weather.tools;


public interface IWeatherResourceProvider<TIn, TOut> {
    TOut getResource(TIn code);
}
