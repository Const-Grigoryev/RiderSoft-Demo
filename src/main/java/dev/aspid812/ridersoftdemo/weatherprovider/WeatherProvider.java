package dev.aspid812.ridersoftdemo.weatherprovider;

import java.io.IOException;

public interface WeatherProvider {
    Weather getWeather(long timeout) throws WeatherProviderException, IOException, InterruptedException;
}
