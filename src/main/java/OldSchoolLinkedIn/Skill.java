package OldSchoolLinkedIn;

import OldSchoolLinkedIn.accounts.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Skill extends AbstractPersistable<Long> {

    private String skillName;
    @ManyToOne
    private Account account;
    @OneToMany(mappedBy = "skill")
    private List<SkillLike> skillLikes = new ArrayList<>();
    private Long amountOfLikes;

}
