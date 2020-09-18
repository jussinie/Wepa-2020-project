package OldSchoolLinkedIn.accounts;

import OldSchoolLinkedIn.accounts.Account;
import OldSchoolLinkedIn.accounts.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> list() {
        return accountRepository.findAll();
    }

}
