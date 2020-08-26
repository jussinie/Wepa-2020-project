package OldSchoolLinkedIn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    AccountRepository accountRepository;

    public Account loggedInAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = authentication.getName();
        return accountRepository.findByUserName(loggedUsername);
    }

    public Long getLoggedInId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = authentication.getName();
        Account loggedInAccount = accountRepository.findByUserName(loggedUsername);
        return loggedInAccount.getId();
    }

}
