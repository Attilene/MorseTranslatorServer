package morse.translator.server.test.utils;

import morse.translator.server.dbms.models.User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.Date;

public final class UserUtil {
    public static String login = "attilene";
    public static String anotherLogin = "t1mon";

    public static String email = "example@mail.ru";
    public static String invalidEmail = "example mail.ru";
    public static String anotherEmail = "example1@mail.ru";

    public static String psw = "password1";
    public static String invalidPsw = "password";
    public static String anotherPsw = "password12";

    public static String firstName = "Example name";
    public static String lastName = "Example surname";

    public static String phoneNumber = "89563125890";
    public static String invalidPhoneNumber = "345f2345";

    public static String birthday = "2001-04-08";
    public static String invalidBirthday = "2013-1sd-3";

    public static MultiValueMap<String, String> loginUser(
            int loginEmailType,
            int passwordType
    ) throws IllegalStateException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("login_email",
                switch (loginEmailType) {
                    case 0 -> login;
                    case 1 -> anotherLogin;
                    case 2 -> email;
                    case 3 -> anotherEmail;
                    case 4 -> invalidEmail;
                    case 5 -> null;
                    default -> throw new IllegalStateException("Unexpected value: " + loginEmailType);
                });
        map.add("password",
                switch (passwordType) {
                    case 0 -> psw;
                    case 1 -> anotherPsw;
                    case 2 -> invalidPsw;
                    case 3 -> null;
                    default -> throw new IllegalStateException("Unexpected value: " + passwordType);
                });
        return map;
    }

    public static User createUser() {
        User user = new User();
        user.setFirst_name("Олег");
        user.setLast_name("Кабачков");
        user.setLogin("qwerty");
        user.setEmail("awt@mail.ru");
        user.setPhone_number("1234567890");
        user.setBirthday(Date.valueOf("1970-01-01"));
        return user;
    }

    public static MultiValueMap<String, String> createUser(
            int loginType,
            int emailType,
            int phoneNumberType,
            int birthdayType,
            int passwordType
    ) throws IllegalStateException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("first_name", firstName);
        map.add("last_name", lastName);
        map.add("login",
                switch (loginType) {
                    case 0 -> login;
                    case 1 -> anotherLogin;
                    case 2 -> null;
                    default -> throw new IllegalStateException("Unexpected value: " + loginType);
                });
        map.add("email",
                switch (emailType) {
                    case 0 -> email;
                    case 1 -> anotherEmail;
                    case 2 -> invalidEmail;
                    case 3 -> null;
                    default -> throw new IllegalStateException("Unexpected value: " + emailType);
                });
        map.add("phone_number",
                switch (phoneNumberType) {
                    case 0 -> phoneNumber;
                    case 1 -> invalidPhoneNumber;
                    case 2 -> null;
                    default -> throw new IllegalStateException("Unexpected value: " + phoneNumberType);
                });
        map.add("birthday",
                switch (birthdayType) {
                    case 0 -> birthday;
                    case 1 -> invalidBirthday;
                    case 2 -> null;
                    default -> throw new IllegalStateException("Unexpected value: " + birthdayType);
                });
        map.add("password",
                switch (passwordType) {
                    case 0 -> psw;
                    case 1 -> anotherPsw;
                    case 2 -> invalidPsw;
                    case 3 -> null;
                    default -> throw new IllegalStateException("Unexpected value: " + passwordType);
                });
        return map;
    }

    private UserUtil() {}
}
