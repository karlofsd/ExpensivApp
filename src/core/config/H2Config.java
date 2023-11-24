package core.config;

public class H2Config extends DBConfig{
    private static final String URL = "jdbc:h2:~/Development/expense-app";
    private static final String USER = "admin";
    private static final String PASSWORD = "1234";
    public H2Config() {
        super(URL,USER,PASSWORD);
    }
}
