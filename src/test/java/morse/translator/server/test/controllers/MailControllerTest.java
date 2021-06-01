package morse.translator.server.test.controllers;

import morse.translator.server.ServerApplication;
import morse.translator.server.dbms.models.User;
import morse.translator.server.dbms.repositories.UserRepository;
import morse.translator.server.test.utils.UserUtil;
import morse.translator.server.utils.security.CryptoUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ServerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application_test.properties")
public class MailControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void beforeEach() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/registration")
                .params(UserUtil.createUser(0, 0, 0, 0, 0))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    void sendPasswordRecovery() throws Exception {
        List<MultiValueMap<String, String>> cases = new ArrayList<>();
        cases.add(new LinkedMultiValueMap<>() {{ add("email", UserUtil.email); }});
        cases.add(new LinkedMultiValueMap<>() {{ add("email", UserUtil.anotherEmail); }});
        cases.add(new LinkedMultiValueMap<>() {{ add("email",null); }});
        ResultMatcher[] expectations = new ResultMatcher[] {
                status().isOk(),
                status().isOk(),
                status().isBadRequest()
        };
        for (int i = 0; i < cases.size(); i++) {
            System.out.println("CASE " + i);
            mvc.perform(MockMvcRequestBuilders
                    .post("/enter/check_email")
                    .params(cases.get(i))
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(expectations[i]);
        }
    }

    @Test
    void changePasswordRecovery() throws Exception {
        User user = userRepository.findUserByEmail(UserUtil.email);
        assertTrue(CryptoUtil.checkPassword(UserUtil.psw, user.getPassword().getHash()));
        mvc.perform(MockMvcRequestBuilders
                .post("/enter/change_password")
                .param("sec_key", user.getPassword().getSalt() + user.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        user = userRepository.findUserByEmail(UserUtil.email);
        assertFalse(CryptoUtil.checkPassword(UserUtil.psw, user.getPassword().getHash()));
    }
}
