package OldSchoolLinkedIn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PostComment extends AbstractPersistable<Long> {

    private String commentContent;
    @ManyToOne
    private Account account;
    @ManyToOne
    private Post post;

}
