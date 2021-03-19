package morse.translator.server.test.utils;

import morse.translator.server.dbms.models.Password;

public final class PasswordUtil {
    public static Password createPassword() {
        Password password = new Password();
        password.setHash("erthdybujhzdfnsiuksjgz");
        password.setSalt("weudfhjncjsdknfjsfjkuwersdffbhshs");
        return password;
    }

    private PasswordUtil() {}
}
