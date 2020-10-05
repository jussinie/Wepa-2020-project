package OldSchoolLinkedIn;

import OldSchoolLinkedIn.accounts.Account;
import OldSchoolLinkedIn.posting.Post;
import OldSchoolLinkedIn.posting.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillLikeRepository extends JpaRepository<SkillLike, Long> {

    Long countBySkillId(Long id);
    SkillLike findBySkillAndAccount(Skill skill, Account account);

}
