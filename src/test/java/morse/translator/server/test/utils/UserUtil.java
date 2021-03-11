package morse.translator.server.test.utils;

import morse.translator.server.dbms.models.User;

import java.sql.Date;

public abstract class UserUtil {
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
}
