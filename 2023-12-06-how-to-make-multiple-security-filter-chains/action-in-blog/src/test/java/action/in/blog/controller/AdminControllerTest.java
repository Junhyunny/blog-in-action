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
@WebMvcTest(controllers = {AdminController.class})
class AdminControllerTest {

    @Autowired
    MockMvc sut;

    UserDetails sampleAdmin() {
        return User.withDefaultPasswordEncoder()
                .username("junhyunny")
                .password("123")
                .roles("ADMIN")
                .build();
    }

    UserDetails sampleUser() {
        return User.withDefaultPasswordEncoder()
                .username("jua")
                .password("123")
                .roles("USER")
                .build();
    }

    @Test
    void login() throws Exception {

        sut.perform(
                        post("/admin/login")
                                .param("username", "junhyunny")
                                .param("password", "123")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/home"))
        ;
    }

    @Test
    void wrongCredential_login_redirectFailure() throws Exception {

        sut.perform(
                        post("/admin/login")
                                .param("username", "junhyunny")
                                .param("password", "12345")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/login?error"))
        ;
    }

    @Test
    void articles() throws Exception {

        sut.perform(
                        get("/admin/api/articles")
                                .with(user(sampleAdmin()))
                )
                .andExpect(content().string("admin articles"))
        ;
    }

    @Test
    void withoutAuthentication_articles_redirectToLogin() throws Exception {

        sut.perform(
                        get("/admin/api/articles")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"))
        ;
    }

    @Test
    void withoutAuthorization_articles_statusForbidden() throws Exception {

        sut.perform(
                        get("/admin/api/articles")
                                .with(user(sampleUser()))
                )
                .andExpect(status().isForbidden())
        ;
    }
}