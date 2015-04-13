package domain.androidweather.weather.tools.translation;


public interface ITranslatable<T> {
    void setTranslator(ITranslator<T> translator);
}
