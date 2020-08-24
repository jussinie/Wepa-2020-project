package OldSchoolLinkedIn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PendingRequest extends AbstractPersistable<Long> {

    @ManyToOne
    private Account accountAdded;
    @ManyToOne
    private Account accountAddedBy;
    private boolean accepted;



}
