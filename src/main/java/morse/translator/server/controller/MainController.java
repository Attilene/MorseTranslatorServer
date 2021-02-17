package morse.translator.server.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @PostMapping(value = "/")
    public String main() {
        return "main";
    }
}
