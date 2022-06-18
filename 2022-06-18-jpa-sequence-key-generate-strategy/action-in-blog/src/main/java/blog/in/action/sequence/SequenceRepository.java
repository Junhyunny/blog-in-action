package blog.in.action.sequence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SequenceRepository extends JpaRepository<SequenceEntity, Long> {
}
