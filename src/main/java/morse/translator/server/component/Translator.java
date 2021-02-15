package morse.translator.server.component;

import org.springframework.stereotype.Component;

@Component
public class Translator extends Dictionaries {
    public String start_str, end_str;

    public Translator() {
        start_str = null;
        end_str = null;
        System.out.println(this.toString());
    }

    public String translate() {
        return null;
    }

    public static String replaceE(String str) {
        return str.replace('Ё', 'Е');
    }

    public String getStart_str() { return start_str; }

    public String getEnd_str() { return end_str; }

    public void setStart_str(String start_str) { this.start_str = start_str; }

    public void setEnd_str(String end_str) { this.end_str = end_str; }
}
