package OldSchoolLinkedIn.posting;

import OldSchoolLinkedIn.accounts.Account;
import OldSchoolLinkedIn.posting.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Long countByPostId(Long Id);
    PostLike findByPostAndAccount(Post post, Account account);

}
