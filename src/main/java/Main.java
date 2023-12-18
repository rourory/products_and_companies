package main.java;

import main.java.service_layer.service.CompanyService;
import main.java.view_layer.form.Application;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("Application has started");
        Application app = new Application();
        System.out.println("Application has shut down");
    }
}