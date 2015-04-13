package domain.androidweather.weather.providers;


import android.os.Bundle;

public interface IServiceProvider {

    boolean RunTask(int methodId, Bundle extras);
}
