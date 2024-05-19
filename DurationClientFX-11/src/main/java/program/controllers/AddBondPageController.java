package program.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import program.Main;
import program.utils.alerts.Alerts;
import program.utils.api.Api;

import java.time.LocalDate;

/**
 * Класс AddBondPageController, отвечает за добавление облигации
 */
public class AddBondPageController {
    protected Stage AddBondStage;
    protected Main main;
    protected Api api;
    @FXML
    TextField bond_nameField;
    @FXML
    TextField couponPaymentDateField;
    @FXML
    TextField nominalField;
    @FXML
    TextField coupon_sizeField;
    @FXML
    TextField number_of_coupon_periodsField;
    @FXML
    TextField bond_durationField;

    @FXML
    TextField maturityField;


    @FXML
    public void initialize() {
        bond_nameField.setText(null);
        couponPaymentDateField.setText(null);
        nominalField.setText(null);
        coupon_sizeField.setText(null);
        number_of_coupon_periodsField.setText(null);
        bond_durationField.setText(null);
        maturityField.setText(null);

    }
    /**
     * Закрытие сцены добавления
     */
    @FXML
    private void handleCancel(){AddBondStage.close();}

    public void setAddBondStage(Stage AddBondStage){this.AddBondStage = AddBondStage;}

    public void setMain(Main main) {
        this.main = main;
    }

    public void setApi(Api api) {
        this.api = api;
    }


    /**
     * Кнопка добавления облигации
     */
    @FXML
    private void handleAddBond() {
        boolean addBondResult = api.addBond(bond_nameField.getText(),
                            LocalDate.parse((couponPaymentDateField.getText())),
                            Integer.parseInt(nominalField.getText()),
                            Integer.parseInt(coupon_sizeField.getText()),
                            Integer.parseInt(number_of_coupon_periodsField.getText()),
                            Integer.parseInt(bond_durationField.getText()),
                            Integer.parseInt(maturityField.getText()));

            if (addBondResult) {
                AddBondStage.close();
            }
    }
}

