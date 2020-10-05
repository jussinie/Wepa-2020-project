package OldSchoolLinkedIn.accounts;

import OldSchoolLinkedIn.PendingRequest;
import OldSchoolLinkedIn.PendingRequestRepository;
import OldSchoolLinkedIn.accounts.Account;
import OldSchoolLinkedIn.accounts.AccountRepository;
import OldSchoolLinkedIn.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PendingRequestRepository pendingRequestRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public List<Account> list() {
        return accountRepository.findAll();
    }

    // Tehdään tänne palvelu, joka kertoo käyttäjän kaverit
    // Haetaan käyttäjät, jotka kirjautunut on itse lisännyt.
    // Haetann ne käyttäjät, jotka ovat lisänneet kirjautuneen
    // Muodostetaan näistä lista.

    public List<Account> friendList() {
        List<Account> friends = new ArrayList<>();
        List<PendingRequest> addedFriends = pendingRequestRepository.findByAccountAddedByIdAndAccepted(authenticationService.getLoggedInId(), true);
        for (PendingRequest friend : addedFriends) {
            friends.add(friend.getAccountAdded());
        }
        List<PendingRequest> friendsWhoAdded = pendingRequestRepository.findByAccountAddedIdAndAccepted(authenticationService.getLoggedInId(), true);
        for (PendingRequest friend : friendsWhoAdded) {
            friends.add(friend.getAccountAddedBy());
        }
        return friends;
    }

}
