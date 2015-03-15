package domain.androidweather.weather.services;


import java.util.List;

public interface ITranslator<T> {
    List<T> translate(List<T> object);
}
