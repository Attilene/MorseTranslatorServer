package morse.translator.server.component;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public abstract class Dictionaries {
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

    protected final Map<String, Character> morseToRus = new HashMap<>() {{
        for(Map.Entry<Character, String> entry: rusToMorse.entrySet()) {
            put(entry.getValue(), entry.getKey());
        }
    }};

    protected final Map<String, Character> morseToEng = new HashMap<>() {{
        for(Map.Entry<Character, String> entry: engToMorse.entrySet()) {
            put(entry.getValue(), entry.getKey());
        }
    }};

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
