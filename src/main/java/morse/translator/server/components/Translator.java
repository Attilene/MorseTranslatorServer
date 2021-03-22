package morse.translator.server.components;

import morse.translator.server.utils.logger.LogType;
import morse.translator.server.utils.logger.LoggerUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Dictionaries for translation to morse code and back
 *
 * @see     Dictionaries
 * @author  Artem Bakanov aka Attilene
 */
@Component
public class Translator extends Dictionaries {
    private static final Logger LOGGER_TRANSLATOR = LoggerUtil.getLogger(LogType.TRANSLATOR);

    /**
     * The start string of words or characters for translating
     */
    public String start_str;

    /**
     * The final string of translated words or characters
     */
    public String end_str;

    /**
     * Constructor of translate class
     */
    public Translator() {
        start_str = null;
        end_str = null;
    }

    /**
     * Method of initial processing the start string and
     * sending a new start string to translations methods
     *
     * @param  toMorse  true, if need to translate to morse code,
     *                  and false, if need to translate from morse code to natural language
     * @param  eng      true, if main natural language is English,
     *                  and false, if Russian is main language
     */
    public void translate(Boolean toMorse, Boolean eng) {
        if (start_str != null) {
            start_str = Translator.replaceE(start_str.toUpperCase());
            start_str = Translator.escapinator(start_str).replaceAll("[\\s]{2,}", " ").trim();
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
            LOGGER_TRANSLATOR.info("Translate '" + start_str + "' to '" + end_str + "'");
        }
    }

    /**
     * Method for translating string with natural language to morse code
     *
     * @param   str   string for morse code translation
     * @param   dict  dictionary in which the key is a natural language letter and the value is a morse code
     * @return        string translated into morse code
     */
    public static String langToMorse(String str, Map<Character, String> dict) {
        String elem;
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            elem = dict.get(str.charAt(i));
            if (elem != null) {
                res.append(elem).append(" ");
            }
        }
        return res.substring(0, res.length() - 1);
    }

    /**
     * Method for translating string with morse code to natural language
     *
     * @param   str   string for natural language translation
     * @param   dict  dictionary in which the key is a morse code and the value is a natural language letter
     * @return        string translated into natural language
     */
    public static String morseToLang(String str, Map<String, Character> dict) {
        Character elem;
        StringBuilder res = new StringBuilder();
        for (String word: str.split(" ")) {
            elem = dict.get(word);
            if (elem != null) {
                res.append(elem);
            }
        }
        return res.toString();
    }

    /**
     * Replacing all letters 'Ё' to 'Е'
     *
     * @param   str  start string which may contains letters 'Ё'
     * @return       final string which has no letters 'Ё'
     */
    public static String replaceE(String str) { return str.replace('Ё', 'Е'); }

    /**
     * Replacing all characters '.' to '·'
     *
     * @param   str  start string which may contains characters '.'
     * @return       final string which has no characters '.'
     */
    public static String replaceDot(String str) { return str.replace(".", "·"); }

    /**
     * <p>Replacing all incorrect characters to ''</p>
     * Incorrect characters: ; $ # @ " ' & % № ^
     * <p></p>
     * © Doofenshmirtz Evil Inc.Corporated
     * @param   str  start string which may contains incorrect characters
     * @return       final string which has no incorrect characters
     */
    public static String escapinator(String str) {
        String[] symbols = new String[] {";", "$", "#", "@", "\"", "'", "&", "%", "№", "^"};
        for (String symbol: symbols) {
            str = str.replace(symbol, "");
        }
        return str;
    }

    /**
     * Getter for start string
     *
     * @return  start_string
     */
    public String getStart_str() { return start_str; }

    /**
     * Getter for end string
     *
     * @return  end_string
     */
    public String getEnd_str() { return end_str; }

    /**
     * Setter for start string
     *
     * @param  start_str  start string for translating
     */
    public void setStart_str(String start_str) { this.start_str = start_str; }
}
