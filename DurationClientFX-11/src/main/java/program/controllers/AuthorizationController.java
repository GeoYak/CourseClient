package program.controllers;

import javafx.fxml.FXML;
import program.models.Person;
import program.models.SignInModel;
import program.utils.alerts.Alerts;
import program.utils.validation.Validation;

/**
 * Класс AuthorizationController, отвечает за авторизацию
 */
public class AuthorizationController extends SignInModel {
    private Person person;

    @FXML
    private void initialize() {
        emailField.setText(null);
        passwordField.setText(null);
        person = new Person();
    }

    public Person getPerson() {
        return person;
    }


    /**
     * Нажатие на кнопку Войти
     */
    @FXML
    private void handlerSignInBtn() {
        if (Validation.isValidAuthorization(this, AuthorizationStage)) {
            if (Validation.isValidAuthorizationRegex(this, AuthorizationStage)) {
                boolean authResult = main.getApi().checkUserExists(emailField.getText(), passwordField.getText());
                if (authResult){
                    main.MainLayout();
                }
                else {
                    Alerts.showNoValidSignIn(AuthorizationStage);
                }

            }
        }
    }

    /**
     * Нажатие на кнопку Зарегистрироваться
     */
    @FXML
    private void handlerRegistrationBtn() {
        main.showRegistrationPage();
    }

}
