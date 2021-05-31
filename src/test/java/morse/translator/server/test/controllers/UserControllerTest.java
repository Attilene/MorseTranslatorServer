package morse.translator.server.test.controllers;

import morse.translator.server.ServerApplication;
import morse.translator.server.dbms.models.User;
import morse.translator.server.dbms.repositories.UserRepository;
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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ServerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application_test.properties")
public class UserControllerTest {
    static String login = "attilene";
    static String anotherLogin = "t1mon";

    static String email = "example@mail.ru";
    static String invalidEmail = "example mail.ru";
    static String anotherEmail = "example1@mail.ru";

    static String psw = "password1";
    static String invalidPsw = "password";
    static String anotherPsw = "password12";

    static String firstName = "Example name";
    static String lastName = "Example surname";

    static String phoneNumber = "89563125890";
    static String invalidPhoneNumber = "345f2345";

    static String birthday = "2001-04-08";
    static String invalidBirthday = "2013-1sd-3";

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void beforeEach() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/registration")
                .params(createUser(0, 0, 0, 0, 0))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    void enterUser() throws Exception {
        List<MultiValueMap<String, String>> cases = new ArrayList<>();
        cases.add(loginUser(0, 0));
        cases.add(loginUser(1, 0));
        cases.add(loginUser(2, 0));
        cases.add(loginUser(3, 0));
        cases.add(loginUser(4, 0));
        cases.add(loginUser(5, 0));
        cases.add(loginUser(0, 1));
        cases.add(loginUser(1, 1));
        cases.add(loginUser(2, 1));
        cases.add(loginUser(3, 1));
        cases.add(loginUser(4, 1));
        cases.add(loginUser(5, 1));
        cases.add(loginUser(0, 3));
        ResultMatcher[] expectations = new ResultMatcher[]{
                status().isOk(),
                status().isOk(),
                status().isOk(),
                status().isOk(),
                status().isOk(),
                status().isBadRequest(),
                status().isOk(),
                status().isOk(),
                status().isOk(),
                status().isOk(),
                status().isOk(),
                status().isBadRequest(),
                status().isBadRequest()
        };
        for (int i = 0; i < cases.size(); i++) {
            System.out.println("CASE " + i);
            mvc.perform(MockMvcRequestBuilders
                    .post("/enter")
                    .params(cases.get(i))
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(expectations[i]);
        }
    }

    @Test
    void registrationUser() throws Exception {
        List<MultiValueMap<String, String>> cases = new ArrayList<>();
        cases.add(createUser(0, 0, 0, 0, 0));
        cases.add(createUser(1, 0, 2, 1, 0));
        cases.add(createUser(2, 1, 0, 2, 1));
        cases.add(createUser(0, 2, 0, 1, 0));
        ResultMatcher[] expectations = new ResultMatcher[]{
                status().isOk(),
                status().isBadRequest(),
                status().isBadRequest(),
                status().isOk()
        };
        for (int i = 0; i < cases.size(); i++) {
            System.out.println("CASE " + i);
            mvc.perform(MockMvcRequestBuilders
                    .post("/registration")
                    .params(cases.get(i))
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(expectations[i]);
        }
        assertEquals(1, userRepository.count());
    }

    @Test
    void updateUser() throws Exception {
        String prefix = "new ";
        User user = userRepository.findUserByEmail(email);
        assertEquals(firstName, user.getFirst_name());
        assertEquals(lastName, user.getLast_name());
        MultiValueMap<String, String> new_user = createUser(0, 0, 0, 0, 0);
        new_user.add("id", user.getId().toString());
        new_user.replace("first_name", Collections.singletonList(prefix + firstName));
        new_user.replace("last_name", Collections.singletonList(prefix + lastName));
        mvc.perform(MockMvcRequestBuilders
                .put("/user")
                .params(new_user)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()
        );
        user = userRepository.findUserByEmail(email);
        assertEquals(prefix + firstName, user.getFirst_name());
        assertEquals(prefix + lastName, user.getLast_name());
    }

    @Test
    void deleteUser() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        User user = userRepository.findUserByEmail(email);
        params.add("id", user.getId().toString());
        params.add("password", psw);
        mvc.perform(MockMvcRequestBuilders
                .delete("/user")
                .params(params)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());
        assertNull(userRepository.findUserByEmail(email));
        assertEquals(0, userRepository.count());
    }

    static MultiValueMap<String, String> loginUser(
            int loginEmailType,
            int passwordType
    ) throws IllegalStateException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("login_email",
                switch (loginEmailType) {
                    case 0 -> login;
                    case 1 -> anotherLogin;
                    case 2 -> email;
                    case 3 -> anotherEmail;
                    case 4 -> invalidEmail;
                    case 5 -> null;
                    default -> throw new IllegalStateException("Unexpected value: " + loginEmailType);
                });
        map.add("password",
                switch (passwordType) {
                    case 0 -> psw;
                    case 1 -> anotherPsw;
                    case 2 -> invalidPsw;
                    case 3 -> null;
                    default -> throw new IllegalStateException("Unexpected value: " + passwordType);
                });
        return map;
    }

    static MultiValueMap<String, String> createUser(
            int loginType,
            int emailType,
            int phoneNumberType,
            int birthdayType,
            int passwordType
    ) throws IllegalStateException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("first_name", firstName);
        map.add("last_name", lastName);
        map.add("login",
                switch (loginType) {
                    case 0 -> login;
                    case 1 -> anotherLogin;
                    case 2 -> null;
                    default -> throw new IllegalStateException("Unexpected value: " + loginType);
        });
        map.add("email",
                switch (emailType) {
                    case 0 -> email;
                    case 1 -> anotherEmail;
                    case 2 -> invalidEmail;
                    case 3 -> null;
                    default -> throw new IllegalStateException("Unexpected value: " + emailType);
        });
        map.add("phone_number",
                switch (phoneNumberType) {
                    case 0 -> phoneNumber;
                    case 1 -> invalidPhoneNumber;
                    case 2 -> null;
                    default -> throw new IllegalStateException("Unexpected value: " + phoneNumberType);
                });
        map.add("birthday",
                switch (birthdayType) {
                    case 0 -> birthday;
                    case 1 -> invalidBirthday;
                    case 2 -> null;
                    default -> throw new IllegalStateException("Unexpected value: " + birthdayType);
                });
        map.add("password",
                switch (passwordType) {
                    case 0 -> psw;
                    case 1 -> anotherPsw;
                    case 2 -> invalidPsw;
                    case 3 -> null;
                    default -> throw new IllegalStateException("Unexpected value: " + passwordType);
                });
        return map;
    }
}
