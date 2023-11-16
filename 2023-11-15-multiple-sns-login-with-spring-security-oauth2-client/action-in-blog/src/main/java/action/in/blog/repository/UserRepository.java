package action.in.blog.repository;

import action.in.blog.domain.entity.UserEntity;
import action.in.blog.domain.enums.LoginType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserByOauth2ClientIdAndLoginType(String oauth2Id, LoginType loginType);
}
