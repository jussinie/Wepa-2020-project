package OldSchoolLinkedIn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PendingRequestRepository extends JpaRepository<PendingRequest, Long> {

    PendingRequest findByAccountAddedIdAndAccountAddedById(Long addedId, Long addedById);
    List<PendingRequest> findByAccountAddedId(Long addedId);
    List<PendingRequest> findByAccountAddedIdAndAccepted(Long addedById, boolean accepted);
    List<PendingRequest> findByAccountAddedByIdAndAccepted(Long addedId, boolean accepted);
    List<PendingRequest> findByAccepted(boolean accepted);
}
