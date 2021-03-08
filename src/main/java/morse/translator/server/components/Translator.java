package morse.translator.server.components;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Translator extends Dictionaries {
    public String start_str, end_str;

    public Translator() {
        start_str = null;
        end_str = null;
    }

    public void translate(Boolean toMorse, Boolean eng) {
        if (start_str != null) {
            start_str = Translator.replaceE(start_str.toUpperCase());
            if (toMorse && !eng) end_str = langToMorse(start_str, rusToMorse);
            else if (toMorse) end_str = langToMorse(start_str, engToMorse);
            else if (!eng) {
                start_str = replaceDot(start_str);
                end_str = morseToLang(start_str, morseToRus);
            }
            else {
                start_str = replaceDot(start_str);
                end_str = morseToLang(start_str, morseToEng);
            }
        }
    }

    public static String replaceE(String str) { return str.replace('Ё', 'Е'); }

    public static String replaceDot(String str) { return str.replace(".", "·"); }

    public static String langToMorse(String str, Map<Character, String> dict) {
        String elem, res = "";
        for (int i = 0; i < str.length(); i++) {
            elem = dict.get(str.charAt(i));
            if (elem != null) {
                res += elem + " ";
            }
        }
        return res.substring(0, res.length() - 1);
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

    public void setStart_str(String start_str) { this.start_str = start_str; }
}
