package dev.aspid812.ridersoftdemo.weatherprovider;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.net.http.HttpResponse;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;


public class YandexWeatherProviderTest {

    // Object under test
    private final YandexWeatherProvider yandex = new YandexWeatherProvider();

    @Test
    @Disabled
    public void run_yandex_weather_provider() throws Exception {
        try {
            var filePath = Paths.get("yandex.html");
            filePath = yandex.getMainPage(5000, HttpResponse.BodyHandlers.ofFile(filePath));
            System.out.println(filePath.toAbsolutePath());
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @ParameterizedTest
    @CsvSource({
            "n/a,<p></p>",
            "−4°,<div><div class='weather__temp'>−4°</div></div>",
            "0°,<div><div class='weather__temp'>0°</div></div>",
            "+15°,<div><div class='weather__temp'>+15°</div></div>"
    })
    public void extractTemperature_works(String expectedTemperature, String input) {
        assertEquals(expectedTemperature, YandexWeatherProvider.extractTemperature(input));
    }

    @Test
    public void extractWeather_works() {
        assertEquals(new Weather("451°F"), YandexWeatherProvider.extractWeather("<div class='weather__temp'>451°F</div>"));
    }
}
