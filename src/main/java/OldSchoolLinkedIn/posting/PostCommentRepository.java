package OldSchoolLinkedIn.posting;

import OldSchoolLinkedIn.posting.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}
