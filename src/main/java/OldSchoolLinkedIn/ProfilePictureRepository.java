package OldSchoolLinkedIn;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Long> {

    ProfilePicture findByUserId(Long userId);



}
