package program.controllers;

import javafx.fxml.FXML;
import program.Main;

/**
 * Данный класс контроллер нужен для управления меню
 */
public class RootLayoutController {

    private Main main;
    /**
     * Кнопка показа "Об авторе"
     */
    @FXML
    private void handleAbout(){
        main.showAboutPage();
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
