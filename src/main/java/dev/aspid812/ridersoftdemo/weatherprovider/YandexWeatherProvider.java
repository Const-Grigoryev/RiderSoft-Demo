package dev.aspid812.ridersoftdemo.weatherprovider;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.regex.Pattern;

import static java.net.HttpURLConnection.HTTP_OK;


public class YandexWeatherProvider implements WeatherProvider {

    private static final URI MAIN_PAGE_URI = URI.create("https://yandex.ru");

    private static final Pattern WEATHER_TEMP_PATTERN
            = Pattern.compile("<div class='weather__temp'>(?<temperature>[−+]?\\d+)°</div>");


    static double extractTemperature(String input) {
        var matcher = WEATHER_TEMP_PATTERN.matcher(input);
        if (matcher.find()) {
            var temperature = matcher.group("temperature");

            // Yandex uses nifty minus sign, which Double.parseDouble() can't understand
            temperature = temperature.replace('−', '-');

            return Double.parseDouble(temperature);
        }
        else {
            return Double.NaN;
        }
    }


    static Weather extractWeather(String input) {
        var temperature = extractTemperature(input);
        return new Weather(temperature);
    }


    private final HttpClient client = HttpClient.newHttpClient();


    <T> T getMainPage(long timeout, HttpResponse.BodyHandler<T> responseHandler)
            throws WeatherProviderException, IOException, InterruptedException {

        var request = HttpRequest.newBuilder()
                .uri(MAIN_PAGE_URI)
                .timeout(Duration.ofMillis(timeout))
                .build();

        var response = client.send(request, responseHandler);
        var status = response.statusCode();
        if (status != HTTP_OK) {
            var message = "Weather request to Yandex was failed with status code " + status;
            throw new WeatherProviderException(status, message);
        }

        return response.body();
    }


    String getMainPage(long timeout) throws WeatherProviderException, IOException, InterruptedException {
        return getMainPage(timeout, HttpResponse.BodyHandlers.ofString());
    }


    @Override
    public Weather getWeather(long timeout) throws WeatherProviderException, IOException, InterruptedException {
        var mainPageContent = getMainPage(timeout);
        return extractWeather(mainPageContent);
    }
}
