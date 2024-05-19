package program.models;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс Portfolio - портфели
 */
@ToString
public class Portfolio {
    private final LongProperty id;
    private final StringProperty portfolio_name;

    /**
     * Конструктор Портфеля
     *
     * @param portfolio_name - название портфолио
     */
    public Portfolio(String portfolio_name) {
        this.id = new SimpleLongProperty();
        this.portfolio_name = new SimpleStringProperty(portfolio_name);


    }

    /**
     * Пустой конструктор Портфеля
     */
    public Portfolio() {
        this(null);
    }


    public void setId(long id) {
        this.id.set(id);
    }

    public void setPortfolio_name(String portfolio_name) {
        this.portfolio_name.set(portfolio_name);
    }

    public LongProperty getId() {
        return id;
    }

    public StringProperty getPortfolio_name() {
        return portfolio_name;
    }
}
