package morse.translator.server.test.utils;

import morse.translator.server.dbms.models.User;

import java.sql.Date;

public class UserUtil {
    public static User createUser() {
        User user = new User();
        user.setFirst_name("Artem");
        user.setLast_name("Bakanov");
        user.setLogin("attilene");
        user.setEmail("artembakanov123@yandex.ru");
        user.setPhone_number("1234567890");
        user.setBirthday(Date.valueOf("2001-04-08"));
        return user;
    }
}
