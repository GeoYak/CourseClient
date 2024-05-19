package program.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import program.Main;
import program.utils.api.Api;

/**
 * Класс MainLayoutController, отвечает за переключения между сценами
 */
public class MainLayoutController {
    private Main main;
    protected Api api;

    @FXML
    private Label helloMessage;


    /**
     * Открытие сцены информации о портфелях пользователя
     */
    @FXML
    public void openCompositionBoard() {
        main.showCompositionPage();
    }

    /**
     * Открытие сцены информации облигаций
     */
    @FXML
    public void openBondInfo() {
        main.showBondInfoPage();
    }

    /**
     * Открытие сцены ЛК пользователя
     */
    @FXML
    public void openEditPerson() {
        main.showEditPersonPage();
    }

    @FXML
    public void showHelloMessage() {
        helloMessage.setText("Здравствуйте, " + api.currentLoginPerson.getLogin());
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setApi(Api api) {
        this.api = api;
    }
}
