package OldSchoolLinkedIn;

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

public class Skill extends AbstractPersistable<Long> {

    private String skillName;
    @ManyToOne
    private Account account;

}
