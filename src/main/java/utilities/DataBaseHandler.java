package utilities;

import IO.ConsoleManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static config.ConfigDataBase.JDBC_DRIVER;

public class DataBaseHandler {
    private String url;
    private String user;
    private String password;
    private Connection connection;

    public DataBaseHandler(String databaseHost, int databasePort, String user, String password, String databaseName) {
        this.url = "jdbc:postrgesql://" + databaseHost + ":" + databasePort + "/" + databaseName;
        this.user = user;
        this.password = password;
        connectToBataBase();
    }

    private void connectToBataBase() {

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e);
            ConsoleManager.printError("Произошла ошибка при подключении к базе данных!");
        } catch (ClassNotFoundException e) {
            ConsoleManager.printError("Database management driver not found((((");
        }
    }


}
