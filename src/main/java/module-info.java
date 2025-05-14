module fr.triobin.workshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires jdk.httpserver;
    requires javafx.base;

    opens fr.triobin.workshop to javafx.fxml;
    exports fr.triobin.workshop;
}
