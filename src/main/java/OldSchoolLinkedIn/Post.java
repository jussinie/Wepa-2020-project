package OldSchoolLinkedIn;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Post extends AbstractPersistable<Long> {

    private Date postingTime;
    private String content;
    @OneToMany(mappedBy = "post")
    private List<PostComment> postComments = new ArrayList<>();
    @ManyToOne
    private Account account;
    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikes = new ArrayList<>();

}
