package OldSchoolLinkedIn.posting;

import OldSchoolLinkedIn.accounts.Account;
import OldSchoolLinkedIn.posting.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostLike extends AbstractPersistable<Long> {

    private int likes;
    @ManyToOne
    private Post post;
    @ManyToOne
    private Account account;

}
