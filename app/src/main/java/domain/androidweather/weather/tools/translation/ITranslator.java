package domain.androidweather.weather.tools.translation;


import java.util.List;

public interface ITranslator<T> {
    List<T> translate(List<T> object);
}
