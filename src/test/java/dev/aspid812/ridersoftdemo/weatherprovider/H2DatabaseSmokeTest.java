package dev.aspid812.ridersoftdemo.weatherprovider;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DatabaseSmokeTest {
    @Test
    void create_H2_database() throws SQLException {
        //Class.forName("org.h2.Driver");
        try (var conn = DriverManager.getConnection("jdbc:h2:~/test", "h2user", "")) {
            try (var stmt = conn.createStatement()) {
                stmt.execute("drop table if exists user");
                stmt.execute("create table user(id int primary key, name varchar(100))");
                stmt.execute("insert into user values(1, 'hello')");
                stmt.execute("insert into user values(2, 'world')");
            }

            try (var stmt = conn.createStatement()) {
                var rs = stmt.executeQuery("select * from user");
                while (rs.next()) {
                    System.out.println("id " + rs.getInt("id") + " name " + rs.getString("name"));
                }
            }
        }
    }
}
