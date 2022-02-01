package dev.aspid812.ridersoftdemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeatherController {

    @GetMapping("/weather")
    @ResponseBody
    public String getWeather() {
        return "Здесь могла быть ваша реклама";
    }
}
