package OldSchoolLinkedIn;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountName(String accountName);
    Account findByUserName(String userName);

}
