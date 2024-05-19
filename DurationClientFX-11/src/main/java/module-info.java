module DurationClientFX {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires com.google.gson;
    requires lombok;




    opens program.controllers to javafx.fxml;
    exports program.controllers;
    opens program to javafx.fxml;
    exports program;
    opens program.models to javafx.fxml, com.google.gson;
    exports program.models;

}