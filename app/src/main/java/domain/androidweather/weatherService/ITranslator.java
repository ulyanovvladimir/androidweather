package domain.androidweather.weatherService;


interface ITranslator<T> {
    T translate(T object);
}
