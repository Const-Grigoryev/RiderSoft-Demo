package dev.aspid812.ridersoftdemo.weatherprovider;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class WeatherRepositoryTest {

    private static final String REPOSITORY_URL = "jdbc:h2:~/weather";
    private static final String REPOSITORY_USERNAME = "weatherservice";
    private static final String REPOSITORY_PASSWORD = "";

//    static {
//        Class.forName("org.h2.Driver");
//    }

    private static Connection connectWeatherRepository() throws SQLException {
        return DriverManager.getConnection(REPOSITORY_URL, REPOSITORY_USERNAME, REPOSITORY_PASSWORD);
    }


    static class UncheckedSQLException extends RuntimeException {
        private UncheckedSQLException(SQLException ex) {
            super(ex);
        }

        static <T> T guard(Callable<T> callable) {
            try {
                return callable.call();
            }
            catch (SQLException ex) {
                throw new UncheckedSQLException(ex);
            }
            catch (Exception ex) {
                throw new RuntimeException("Not an SQL-related exception", ex);
            }
        }

        @Override
        public synchronized SQLException getCause() {
            return (SQLException) super.getCause();
        }
    }


    @Test
    void generate_dummy_weather_history() throws SQLException {
        try (var conn = connectWeatherRepository()) {
            try (var stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS weather_history(weather_date DATE, weather_value VARCHAR)");
                stmt.execute("INSERT INTO weather_history VALUES ('2022-02-02', '451°F')");
            }
        }
    }


    @Test
    void describe_weather_history() throws SQLException {
        try (var conn = connectWeatherRepository()) {
            try (var stmt = conn.createStatement()) {
                var rs = stmt.executeQuery("SELECT * FROM weather_history");
                var rsm = rs.getMetaData();

                var columns = IntStream.range(1, rsm.getColumnCount() + 1)
                        .mapToObj(col -> UncheckedSQLException.guard(() -> rsm.getColumnName(col)))
                        .collect(Collectors.toUnmodifiableList());

                for (var column : columns) {
                    System.out.println(column);
                }
            }
        }
        catch (UncheckedSQLException ex) {
            throw ex.getCause();
        }
    }


    @Test
    void list_weather_history() throws SQLException {
        try (var conn = connectWeatherRepository();
             var stmt = conn.createStatement()) {

            var rs = stmt.executeQuery("SELECT * FROM weather_history");

            while (rs.next()) {
                System.out.format("%s: %s\n", rs.getDate("weather_date"), rs.getString("weather_value"));
            }
        }
    }
}
