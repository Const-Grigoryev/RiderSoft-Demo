package dev.aspid812.ridersoftdemo;

import dev.aspid812.ridersoftdemo.model.WeatherHistory;
import dev.aspid812.ridersoftdemo.weatherprovider.Weather;
import dev.aspid812.ridersoftdemo.weatherprovider.WeatherProvider;
import dev.aspid812.ridersoftdemo.weatherprovider.YandexWeatherProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.time.LocalDate;


@Controller
public class WeatherController {

    private static final long WEATHER_PROVIDER_TIMEOUT = 5000;

    private final WeatherProvider weatherProvider = new YandexWeatherProvider();

    @Autowired
    private WeatherRepository weatherRepository;

    @GetMapping("/weather")
    @ResponseBody
    public Weather getWeather() throws Exception {
        var today = Date.valueOf(LocalDate.now());
        var weather = weatherRepository.findById(today)
                .orElseGet(WeatherHistory::new);
        if (weather.isInvalid()) {
            var weatherUpdate = weatherProvider.getWeather(WEATHER_PROVIDER_TIMEOUT);
            weather.setWeatherDate(today);
            weather.setWeatherValue(weatherUpdate.getTemperature());
            weatherRepository.save(weather);
        }

        return new Weather(weather.getWeatherValue());
    }
}
