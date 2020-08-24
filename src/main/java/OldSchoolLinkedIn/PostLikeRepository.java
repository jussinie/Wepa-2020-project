package OldSchoolLinkedIn;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Long countByPostId(Long Id);

}
