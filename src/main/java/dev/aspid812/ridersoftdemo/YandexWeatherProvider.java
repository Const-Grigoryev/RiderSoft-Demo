package dev.aspid812.ridersoftdemo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import static java.net.HttpURLConnection.HTTP_OK;



public class YandexWeatherProvider {

    private final URI MAIN_PAGE_URI = URI.create("https://yandex.ru");

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
}
