package morse.translator.server.test.controllers;

import morse.translator.server.ServerApplication;
import morse.translator.server.dbms.models.History;
import morse.translator.server.dbms.models.User;
import morse.translator.server.dbms.repositories.HistoryRepository;
import morse.translator.server.dbms.repositories.UserRepository;
import morse.translator.server.test.utils.HistoryUtil;
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
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ServerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application_test.properties")
public class HistoryControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HistoryRepository historyRepository;

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
    void getHistories() throws Exception {
        User user = userRepository.findUserByEmail(UserUtil.email);
        mvc.perform(MockMvcRequestBuilders
                .post("/history")
                .params(HistoryUtil.createHistory(user.getId(), 0, true, true))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders
                .post("/histories")
                .param("user_id", user.getId().toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals(1, historyRepository.count());
    }

    @Test
    void addHistory() throws Exception {
        User user = userRepository.findUserByEmail(UserUtil.email);
        List<MultiValueMap<String, String>> cases = new ArrayList<>();
        cases.add(HistoryUtil.createHistory(user.getId(), 0, true, true));
        cases.add(HistoryUtil.createHistory(user.getId(), 1, true, true));
        cases.add(HistoryUtil.createHistory(user.getId(), 2, true, true));
        cases.add(HistoryUtil.createHistory(user.getId(), 3, true, true));
        cases.add(HistoryUtil.createHistory(user.getId(), 0, true, false));
        cases.add(HistoryUtil.createHistory(user.getId(), 2, false, true));
        cases.add(HistoryUtil.createHistory(user.getId(), 2, false, false));
        ResultMatcher[] expectations = new ResultMatcher[] {
                status().isOk(),
                status().isOk(),
                status().isOk(),
                status().isBadRequest(),
                status().isOk(),
                status().isOk(),
                status().isOk()
        };
        for (int i = 0; i < cases.size(); i++) {
            System.out.println("CASE " + i);
            mvc.perform(MockMvcRequestBuilders
                    .post("/history")
                    .params(cases.get(i))
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(expectations[i]);
        }
        assertEquals(6, historyRepository.count());
    }

    @Test
    void deleteHistory() throws Exception {
        User user = userRepository.findUserByEmail(UserUtil.email);
        mvc.perform(MockMvcRequestBuilders
                .post("/history")
                .params(HistoryUtil.createHistory(user.getId(), 0, true, true))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        History history = historyRepository.findByUserId(user.getId()).get(0);
        mvc.perform(MockMvcRequestBuilders
                .delete("/history")
                .param("id", history.getId().toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals(0, historyRepository.count());
    }
}
