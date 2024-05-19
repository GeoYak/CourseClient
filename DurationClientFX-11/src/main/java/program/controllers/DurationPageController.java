package program.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import program.models.Composition;
import program.utils.alerts.Alerts;
import program.utils.api.Api;
import program.utils.validation.Validation;

import java.util.List;

/**
 * Класс DurationPageController, отвечает за расчет дюрации портфеля
 */
public class DurationPageController {
    private Stage stage;
    protected Api api;


    @FXML
    private TextField durationCompany;

    @FXML
    private TextField searchCompany;


    @FXML
    public void initialize() {
        searchCompany.setText(null);
        durationCompany.setText(null);

    }

    /**
     * Кнопка расчета дюрации
     */
    @FXML
    public void handleCalculationBtn() {
            double result;

            String login = api.currentLoginPerson.getLogin();
            result = api.getDurationBySubName(login, searchCompany.getText());

            durationCompany.setText(String.valueOf(result));
    }

    /**
     * закрытие окна
     */
    @FXML
    public void handleBackBtn() {
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setApi(Api api) {
        this.api = api;
    }

}
