package OldSchoolLinkedIn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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


    @JsonIgnore
    @ManyToOne
    private Account accountAdded;
    @JsonIgnore
    @ManyToOne
    private Account accountAddedBy;
    private boolean accepted;



}
