package program.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import program.Main;
import program.utils.api.Api;

/**
 * Класс AddToPortfolioPageController, отвечает за добавление облигации в портфель
 */
public class AddToPortfolioPageController {
    protected Stage AddToPortfolioStage;
    protected Main main;
    protected Api api;
    @FXML
    TextField bond_nameField;
    @FXML
    TextField portfolio_nameField;
    @FXML
    TextField bond_shareField;


    @FXML
    public void initialize() {
        bond_nameField.setText(null);
        portfolio_nameField.setText(null);
        bond_shareField.setText(null);

    }
    /**
     * Закрытие сцены добавления
     */
    @FXML
    private void handleCancel(){AddToPortfolioStage.close();}

    public void setAddBondStage(Stage AddToPortfolioStage){this.AddToPortfolioStage = AddToPortfolioStage;}

    public void setMain(Main main) {
        this.main = main;
    }

    public void setApi(Api api) {
        this.api = api;
    }


    /**
     * Кнопка добавления облигации в портфель
     */
    @FXML
    private void handleAddToPortfolio() {

        boolean addBondToPortfolioResult = api.addToPortfolio(Double.parseDouble(bond_shareField.getText()),
                bond_nameField.getText(),
                portfolio_nameField.getText());

            if (addBondToPortfolioResult) {
                AddToPortfolioStage.close();
            }
    }
}

