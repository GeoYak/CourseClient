package program.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import program.Main;
import program.models.Bond;
import program.utils.alerts.Alerts;
import program.utils.api.Api;
import program.utils.validation.Validation;

import java.time.LocalDate;
import java.util.List;

/**
 * Класс BondController, отвечает за облигации
 */
public class BondController {
    private Main main;
    private Stage stage;
    protected Api api;
    private final ObservableList<Bond> bondsData = FXCollections.observableArrayList();

    @FXML
    private TableView<Bond> bondTableView;
    @FXML
    private TableColumn<Bond, String> bondNameColumn;
    @FXML
    private TableColumn<Bond, LocalDate> couponPaymentDateColumn;
    @FXML
    private TableColumn<Bond, Integer> nominalColumn;
    @FXML
    private TableColumn<Bond, Integer> couponSizeColumn;
    @FXML
    private TableColumn<Bond, Integer> numberOfCouponPeriodsColumn;
    @FXML
    private TableColumn<Bond, Integer> bondDurationColumn;
    @FXML
    private TableColumn<Bond, Integer> maturityColumn;
    @FXML
    private TextField searchCompany;

    @FXML
    private CheckBox sortBondBtn;

    @FXML
    public void initialize() {
        searchCompany.setText(null);

        bondNameColumn.setCellValueFactory(cellData -> cellData.getValue().getBond_name());
        couponPaymentDateColumn.setCellValueFactory(cellData -> cellData.getValue().getCoupon_payment_date());
        nominalColumn.setCellValueFactory(cellData -> cellData.getValue().getNominal().asObject());
        couponSizeColumn.setCellValueFactory(cellData -> cellData.getValue().getCoupon_size().asObject());
        numberOfCouponPeriodsColumn.setCellValueFactory(cellData -> cellData.getValue().getNumber_of_coupon_periods().asObject());
        bondDurationColumn.setCellValueFactory(cellData -> cellData.getValue().getBond_duration().asObject());
        maturityColumn.setCellValueFactory(cellData -> cellData.getValue().getMaturity().asObject());

        bondTableView.setItems(bondsData);

    }

    /**
     * Кнопка показа облигаций
     */
    @FXML
    public void handlerBondsBtn() {
        // обнуление таблицы
        if (Validation.checkLength(searchCompany.getText(), 40)) {

            bondTableView.getItems().clear();
            boolean filter = false;
            filter = sortBondBtn.isSelected();
            List<Bond> result;
            if (Validation.isNameBlank(searchCompany.getText())) {
                // Получаем все авиакомпании
                result = api.getAllBonds(filter);
            } else {
                // Получаем все авиакомпании по вхождению подстроки
                result = api.getBondsBySubBondName(searchCompany.getText(), filter);
            }
            bondsData.addAll(result);
        } else {
            Alerts.showNoValidLength(stage, "поиск", 40);
        }
    }

    /**
     * Кнопка удаления облигации по названию облигации
     */
    @FXML
    private void handleDeleteBond() {
        boolean result = api.deleteBond(searchCompany.getText());
                if (result) {
                    main.showBondInfoPage();
            }
        }

    /**
     * Кнопка показа окна добавления облигации
     */
    @FXML
    private void handleAddBondPage(){
        main.showAddBondPage();
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
