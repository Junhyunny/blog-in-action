package action.in.blog.controller;

import action.in.blog.MockUsers;
import action.in.blog.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({SecurityConfig.class, MockUsers.class})
@WebMvcTest(controllers = {UserController.class})
class UserControllerTest {

    @Autowired
    MockMvc sut;

    UserDetails sampleUser() {
        return User.withDefaultPasswordEncoder()
                .username("jua")
                .password("123")
                .build();
    }

    @Test
    void login() throws Exception {

        sut.perform(
                        post("/app/login")
                                .param("username", "jua")
                                .param("password", "123")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/app/home"))
        ;
    }

    @Test
    void wrongCredential_login_redirectFailure() throws Exception {

        sut.perform(
                        post("/app/login")
                                .param("username", "jua")
                                .param("password", "12345")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/app/login?error"))
        ;
    }

    @Test
    void articles() throws Exception {

        sut.perform(
                        get("/app/api/articles")
                )
                .andExpect(content().string("user articles"))
        ;
    }

    @Test
    void privateArticles() throws Exception {

        sut.perform(
                        get("/app/api/private-articles")
                                .with(user(sampleUser()))
                )
                .andExpect(content().string("user's private articles"))
        ;
    }

    @Test
    void withoutAuthentication_privateArticles_redirectToLogin() throws Exception {

        sut.perform(
                        get("/app/api/private-articles")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"))
        ;
    }
}