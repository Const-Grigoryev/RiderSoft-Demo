package dev.aspid812.ridersoftdemo.weatherprovider;

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
            "NaN,<p></p>",
            "-4,<p><div class='weather__temp'>−4°</div></p>",
            "0,<p><div class='weather__temp'>0°</div></p>",
            "15,<p><div class='weather__temp'>+15°</div></p>"
    })
    public void extractTemperature_works(double expectedTemperature, String input) {
        assertEquals(expectedTemperature, YandexWeatherProvider.extractTemperature(input));
        // "Double.NaN,"<p></p>"));
    }

    @Test
    public void extractWeather_works() {
        assertEquals(new Weather(451), YandexWeatherProvider.extractWeather("<p><div class='weather__temp'>451°</div></p>"));
    }
}
