package OldSchoolLinkedIn.accounts;

import OldSchoolLinkedIn.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountName(String accountName);
    Account findByUserName(String userName);
    List<Account> findByIdNot(Long id);

}
