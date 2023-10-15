package action.in.blog.controller;

import action.in.blog.domain.Profile;
import action.in.blog.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileControllerTest {

    ProfileService profileService;
    MockMvc sut;

    @BeforeEach
    void setUp() {
        profileService = Mockito.mock(ProfileService.class);
        sut = MockMvcBuilders
                .standaloneSetup(new ProfileController(profileService))
                .build();
    }

    @Test
    public void uploadProfileImage() throws Exception {

        var profileImage = new MockMultipartFile("file", "profile.jpg", "image/jpeg", "profile-image-binary".getBytes());


        sut.perform(
                multipart("/profiles/image")
                        .file(profileImage)
        ).andExpect(status().isOk());


        var argumentCaptor = ArgumentCaptor.forClass(MultipartFile.class);
        verify(profileService, times(1)).uploadProfileImage(argumentCaptor.capture());

        var result = argumentCaptor.getValue();
        assertEquals("profile.jpg", result.getOriginalFilename());
        assertEquals("image/jpeg", result.getContentType());
        assertArrayEquals("profile-image-binary".getBytes(), result.getBytes());
    }

    @Test
    public void uploadProfileImages() throws Exception {

        var profileImage = new MockMultipartFile("files", "profile.jpg", "image/jpeg", "profile-image-binary".getBytes());
        var backgroundImage = new MockMultipartFile("files", "background.jpg", "image/jpeg", "background-image-binary".getBytes());


        sut.perform(
                multipart("/profiles/images")
                        .file(profileImage)
                        .file(backgroundImage)
        ).andExpect(status().isOk());


        var argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(profileService, times(1)).uploadProfileImages(argumentCaptor.capture());

        var result = (List<MultipartFile>) argumentCaptor.getValue();
        assertEquals(2, result.size());
        assertEquals("profile.jpg", result.get(0).getOriginalFilename());
        assertEquals("image/jpeg", result.get(0).getContentType());
        assertArrayEquals("profile-image-binary".getBytes(), result.get(0).getBytes());
        assertEquals("background.jpg", result.get(1).getOriginalFilename());
        assertEquals("image/jpeg", result.get(1).getContentType());
        assertArrayEquals("background-image-binary".getBytes(), result.get(1).getBytes());
    }

    @Test
    public void uploadProfile() throws Exception {

        var profileImage = new MockMultipartFile("picture", "profile.jpg", "image/jpeg", "profile-image-binary".getBytes());


        sut.perform(
                multipart("/profiles")
                        .file(profileImage)
                        .param("name", "junhyunny")
                        .param("age", "20")
        ).andExpect(status().isOk());


        var argumentCaptor = ArgumentCaptor.forClass(Profile.class);
        verify(profileService, times(1)).uploadProfile(argumentCaptor.capture());

        var result = argumentCaptor.getValue();
        assertEquals("junhyunny", result.name());
        assertEquals(20, result.age());
        assertEquals("profile.jpg", result.picture().getOriginalFilename());
        assertEquals("image/jpeg", result.picture().getContentType());
        assertArrayEquals("profile-image-binary".getBytes(), result.picture().getBytes());
    }
}