package morse.translator.server.component;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Translator extends Dictionaries {
    public String start_str, end_str;
    public Boolean eng;

    public Translator() {
        start_str = null;
        end_str = null;
        eng = false;
    }

    public void translate(Boolean toMorse) {
        if (start_str != null) {
            start_str = Translator.replaceE(start_str.toUpperCase());
            if (toMorse && !eng) end_str = langToMorse(start_str, rusToMorse);
            else if (toMorse) end_str = langToMorse(start_str, engToMorse);
            else if (!eng) end_str = morseToLang(start_str, morseToRus);
            else end_str = morseToLang(start_str, morseToEng);
        }
    }

    public static String replaceE(String str) { return str.replace('Ё', 'Е'); }

    public static String langToMorse(String str, Map<Character, String> dict) {
        String elem, res = "";
        for (int i = 0; i < str.length(); i++) {
            elem = dict.get(str.charAt(i));
            if (elem != null) {
                res += elem + " ";
            }
        }
        return res;
    }

    public static String morseToLang(String str, Map<String, Character> dict) {
        Character elem;
        String res = "";
        for (String word: str.split(" ")) {
            elem = dict.get(word);
            if (elem != null) {
                res += elem;
            }
        }
        return res;
    }

    public String getStart_str() { return start_str; }

    public String getEnd_str() { return end_str; }

    public Boolean getEng() { return eng; }

    public void setStart_str(String start_str) { this.start_str = start_str; }

    public void setEng(Boolean eng) { this.eng = eng; }
}
