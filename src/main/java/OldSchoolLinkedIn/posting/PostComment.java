package OldSchoolLinkedIn.posting;

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
@NoArgsConstructor
@AllArgsConstructor

public class PostComment extends AbstractPersistable<Long> {

    private String commentContent;
    @ManyToOne
    private Account account;
    @ManyToOne
    private Post post;

}
