package OldSchoolLinkedIn;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findByAccountId(Long id);
    //List<Skill> findByAccountIdPageable(Long id, Pageable pageable);

}
