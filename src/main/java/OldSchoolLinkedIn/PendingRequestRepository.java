package OldSchoolLinkedIn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PendingRequestRepository extends JpaRepository<PendingRequest, Long> {

    //List<PendingRequest> findByAccountAdded(Long Id);


}
