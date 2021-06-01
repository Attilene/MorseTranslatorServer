package morse.translator.server.test.controllers;

import morse.translator.server.ServerApplication;
import morse.translator.server.dbms.models.User;
import morse.translator.server.dbms.repositories.UserRepository;
import morse.translator.server.test.utils.UserUtil;
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
    void enterUser() throws Exception {
        List<MultiValueMap<String, String>> cases = new ArrayList<>();
        cases.add(UserUtil.loginUser(0, 0));
        cases.add(UserUtil.loginUser(1, 0));
        cases.add(UserUtil.loginUser(2, 0));
        cases.add(UserUtil.loginUser(3, 0));
        cases.add(UserUtil.loginUser(4, 0));
        cases.add(UserUtil.loginUser(5, 0));
        cases.add(UserUtil.loginUser(0, 1));
        cases.add(UserUtil.loginUser(1, 1));
        cases.add(UserUtil.loginUser(2, 1));
        cases.add(UserUtil.loginUser(3, 1));
        cases.add(UserUtil.loginUser(4, 1));
        cases.add(UserUtil.loginUser(5, 1));
        cases.add(UserUtil.loginUser(0, 3));
        ResultMatcher[] expectations = new ResultMatcher[] {
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
        cases.add(UserUtil.createUser(0, 0, 0, 0, 0));
        cases.add(UserUtil.createUser(1, 0, 2, 1, 0));
        cases.add(UserUtil.createUser(2, 1, 0, 2, 1));
        cases.add(UserUtil.createUser(0, 2, 0, 1, 0));
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
        User user = userRepository.findUserByEmail(UserUtil.email);
        assertEquals(UserUtil.firstName, user.getFirst_name());
        assertEquals(UserUtil.lastName, user.getLast_name());
        MultiValueMap<String, String> new_user = UserUtil.createUser(
                0, 0, 0, 0, 0);
        new_user.add("id", user.getId().toString());
        new_user.replace("first_name", Collections.singletonList(prefix + UserUtil.firstName));
        new_user.replace("last_name", Collections.singletonList(prefix + UserUtil.lastName));
        mvc.perform(MockMvcRequestBuilders
                .put("/user")
                .params(new_user)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()
        );
        user = userRepository.findUserByEmail(UserUtil.email);
        assertEquals(prefix + UserUtil.firstName, user.getFirst_name());
        assertEquals(prefix + UserUtil.lastName, user.getLast_name());
    }

    @Test
    void deleteUser() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        User user = userRepository.findUserByEmail(UserUtil.email);
        params.add("id", user.getId().toString());
        params.add("password", UserUtil.psw);
        mvc.perform(MockMvcRequestBuilders
                .delete("/user")
                .params(params)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());
        assertNull(userRepository.findUserByEmail(UserUtil.email));
        assertEquals(0, userRepository.count());
    }
}
