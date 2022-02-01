package dev.aspid812.ridersoftdemo.weatherprovider;

public class WeatherProviderException extends Exception {
    int statusCode;

    public WeatherProviderException(int statusCode) {
        this.statusCode = statusCode;
    }

    public WeatherProviderException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
