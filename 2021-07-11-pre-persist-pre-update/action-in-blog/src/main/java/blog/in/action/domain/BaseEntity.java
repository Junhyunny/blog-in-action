package blog.in.action.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Log4j2
@Getter
@NoArgsConstructor
@MappedSuperclass
class BaseEntity {

    @Column(updatable = false, nullable = false)
    protected LocalDateTime createAt;
    @Column(nullable = false)
    protected LocalDateTime updatedAt;

    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createAt = now;
        updatedAt = now;
    }

    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
