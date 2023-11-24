package core.config;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
    private final String URL;
    private final String USER;
    private final String PASSWORD;

    public DBConfig(String URL, String USER, String PASSWORD) {
        this.URL = URL;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.URL,this.USER,this.PASSWORD);
    }

}
