package morse.translator.server.components;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Dictionaries of languages and morse for translation to morse code and back
 *
 * @see     Translator
 * @author  Artem Bakanov aka Attilene
 */
@Component
public abstract class Dictionaries {
    /**
     * Dictionary for translating from Russian language to Morse code.
     * Each letter of the language has its own set of dots and dashes.
     * Morse code has not case
     */
    protected final Map<Character, String> rusToMorse = new HashMap<>() {{
        put('А', "·-");
        put('Б', "-···");
        put('В', "·--");
        put('Г', "--·");
        put('Д', "-··");
        put('Е', "·");
        put('Ж', "···-");
        put('З', "--··");
        put('И', "··");
        put('Й', "·---");
        put('К', "-·-");
        put('Л', "·-··");
        put('М', "--");
        put('Н', "-·");
        put('О', "---");
        put('П', "·--·");
        put('Р', "·-·");
        put('С', "···");
        put('Т', "-");
        put('У', "··-");
        put('Ф', "··-·");
        put('Х', "····");
        put('Ц', "-·-·");
        put('Ч', "---·");
        put('Ш', "----");
        put('Щ', "--·-");
        put('Ъ', "·--·-·");
        put('Ы', "-·--");
        put('Ь', "-··-");
        put('Э', "··-··");
        put('Ю', "··--");
        put('Я', "·-·-");
        put('0', "-----");
        put('1', "·----");
        put('2', "··---");
        put('3', "···--");
        put('4', "····-");
        put('5', "·····");
        put('6', "-····");
        put('7', "--···");
        put('8', "---··");
        put('9', "----·");
        put('.', "······");
        put(',', "·-·-·-");
        put('/', "-··-·");
        put('?', "··--··");
        put(':', "---···");
        put('!', "--··--");
        put(' ', "-···-");
    }};

    /**
     * Dictionary for translating from English language to Morse code
     * Each letter of the language has its own set of dots and dashes.
     * Morse code has not case
     */
    protected Map<Character, String> engToMorse = new HashMap<>() {{
        put('A', "·-");
        put('B', "-···");
        put('C', "-·-·");
        put('D', "-··");
        put('E', "·");
        put('F', "··-·");
        put('G', "--·");
        put('H', "····");
        put('I', "··");
        put('J', "·---");
        put('K', "-·-");
        put('L', "·-··");
        put('M', "--");
        put('N', "-·");
        put('O', "---");
        put('P', "·--·");
        put('Q', "--·-");
        put('R', "·-·");
        put('S', "···");
        put('T', "-");
        put('U', "··-");
        put('V', "···-");
        put('W', "·--");
        put('X', "-··-");
        put('Y', "-·--");
        put('Z', "--··");
        put('0', "-----");
        put('1', "·----");
        put('2', "··---");
        put('3', "···--");
        put('4', "····-");
        put('5', "·····");
        put('6', "-····");
        put('7', "--···");
        put('8', "---··");
        put('9', "----·");
        put('.', "······");
        put(',', "·-·-·-");
        put('/', "-··-·");
        put('?', "··--··");
        put(':', "---···");
        put('!', "--··--");
        put(' ', "-···-");
    }};

    /**
     * Dictionary for translating from Morse code to Russian language
     * Each set of dots and dashes has its own letter of the language.
     * Morse code has not case
     */
    protected final Map<String, Character> morseToRus = new HashMap<>() {{
        for(Map.Entry<Character, String> entry: rusToMorse.entrySet()) {
            put(entry.getValue(), entry.getKey());
        }
    }};

    /**
     * Dictionary for translating from Morse code to English language
     * Each set of dots and dashes has its own letter of the language.
     * Morse code has not case
     */
    protected final Map<String, Character> morseToEng = new HashMap<>() {{
        for(Map.Entry<Character, String> entry: engToMorse.entrySet()) {
            put(entry.getValue(), entry.getKey());
        }
    }};

    /**
     * Method for displaying or returning all translation dictionaries
     *
     * @return  set of translation dictionaries in string field
     */
    @Override
    public String toString() {
        return "Dictionaries{" +
                "rusToMorse=" + rusToMorse +
                ", engToMorse=" + engToMorse +
                ", morseToRus=" + morseToRus +
                ", morseToEng=" + morseToEng +
                '}';
    }
}
