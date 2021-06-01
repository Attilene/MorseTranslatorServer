package morse.translator.server.test.utils;

import morse.translator.server.dbms.models.History;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.Instant;
import java.util.Date;

public final class HistoryUtil {
    public static String startString = "Hello, World!";
    public static String anotherStartString = "Qwerty123!";
    public static String anotherStartString2 = "··· · ·-·· · -·-· - -···- -···- ··-· ·-· --- -- -···- ··- ··· · ·-· ···";

    public static History createHistory() {
        History history = new History();
        history.setStart_string("rtridjsncskdxgjf");
        history.setEnd_string("egidknvkdfskdflskrtdf");
        history.setOperation_time(Date.from(Instant.now()));
        return history;
    }

    public static MultiValueMap<String, String> createHistory(
            Long user_id,
            int startStringType,
            Boolean morse,
            Boolean language
    ) throws IllegalStateException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("start_string",
                switch (startStringType) {
                    case 0 -> startString;
                    case 1 -> anotherStartString;
                    case 2 -> anotherStartString2;
                    case 3 -> null;
                    default -> throw new IllegalStateException("Unexpected value: " + startStringType);
                });
        map.add("user_id", user_id.toString());
        map.add("morse", morse.toString());
        map.add("language", language.toString());
        return map;
    }

    private HistoryUtil() {}
}
