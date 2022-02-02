package dev.aspid812.ridersoftdemo;

import dev.aspid812.ridersoftdemo.model.WeatherHistory;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;


public interface WeatherRepository extends CrudRepository<WeatherHistory, Date> {
}
