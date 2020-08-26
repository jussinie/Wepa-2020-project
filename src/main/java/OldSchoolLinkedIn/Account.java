package OldSchoolLinkedIn;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractPersistable<Long> {

    private String userName;
    private String password;
    private String realName;
    private String accountName;
    @OneToMany(mappedBy = "account")
    private List<Skill> skills = new ArrayList<>();
    @OneToMany(mappedBy = "account")
    private List<Post> posts = new ArrayList<>();
    @OneToMany(mappedBy = "account")
    private List<PostComment> postComments = new ArrayList<>();
    @OneToMany(mappedBy = "account")
    private List<PostLike> postLikes = new ArrayList<>();
    @OneToMany(mappedBy = "accountAdded")
    private List<PendingRequest> accountsAdded = new ArrayList<>();
    @OneToMany(mappedBy = "accountAddedBy")
    private List<PendingRequest> accountsAddedBy = new ArrayList<>();

}
