package domain.androidweather.weather.services;


import domain.androidweather.weather.services.ITranslator;

public interface ITranslatable<T> {
    void setTranslator(ITranslator<T> translator);
}
