package OldSchoolLinkedIn;

import OldSchoolLinkedIn.accounts.Account;
import OldSchoolLinkedIn.posting.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class SkillLike extends AbstractPersistable<Long> {

    private int likes;
    @ManyToOne
    private Skill skill;
    @ManyToOne
    private Account account;

}
