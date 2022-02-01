package dev.aspid812.ridersoftdemo;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.file.Paths;

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
}
