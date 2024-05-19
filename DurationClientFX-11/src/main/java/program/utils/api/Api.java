package program.utils.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import program.models.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Класс Api - нужен для связи с сервером по HTTP протоколу
 */
public class Api {
    private final String HOST = "http://localhost:8080";
    public Person currentLoginPerson;

    /**
     * Метод проверки авторизации пользователя на сервере
     *
     * @param email    email
     * @param password пароль
     * @return true если есть в БД
     */
    public Boolean checkUserExists(String email, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        Gson gson = new Gson();
        String json = gson.toJson(map);
        String URL = String.format("%s/users/auth", HOST);
        String response = HttpRequest.sendPost(URL, json);

        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            if (jsonResult.get("success").getAsBoolean()) {
                JsonObject result = jsonResult.get("result").getAsJsonObject();
                currentLoginPerson = new Person();
                currentLoginPerson.setLogin(result.get("login").getAsString());
                currentLoginPerson.setEmail(result.get("email").getAsString());
                currentLoginPerson.setFirstName(result.get("firstName").getAsString());
                currentLoginPerson.setLastName(result.get("lastName").getAsString());


                try {
                    currentLoginPerson.setPhoneNumber(result.get("phoneNumber").getAsString());
                } catch (RuntimeException e) {
                    currentLoginPerson.setPhoneNumber(null);
                }
                try {
                    LocalDate birthDay = DateConvert.stringToDate(result.get("birthDate").getAsString());
                    currentLoginPerson.setBirthday(birthDay);

                } catch (RuntimeException e) {
                    currentLoginPerson.setBirthday(null);
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Метод регистрации пользователя
     *
     * @param firstName имя
     * @param lastName  фамилия
     * @param login     логин
     * @param email     почта
     * @param password  пароль
     * @return true  если добавлен в БД
     */
    public Boolean createUser(String firstName, String lastName, String login, String email, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("login", login);
        map.put("firstName", firstName);
        map.put("lastName", lastName);
        map.put("email", email);
        map.put("password", password);

        Gson gson = new Gson();
        String json = gson.toJson(map);
        String URL = String.format("%s/users/registration", HOST);
        String response = HttpRequest.sendPost(URL, json);
        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            if (jsonResult.get("success").getAsBoolean()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод обновление информации о пользователе
     *
     * @param firstName   имя
     * @param lastName    фамилия
     * @param login       логин
     * @param email       почта
     * @param phoneNumber номер телефона
     * @param birthDate   дата рождения
     * @param password    пароль
     * @return true если обновление информации пользователя в БД успешно
     */
    public Boolean updateUser(String firstName, String lastName, String login,
                              String email, String phoneNumber, LocalDate birthDate, String password) {
        String URL = String.format("%s/users/update", HOST);
        Map<String, String> user = new HashMap<>();
        user.put("login", login);
        user.put("firstName", firstName);
        user.put("lastName", lastName);
        user.put("email", email);
        if (birthDate != null) {
            user.put("birthDate", String.valueOf(birthDate));
        }
        user.put("phoneNumber", phoneNumber);
        user.put("password", password);

        Gson gson = new Gson();
        String json = gson.toJson(user);
        String response = HttpRequest.sendPut(URL, json);
        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            if (jsonResult.get("success").getAsBoolean()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод удаления пользователя по почте
     *
     * @param email    почта
     * @param password пароль
     * @return true если удалили пользователя из БД
     */

    public boolean deleteUser(String email, String password) {
        password = URLEncoder.encode(password, StandardCharsets.UTF_8);

        String URL = String.format("%s/users/delete?email=%s&password=%s", HOST, email, password);
        String response = HttpRequest.sendDelete(URL);
        if (response != null) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Получаем всю информацию облигаций
     *
     * @param filter вид сортировки
     * @return список облигаций
     */
    public List<Bond> getAllBonds(Boolean filter) {

        String URL = String.format("%s/bonds/all?filter=%s", HOST, filter);
        List<Bond> result = new ArrayList<>();
        String response = HttpRequest.sendGet(URL);

        if (response != null) {
            JsonArray jsonBondArray = JsonParser.parseString(response).getAsJsonArray();
            for (int i = 0; i < jsonBondArray.size(); i++) {
                JsonObject bondJson = jsonBondArray.get(i).getAsJsonObject();

                Bond bond = new Bond();
                bond.setId(bondJson.get("bond_id").getAsLong());
                bond.setBond_name(bondJson.get("bondName").getAsString());
                LocalDate couponPaymentDate = DateConvert.stringToDate(bondJson.get("coupon_payment_date").getAsString());
                bond.setCoupon_payment_date(couponPaymentDate);
                bond.setNominal(bondJson.get("nominal").getAsInt());
                bond.setCoupon_size(bondJson.get("coupon_size").getAsInt());
                bond.setNumber_of_coupon_periods(bondJson.get("number_of_coupon_periods").getAsInt());
                bond.setBond_duration(bondJson.get("bond_duration").getAsInt());
                bond.setMaturity(bondJson.get("maturity").getAsInt());

                result.add(bond);
            }
        }

        return result;
    }

    /**
     * Получаем всю информацию облигаций по названию облигации
     *
     * @param name названия облигации
     * @param filter    вид сортировки
     * @return список облигаций
     */
    public List<Bond> getBondsBySubBondName(String name, Boolean filter) {

        String URL = String.format("%s/bonds/like?name=%s&filter=%s", HOST, name, filter);
        List<Bond> result = new ArrayList<>();
        String response = HttpRequest.sendGet(URL);

        if (response != null) {
            JsonArray jsonBondArray = JsonParser.parseString(response).getAsJsonArray();
            for (int i = 0; i < jsonBondArray.size(); i++) {
                JsonObject bondJson = jsonBondArray.get(i).getAsJsonObject();

                Bond bond = new Bond();
                bond.setId(bondJson.get("bond_id").getAsLong());
                bond.setBond_name(bondJson.get("bondName").getAsString());
                LocalDate couponPaymentDate = DateConvert.stringToDate(bondJson.get("coupon_payment_date").getAsString());
                bond.setCoupon_payment_date(couponPaymentDate);
                bond.setNominal(bondJson.get("nominal").getAsInt());
                bond.setCoupon_size(bondJson.get("coupon_size").getAsInt());
                bond.setNumber_of_coupon_periods(bondJson.get("number_of_coupon_periods").getAsInt());
                bond.setBond_duration(bondJson.get("bond_duration").getAsInt());
                bond.setMaturity(bondJson.get("maturity").getAsInt());

                result.add(bond);
            }
        }
        return result;
    }
    /**
     * Получаем всю информацию о композициях пользователя
     *
     * @param login логин
     * @return список композиций
     */
    public List<Composition> getAllCompositions(String login) {

        String URL = String.format("%s/compositions/all?login=%s", HOST, login);
        List<Composition> result = new ArrayList<>();
        String response = HttpRequest.sendGet(URL);

        if (response != null) {

            JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject сompositionJson = jsonArray.get(i).getAsJsonObject();
                JsonObject bondJson = сompositionJson.get("bond").getAsJsonObject();
                JsonObject portfolioJson = сompositionJson.get("portfolio").getAsJsonObject();

                Composition сomposition = new Composition();
                сomposition.setId(сompositionJson.get("composition_id").getAsLong());
                //Имена в "" из сервера(Entity)
                сomposition.setBond_name(bondJson.get("bondName").getAsString());
                сomposition.setPortfolio_name(portfolioJson.get("portfolioName").getAsString());
                сomposition.setBond_share(сompositionJson.get("bondShare").getAsDouble());


                result.add(сomposition);
            }
        }

        return result;
    }

    /**
     * Получаем всю информацию о композициях пользователя по названию портфеля
     *
     * @param login логин
     * @param name название портфеля
     * @return список композиций
     */
    public List<Composition> getCompositionsBySubName(String login, String name) {

        String URL = String.format("%s/compositions/like?login=%s&name=%s", HOST, login, name);
        List<Composition> result = new ArrayList<>();
        String response = HttpRequest.sendGet(URL);

        if (response != null) {

            JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject сompositionJson = jsonArray.get(i).getAsJsonObject();
                JsonObject bondJson = сompositionJson.get("bond").getAsJsonObject();
                JsonObject portfolioJson = сompositionJson.get("portfolio").getAsJsonObject();

                Composition сomposition = new Composition();
                сomposition.setId(сompositionJson.get("composition_id").getAsLong());
                //Имена в "" из сервера(Entity)
                сomposition.setBond_name(bondJson.get("bondName").getAsString());
                сomposition.setPortfolio_name(portfolioJson.get("portfolioName").getAsString());
                сomposition.setBond_share(сompositionJson.get("bondShare").getAsDouble());


                result.add(сomposition);
            }
        }

        return result;
    }
    /**
     * Расчет дюрациии портфеля пользователя по названию портфеля
     *
     * @param login логин
     * @param name название портфеля
     * @return дюрация портфеля
     */

    public double getDurationBySubName(String login, String name) {

        String URL = String.format("%s/compositions/like?login=%s&name=%s", HOST, login, name);
        double duration_result = 0;
        String response = HttpRequest.sendGet(URL);

        if (response != null) {

            JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject сompositionJson = jsonArray.get(i).getAsJsonObject();
                JsonObject bondJson = сompositionJson.get("bond").getAsJsonObject();
                JsonObject portfolioJson = сompositionJson.get("portfolio").getAsJsonObject();

                duration_result += (bondJson.get("bond_duration").getAsInt()*(сompositionJson.get("bondShare").getAsDouble())/100);

            }
        }
        return duration_result;
    }

    /**
     * Метод добавления облигации
     *
     * @param bond_name  название облигации
     * @param coupon_payment_date  дата погашения
     * @param nominal   номинал
     * @param coupon_size  размер купона
     * @param number_of_coupon_periods  кол-во купонных периодов
     * @param bond_duration  дюрация облигации
     * @return true  если добавлен в БД
     */
    public Boolean addBond(String bond_name,
                           LocalDate coupon_payment_date,
                           Integer nominal,
                           Integer coupon_size,
                           Integer number_of_coupon_periods,
                           Integer bond_duration,
                           Integer maturity) {

        Map<String, Object> map = new HashMap<>();
        map.put("bondName", bond_name);
        map.put("coupon_payment_date",  String.valueOf(coupon_payment_date));
        map.put("nominal", nominal);
        map.put("coupon_size", coupon_size);
        map.put("number_of_coupon_periods", number_of_coupon_periods);
        map.put("bond_duration", bond_duration);
        map.put("maturity", maturity);

        Gson gson = new Gson();
        String json = gson.toJson(map);

        String URL = String.format("%s/bonds/add", HOST);
        String response = HttpRequest.sendPost(URL, json);
        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            if (jsonResult.get("success").getAsBoolean()) {
                return true;
            }
        }
        return false;
    }
    /**
     * Метод удаления облигации по названию облигации
     *
     * @param name название облигации
     * @return true если удалили облигацию из БД
     */
    public boolean deleteBond(String name) {

        String URL = String.format("%s/bonds/delete?name=%s", HOST, name);
        String response = HttpRequest.sendDelete(URL);
        if (response != null) {
            return true;
        } else {
            return false;
        }

    }
    /**
     * Получаем всю информацию о портфелях пользователя по названию портфеля
     *
     * @param login логин
     * @param name название портфеля
     * @return список портфелей
     */
    public List<Portfolio> getPortfolioBySubName(String login, String name) {

        String URL = String.format("%s/portfolios/like?login=%s&name=%s", HOST, login, name);
        List<Portfolio> result = new ArrayList<>();
        String response = HttpRequest.sendGet(URL);

        if (response != null) {
            JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject portfolioJson = jsonArray.get(i).getAsJsonObject();


                Portfolio portfolio = new Portfolio();
                portfolio.setId(portfolioJson.get("portfolio_id").getAsLong());
                portfolio.setPortfolio_name(portfolioJson.get("portfolioName").getAsString());



                result.add(portfolio);
            }
        }
        return result;
    }

    /**
     * Метод добавления в портфель облигации
     *
     * @param bond_share доля облигации в портфеле
     * @param bondName  название облигации
     * @param portfolioName название портфеля
     * @return true если облигация добавлена в портфель
     */
    public Boolean addToPortfolio(Double bond_share,
                           String bondName,
                           String portfolioName) {

        Gson gson = new Gson();

        List<Bond> bonds = getBondsBySubBondName(bondName, false);
        List<Portfolio> portfolios = getPortfolioBySubName(currentLoginPerson.getLogin(), portfolioName);
        Long portfolioLongId = null;
        if (portfolios.size() != 0) {
            Portfolio portfolio = portfolios.get(0);
            portfolioLongId = portfolio.getId().longValue();
        }
        Bond bond = bonds.get(0);
        Long bondLongId = bond.getId().longValue();

        Map<String, Object> map = new HashMap<>();

        map.put("bondShare", bond_share);
        map.put("bond_id", bondLongId);
        map.put("portfolio_id", portfolioLongId);
        map.put("portfolioName", portfolioName);
        map.put("login", currentLoginPerson.getLogin());

        String json = gson.toJson(map);

        String URL = String.format("%s/compositions/add", HOST);
        String response = HttpRequest.sendPost(URL, json);
        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            if (jsonResult.get("success").getAsBoolean()) {
                return true;
            }
        }
        return false;
    }
    /**
     * Метод удаления портфеля пользователя по названию портфеля
     *
     * @param login логин
     * @param name название портфеля
     * @return true если удалили облигацию из БД
     */
    public boolean deleteComposition(String login, String name) {

        String URL = String.format("%s/compositions/delete?login=%s&name=%s", HOST, login, name);
        String response = HttpRequest.sendDelete(URL);
        if (response != null) {
            return true;
        } else {
            return false;
        }

    }

}
