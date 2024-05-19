package program.models;

import javafx.beans.property.*;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Класс Bond - облигации
 */
@ToString
public class Bond {
    private final LongProperty id;
    private final StringProperty bond_name;
    private final ObjectProperty<LocalDate> coupon_payment_date;
    private final IntegerProperty nominal;
    private final IntegerProperty coupon_size;
    private final IntegerProperty number_of_coupon_periods;
    private final IntegerProperty bond_duration;
    private final IntegerProperty maturity;



    /**
     * Конструктор Облигации
     *
     * @param bond_name  название облигации
     * @param coupon_payment_date  дата выплаты купона
     * @param nominal   номинал
     * @param coupon_size  размер купона
     * @param number_of_coupon_periods  кол-во купонных периодов
     * @param bond_duration  дюрация облигации
     * @param maturity срок погашения
     */
    public Bond(String bond_name,
                LocalDate coupon_payment_date,
                Integer nominal,
                Integer coupon_size,
                Integer number_of_coupon_periods,
                Integer bond_duration,
                Integer maturity) {
        this.id = new SimpleLongProperty();
        this.bond_name = new SimpleStringProperty(bond_name);
        this.coupon_payment_date = new SimpleObjectProperty<>(coupon_payment_date);
        this.nominal = new SimpleIntegerProperty(nominal);
        this.coupon_size = new SimpleIntegerProperty(coupon_size);
        this.number_of_coupon_periods = new SimpleIntegerProperty(number_of_coupon_periods);
        this.bond_duration = new SimpleIntegerProperty(bond_duration);
        this.maturity = new SimpleIntegerProperty(maturity);
    }

    /**
     * Пустой конструктор Облигации
     */
    public Bond() {
        this(null, null, 0, 0, 0,0,0);
    }



    public void setId(long id) {
        this.id.set(id);
    }
    public void setBond_name(String bond_name) {
        this.bond_name.set(bond_name);
    }
    public void setCoupon_payment_date(LocalDate coupon_payment_date) {
        this.coupon_payment_date.set(coupon_payment_date);
    }

    public void setNominal(int nominal) {
        this.nominal.set(nominal);
    }

    public void setCoupon_size(int coupon_size) {
        this.coupon_size.set(coupon_size);
    }

    public void setNumber_of_coupon_periods(int number_of_coupon_periods) {
        this.number_of_coupon_periods.set(number_of_coupon_periods);
    }

    public void setBond_duration(int bond_duration) {
        this.bond_duration.set(bond_duration);
    }

    public void setMaturity(int maturity) {
        this.maturity.set(maturity);
    }

    public LongProperty getId() {
        return id;
    }

    public StringProperty getBond_name() {
        return bond_name;
    }

    public ObjectProperty<LocalDate> getCoupon_payment_date() {
        return coupon_payment_date;
    }


    public IntegerProperty getNominal() {
        return nominal;
    }


    public IntegerProperty getCoupon_size() {
        return coupon_size;
    }

    public IntegerProperty getNumber_of_coupon_periods() {
        return number_of_coupon_periods;
    }
    public IntegerProperty getBond_duration() {
        return bond_duration;
    }
    public IntegerProperty getMaturity() {
        return maturity;
    }

}
