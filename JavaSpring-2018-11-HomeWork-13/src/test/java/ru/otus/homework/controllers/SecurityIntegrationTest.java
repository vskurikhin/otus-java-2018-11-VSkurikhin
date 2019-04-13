package ru.otus.homework.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.homework.configs.ApplicationConfig;
import ru.otus.homework.configs.YamlApplicationProperties;
import ru.otus.homework.dao.UserProfileDao;
import ru.otus.homework.models.UserProfile;
import ru.otus.homework.services.DatabaseService;

import javax.servlet.Filter;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.homework.controllers.Constants.*;
import static ru.otus.homework.security.Constants.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest({
    AuthorsController.class, AuthorsRestController.class,
    BooksController.class, BooksRestController.class,
    ReviewsController.class, ReviewsRestController.class,
    LoginController.class
})
public class SecurityIntegrationTest
{
    public static final String TEST_USER = "user";

    public static final String TEST_USER_PASSWORD_PLAIN = "123456";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    @MockBean
    UserProfileDao userProfileDao;

    @MockBean
    DatabaseService databaseService;

    @Autowired
    ApplicationConfig applicationConfig;

    @Autowired
    YamlApplicationProperties yamlApplicationProperties;

    MockMvc mvc;

    @BeforeEach
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .addFilters(springSecurityFilterChain)
            .apply(springSecurity()).build();
        when(userProfileDao.findByLogin(TEST_USER)).thenReturn(Optional.of(
            new UserProfile(1L, TEST_USER, userPasswordEncoded(), false, false)
        ));
    }

    @Test
    public void notAuthenticatedAccess() throws Exception
    {
        mvc.perform(get(REQUEST_BOOK_AUTHORS_LIST)).andExpect(status().isFound());
        mvc.perform(get(REQUEST_AUTHOR_CREATE).param("bookId", "1")).andExpect(status().isFound());
        mvc.perform(get(REQUEST_AUTHOR_EDIT).param("authorId","1").param("bookId", "1"))
            .andExpect(status().isFound());
        mvc.perform(get("/")).andExpect(status().isFound());
        mvc.perform(get(REQUEST_BOOK_CREATE)).andExpect(status().isFound());
        mvc.perform(get(REQUEST_BOOK_EDIT).param("bookId", "1")).andExpect(status().isFound());
        mvc.perform(get(REQUEST_REVIEWS_LIST)).andExpect(status().isFound());
        mvc.perform(get(REQUEST_REVIEW_CREATE).param("bookId", "1")).andExpect(status().isFound());
        mvc.perform(get(REQUEST_REVIEW_EDIT).param("reviewId","1").param("bookId", "1"))
            .andExpect(status().isFound());

        mvc.perform(get(REST_API + REST_V1_AUTHORS + "/0")).andExpect(status().isFound());
        mvc.perform(post(REST_API + REST_V1_AUTHORS)).andExpect(status().isFound());
        mvc.perform(put(REST_API + REST_V1_AUTHORS)).andExpect(status().isFound());
        mvc.perform(delete(REST_API + REST_V1_AUTHORS + "/0")).andExpect(status().isFound());

        mvc.perform(get(REST_API + REST_V1_BOOKS)).andExpect(status().isFound());
        mvc.perform(post(REST_API + REST_V1_BOOKS)).andExpect(status().isFound());
        mvc.perform(put(REST_API + REST_V1_BOOKS)).andExpect(status().isFound());
        mvc.perform(delete(REST_API + REST_V1_BOOKS + "/0")).andExpect(status().isFound());

        mvc.perform(get(REST_API + REST_V1_REVIEWS + "/0")).andExpect(status().isFound());
        mvc.perform(post(REST_API + REST_V1_REVIEWS)).andExpect(status().isFound());
        mvc.perform(put(REST_API + REST_V1_REVIEWS)).andExpect(status().isFound());
        mvc.perform(delete(REST_API + REST_V1_REVIEWS + "/0")).andExpect(status().isFound());
    }

    @Test
    @WithMockUser
    public void authenticatedAccess() throws Exception
    {
        mvc.perform(get(REQUEST_AUTHOR_CREATE).param("bookId", "1")).andExpect(status().isOk());
        mvc.perform(get(REQUEST_AUTHOR_EDIT).param("authorId","1").param("bookId", "1"))
            .andExpect(status().isOk());
        mvc.perform(get("/")).andExpect(status().isOk());
        mvc.perform(get(REQUEST_BOOK_CREATE)).andExpect(status().isOk());
        mvc.perform(get(REQUEST_REVIEW_CREATE).param("bookId", "1")).andExpect(status().isOk());
        mvc.perform(get(REST_API + REST_V1_AUTHORS + "/0")).andExpect(status().isOk());
        mvc.perform(get(REST_API + REST_V1_BOOKS)).andExpect(status().isOk());
        mvc.perform(get(REST_API + REST_V1_REVIEWS + "/0")).andExpect(status().isOk());
    }

    @Test
    public void testLogin() throws Exception {
        mvc.perform(formLogin(REQUEST_LOGIN_PROCESS)
            .userParameter(PARAMETER_USERNAME)
            .passwordParam(PARAMETER_PASSWORD + "_TEST")
            .user(TEST_USER)
            .password(TEST_USER_PASSWORD_PLAIN)
        ).andExpect(redirectedUrl(URL_FAILURE));

        mvc.perform(formLogin(REQUEST_LOGIN_PROCESS)
            .userParameter(PARAMETER_USERNAME)
            .passwordParam(PARAMETER_PASSWORD)
            .user(TEST_USER)
            .password(TEST_USER_PASSWORD_PLAIN)
        ).andExpect(redirectedUrl("/"));
    }

    private String userPasswordEncoded()
    {
        int strength = yamlApplicationProperties.getStrength();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(strength);
        return passwordEncoder.encode(TEST_USER_PASSWORD_PLAIN);
    }

    @Test
    public void outUserPasswordPlain(){
        System.out.println(userPasswordEncoded());
    }
}
