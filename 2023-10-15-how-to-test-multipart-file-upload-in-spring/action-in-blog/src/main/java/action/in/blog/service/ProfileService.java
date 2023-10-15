package action.in.blog.service;

import action.in.blog.domain.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileService {

    void uploadProfileImage(MultipartFile file);

    void uploadProfileImages(List<MultipartFile> files);

    void uploadProfile(Profile profile);
}

@Service
class DefaultProfileService implements ProfileService {

    @Override
    public void uploadProfileImage(MultipartFile file) {

    }

    @Override
    public void uploadProfileImages(List<MultipartFile> files) {

    }

    @Override
    public void uploadProfile(Profile profile) {

    }
}