package OldSchoolLinkedIn;

import OldSchoolLinkedIn.posting.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillLikeRepository extends JpaRepository<SkillLike, Long> {

    Long countBySkillId(Long id);
}
