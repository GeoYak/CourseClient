package program.utils.validation;

import java.util.regex.Pattern;

/**
 * Класс RegexValidation содержит проверки через регулярные выражения
 * Проверки:
 * stdRegex - строка запрещенных символов
 * emailRegex - проверка почты
 * passwordRegex - проверка сложности и шаблона пароля
 * phoneNumberRegex - проверка номера телефона
 */
public class RegexValidation {
    private static final String stdRegex = "^[^%\"';:]*$";
    private static final String emailRegex = "^[a-zA-Z0-9.]+@([a-zA-Z]{2,10}[.]){1,3}(com|by|ru|eu|uk)$";
    private static final String passwordRegex = "^((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,})$";
    private static final String phoneNumberRegex = "^[(+7)8]+([0-9]{10})$";
    private static final String nameRegex ="^[a-zA-Z]*$";

    public static boolean checkStandard(String text) { return Pattern.matches(stdRegex, text); }

    public static boolean checkEmail(String email) { return Pattern.matches(emailRegex, email); }

    public static boolean checkPassword(String password) { return Pattern.matches(passwordRegex, password); }

    public static boolean checkPhoneNumber(String phoneNumber) { return Pattern.matches(phoneNumberRegex, phoneNumber); }

    public static boolean checkLength(String text, int num) { return text.length() <= num; }

    public static boolean checkName(String name) { return Pattern.matches(nameRegex, name); }

    public static String getEmailRegex() { return emailRegex; }

    public static String getPasswordRegex() { return passwordRegex; }

    public static String getPhoneNumberRegex() { return phoneNumberRegex; }

    public static String getStandardRegex() { return stdRegex; }
}
