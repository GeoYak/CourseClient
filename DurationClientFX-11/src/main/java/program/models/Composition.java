package program.models;

import javafx.beans.property.*;
import lombok.ToString;

/**
 * Класс Composition - композиции
 */
@ToString
public class Composition {
    private final LongProperty id;
    private final StringProperty portfolio_name;
    private final DoubleProperty bond_share;
    private final StringProperty bond_name;



    /**
     * Конструктор Композиции
     *
     * @param bond_share доля облигации в портфеле
     * @param bond_name  название облигации
     * @param portfolio_name название портфеля
     */
    public Composition(String portfolio_name,
                       Double bond_share,
                       String bond_name
                       ) {
        this.id = new SimpleLongProperty();
        this.portfolio_name = new SimpleStringProperty(portfolio_name);
        this.bond_share = new SimpleDoubleProperty(bond_share);
        this.bond_name = new SimpleStringProperty(bond_name);
    }

    /**
     * Пустой конструктор Композиции
     */
    public Composition() {
        this(null, 0.0, null);
    }



    public void setId(long id) {
        this.id.set(id);
    }

    public void setPortfolio_name(String portfolio_name) {
        this.portfolio_name.set(portfolio_name);
    }

    public void setBond_share(double bond_share) {
        this.bond_share.set(bond_share);
    }

    public void setBond_name(String bond_name) {
        this.bond_name.set(bond_name);
    }

    public StringProperty getPortfolio_name() {
        return portfolio_name;
    }

    public DoubleProperty getBond_share() {
        return bond_share;
    }

    public StringProperty getBond_name() {
        return bond_name;
    }

}
