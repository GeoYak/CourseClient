package program;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import program.controllers.*;
import program.models.Person;
import program.utils.api.Api;

import java.io.IOException;

/**
 * Класс Main запускает приложение Duration
 */
public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private BorderPane mainLayout;
    private final Api api;
    private final ObservableList<Person> personData = FXCollections.observableArrayList();

    public Api getApi() {
        return api;
    }

    /**
     * Пустой конструктор с экземпляром класса api
     */
    public Main() {
        api = new Api();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    /**
     * Метод, которые устанавливает первое окно приложения
     *
     * @param primaryStage установка сцены
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Duration app");

        initRootLayout();

    }

    /**
     * Загрузка сцены rootLayout
     * Меню содержит строку об авторе
     */
    @FXML
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/rootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);

            RootLayoutController rootLayoutController = loader.getController();
            rootLayoutController.setMain(this);
            primaryStage.show();
            showAuthorizationPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Главное окно с Toolbar
     * Панель на которой расположены основные окна программы
     */
    public void MainLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/mainLayout.fxml"));
            mainLayout = loader.load();

            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();

            MainLayoutController mainLayoutController = loader.getController();
            mainLayoutController.setApi(api);
            mainLayoutController.setMain(this);
            mainLayoutController.showHelloMessage();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Окно авторизации пользователя
     * расположено в центре rootLayout
     */
    public void showAuthorizationPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/authorization.fxml"));
            AnchorPane authorizationPage = loader.load();

            rootLayout.setCenter(authorizationPage);

            AuthorizationController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Окно регистрации пользователя
     */
    public void showRegistrationPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/registration.fxml"));
            AnchorPane registrationPage = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Регистрация");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            stage.setResizable(false);

            Scene scene = new Scene(registrationPage);
            stage.setScene(scene);

            RegistrationPageController controller = loader.getController();
            controller.setRegistrationStage(stage);
            controller.setApi(api);
            controller.setMain(this);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Окно об авторе
     */
    public void showAboutPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/aboutPage.fxml"));
            AnchorPane aboutPage = loader.load();
            Stage stage = new Stage();
            stage.setTitle("GeoYak страница");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            stage.setResizable(false);

            Scene scene = new Scene(aboutPage);
            stage.setScene(scene);

            AboutPageController controller = loader.getController();
            controller.setAboutStage(stage);
            controller.setHostServices(getHostServices());
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Окно редактирования данных пользователя
     * расположено в центре mainLayout
     */
    public void showEditPersonPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/editPerson.fxml"));
            AnchorPane editPersonPage = loader.load();

            mainLayout.setCenter(editPersonPage);

            EditPersonController controller = loader.getController();
            controller.setApi(api);
            controller.setPerson(api.currentLoginPerson);
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Окно Табло композиций
     * расположено в центре mainLayout
     */
    public void showCompositionPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/compositionPage.fxml"));
            BorderPane compositionPage = loader.load();

            mainLayout.setCenter(compositionPage);

            CompositionController controller = loader.getController();
            controller.setStage(primaryStage);
            controller.setApi(api);
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Окно информации об облигациях
     * расположено в центре mainLayout
     */
    public void showBondInfoPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/bondsPage.fxml"));
            BorderPane bondInfoPage = loader.load();

            mainLayout.setCenter(bondInfoPage);

            BondController controller = loader.getController();
            controller.setMain(this);
            controller.setStage(primaryStage);
            controller.setApi(api);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Окно расчета дюрации
     */
    public void showDurationPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/durationPage.fxml"));
            AnchorPane durationPage = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Расчет дюрации");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            stage.setResizable(false);

            Scene scene = new Scene(durationPage);
            stage.setScene(scene);

            DurationPageController controller = loader.getController();
            controller.setStage(stage);
            controller.setApi(api);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Окно добавления облигации
     */
    public void showAddBondPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/addBond.fxml"));
            AnchorPane registrationPage = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Добавит облигацию");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            stage.setResizable(false);

            Scene scene = new Scene(registrationPage);
            stage.setScene(scene);

            AddBondPageController controller = loader.getController();
            controller.setAddBondStage(stage);
            controller.setApi(api);
            controller.setMain(this);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Окно добавления в портфель облигации
     */
    public void showAddToPortfolioPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/addToPortfolio.fxml"));
            AnchorPane registrationPage = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Добавления в портфель облигации");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            stage.setResizable(false);

            Scene scene = new Scene(registrationPage);
            stage.setScene(scene);

            AddToPortfolioPageController controller = loader.getController();
            controller.setAddBondStage(stage);
            controller.setApi(api);
            controller.setMain(this);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
