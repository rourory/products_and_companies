package main.java.data_access_layer.connection;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
 * Этот класс отвечает за подключение к базе данных.
 * Для его работы необходимо скофигурировать variables.properties в директории resources
 */
public final class ConnectionManager {

    //Метод создания поключения к базе данных
    public static Connection getConnection() {
        Connection connection;
        try {
            /*
             * Следующие две строчки читают проперти из файла variables.properties
             * Такой подход считается best practices
             * Он позволяет многократно использовать переменные, содержание данные о БД
             * Bad practices считается "хардкодить" такие данные
             */
            Properties properties = new Properties();
            properties.load(new FileReader("src/main/resources/variables.properties"));
            try {
                Class.forName(properties.getProperty("driver"));
                try {
                    connection = DriverManager.getConnection(
                            properties.getProperty("database"),
                            properties.getProperty("user"),
                            properties.getProperty("password"));
                } catch (SQLException ex) {
                    System.out.println("Failed to create the database connection");
                    throw new RuntimeException(ex);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Driver wasn't found");
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Unable to find properties file");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Properties file was found, but wasn't loaded");
            throw new RuntimeException(e);
        }
        return connection;
    }
}
