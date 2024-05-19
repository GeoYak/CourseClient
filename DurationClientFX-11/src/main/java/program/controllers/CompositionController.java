package program.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import program.Main;
import program.models.Composition;
import program.utils.alerts.Alerts;

import program.utils.api.Api;
import program.utils.validation.Validation;

import java.util.List;

/**
 * Класс CompositionController, отвечает за портфели пользователя
 */
public class CompositionController {
    private Main main;
    private Stage stage;
    protected Api api;
    private final ObservableList<Composition> compositionsData = FXCollections.observableArrayList();

    @FXML
    private TableView<Composition> compositionTableView;
    @FXML
    private TableColumn<Composition, String> portfolioNameColumn;
    @FXML
    private TableColumn<Composition, Double> bondShareColumn;
    @FXML
    private TableColumn<Composition, String> bondNameColumn;
    @FXML
    private TextField searchCompany;


    @FXML
    public void initialize() {
        searchCompany.setText(null);

        bondNameColumn.setCellValueFactory(cellData -> cellData.getValue().getBond_name());
        bondShareColumn.setCellValueFactory(cellData -> cellData.getValue().getBond_share().asObject());
        portfolioNameColumn.setCellValueFactory(cellData -> cellData.getValue().getPortfolio_name());

        compositionTableView.setItems(compositionsData);

    }

    /**
     * Кнопка показа портфелей текущего пользователя
     */
    @FXML
    public void handlerCompositionsBtn() {

        if (Validation.checkLength(searchCompany.getText(), 40)) {

            compositionTableView.getItems().clear();
            List<Composition> result;
            String login = api.currentLoginPerson.getLogin();
            if (Validation.isNameBlank(searchCompany.getText())) {
                result = api.getAllCompositions(login);
            } else {

                result = api.getCompositionsBySubName(login, searchCompany.getText());
            }

            compositionsData.addAll(result);
        } else {
            Alerts.showNoValidLength(stage, "поиск", 40);
        }
    }

    /**
     * Кнопка показа окна добавления в портфель облигации
     */
    @FXML
    private void handleAddToPortfolio(){
        main.showAddToPortfolioPage();
    }

    /**
     * Кнопка удаления портфель по названию портфеля
     */
    @FXML
    private void handleDeletePortfolio() {
        boolean result = api.deleteComposition(api.currentLoginPerson.getLogin(), searchCompany.getText());
        if (result) {
            main.showCompositionPage();
        }
    }

    /**
     * Кнопка показа окна расчета дюрации портфеля
     */
    @FXML
    private void handleDuration(){
        main.showDurationPage();
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setApi(Api api) {
        this.api = api;
    }
}
