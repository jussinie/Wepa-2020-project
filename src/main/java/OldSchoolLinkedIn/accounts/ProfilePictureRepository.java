package OldSchoolLinkedIn.accounts;

import OldSchoolLinkedIn.accounts.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Long> {

    ProfilePicture findByUserId(Long userId);



}
