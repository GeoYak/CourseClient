package program.controllers;

import javafx.fxml.FXML;
import program.models.RegistrationEditModel;
import program.utils.alerts.Alerts;
import program.utils.validation.Validation;

/**
 * Класс RegistrationPageController, отвечает за регистрацию пользователя
 */
public class RegistrationPageController extends RegistrationEditModel {

    @FXML
    public void initialize() {
        firstNameField.setText(null);
        lastNameField.setText(null);
        loginField.setText(null);
        emailField.setText(null);
        passwordField.setText(null);
        passwordRepeatField.setText(null);

    }

    /**
     * Кнопка регистрации пользователя
     */
    @FXML
    private void handleRegistration() {

        if (Validation.RegistrationDataValidation(this, RegistrationStage)) {
            if (Validation.isValidLength(this, RegistrationStage)) {
                if (Validation.isValidRegistrationRegex(this, RegistrationStage)) {
                    boolean regResult = api.createUser(firstNameField.getText(), lastNameField.getText(), loginField.getText(), emailField.getText(), passwordField.getText());
                    if (regResult) {
                        Alerts.showSuccessRegistration(RegistrationStage);
                        RegistrationStage.close();
                    } else {
                        Alerts.showFailedRegistration(RegistrationStage);
                    }
                }
            }
        }
    }
}


