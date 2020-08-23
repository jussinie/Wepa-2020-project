package OldSchoolLinkedIn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePicture extends AbstractPersistable<Long> {

    private String name;
    private String mediaType;
    private Long size;
    private Long userId;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;


}
