package dev.aspid812.ridersoftdemo;

import dev.aspid812.ridersoftdemo.weatherprovider.Weather;
import dev.aspid812.ridersoftdemo.weatherprovider.WeatherProvider;
import dev.aspid812.ridersoftdemo.weatherprovider.YandexWeatherProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeatherController {

    private static final long WEATHER_PROVIDER_TIMEOUT = 5000;

    private final WeatherProvider weatherProvider = new YandexWeatherProvider();

    @GetMapping("/weather")
    @ResponseBody
    public Weather getWeather() throws Exception {
        var weather = weatherProvider.getWeather(WEATHER_PROVIDER_TIMEOUT);
        return weather;
    }
}
