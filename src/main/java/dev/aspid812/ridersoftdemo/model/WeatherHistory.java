package dev.aspid812.ridersoftdemo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;


@Data
@Entity
public class WeatherHistory {
    @Id
    Date weatherDate;

    String weatherValue;

    public boolean isInvalid() {
        return weatherDate == null;
    }
}
