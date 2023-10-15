package action.in.blog.controller;

import action.in.blog.domain.Profile;
import action.in.blog.service.ProfileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/profiles/image")
    public void uploadProfileImage(MultipartFile file) {
        profileService.uploadProfileImage(file);
    }

    @PostMapping("/profiles/images")
    public void uploadProfileImages(List<MultipartFile> files) {
        profileService.uploadProfileImages(files);
    }

    @PostMapping("/profiles")
    public void uploadProfile(Profile profile) {
        profileService.uploadProfile(profile);
    }
}
