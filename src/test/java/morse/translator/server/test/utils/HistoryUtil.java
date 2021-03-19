package morse.translator.server.test.utils;

import morse.translator.server.dbms.models.History;

import java.time.Instant;
import java.util.Date;

public final class HistoryUtil {
    public static History createHistory() {
        History history = new History();
        history.setStart_string("rtridjsncskdxgjf");
        history.setEnd_string("egidknvkdfskdflskrtdf");
        history.setOperation_time(Date.from(Instant.now()));
        return history;
    }

    private HistoryUtil() {}
}
