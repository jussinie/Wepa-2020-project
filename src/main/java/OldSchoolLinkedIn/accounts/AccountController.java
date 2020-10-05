package OldSchoolLinkedIn.accounts;

import OldSchoolLinkedIn.*;
import OldSchoolLinkedIn.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    ProfilePictureRepository profilePictureRepository;

    @Autowired
    PendingRequestRepository pendingRequestRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    SkillLikeRepository skillLikeRepository;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/accounts")
    public String showAccounts(Model model) {
        //Tämä näyttää kaikki muut paitsi kirjautuneen käyttäjän
        //Tehdään täällä lista renderöitävistä asioista
        //Ensin ne, mitkä voidaan lisätä (jotka eivät ole pendingRequest-taulussa)
        List<Account> otherAccounts = accountRepository.findByIdNot(authenticationService.getLoggedInId());
        List<PendingRequest> pendingRequests = pendingRequestRepository.findAll();
        List<Long> requestedIds = new ArrayList<>(); // Luodaan lista lisätyille id:lle
        List<Account> renderedAccounts = new ArrayList<>(); // Luodaan lista renderöitäville, ei vielä lisätyille käyttäjille
        List<Account> requestedAccounts = new ArrayList<>(); // Luodaan lista pyydetyille käyttäjille
        List<PendingRequest> requestsForYou = pendingRequestRepository.findByAccountAddedId(authenticationService.getLoggedInId());
        List<Long> idsWhoRequestedYou = new ArrayList<>();
        List<Account> renderedRequestsForYou = new ArrayList<>();
        List<PendingRequest> friendsRequests = new ArrayList<>();
        List<Account> friends = new ArrayList<>();

        // Seuraava luo listan käyttäjän lisäämistä id:stä
        for (PendingRequest rq : pendingRequests) {
            requestedIds.add(rq.getAccountAdded().getId());
        }

        // Tässä luodaan lista niistä id:stä, jotka ovat lisänneet rekisteröityneen käyttäjän
        for (PendingRequest rqForYou : requestsForYou) {
            idsWhoRequestedYou.add(rqForYou.getAccountAddedBy().getId());
        }

        // Tässä luodaan renderöitävä lista accounteista, jotka ovat lisänneet käyttäjän
        for (Account account : otherAccounts) {
            if (idsWhoRequestedYou.contains(account.getId())) {
                renderedRequestsForYou.add(account);
            }
        }

        // Tässä luodaan renderöitävä lista tileistä, jotka kirjautunut käyttäjä on lisännyt ja joita hän ei ole.
        // Jälkimmäisestä on poistettava tili siinä tapauksessa, että kirjautunut käyttäjä on jo lisännyt sen.
        for (Account account : otherAccounts) {
            if (requestedIds.contains(account.getId())) {
                requestedAccounts.add(account);
            } else if (!requestedIds.contains(account.getId()) && !idsWhoRequestedYou.contains(account.getId())) {
                renderedAccounts.add(account);
            }
        }

        model.addAttribute("accounts", renderedAccounts);
        model.addAttribute("requested", requestedAccounts);
        model.addAttribute("requestsForYou", renderedRequestsForYou);
        model.addAttribute("friends", pendingRequestRepository.findByAccepted(true));

        model.addAttribute("loggedInId", authenticationService.getLoggedInId());
        return "accounts";
    }

    @PostMapping("/registration")
    public String addAccount(@RequestParam String username, @RequestParam String password, @RequestParam String realName, @RequestParam String accountName) {
        //Tänhän voisi tehdä sillä modelilla!
        accountRepository.save(new Account(username, passwordEncoder.encode(password), realName, accountName, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        return "redirect:/accounts";
    }

    @GetMapping("/accounts/{accountName}")
    public String showAccount(Model model, @PathVariable String accountName) {
        //Ensin haetaan account, kenen profiilia ja profiilikuvaa katsellaan
        model.addAttribute("account", accountRepository.findByAccountName(accountName));
        model.addAttribute("profilePic", profilePictureRepository.getOne(Long.valueOf(3)));

        //Sitten haetaan taidot, mitä tällä käyttäjällä on
        List<Skill> skills = skillRepository.findByAccountId(accountRepository.findByAccountName(accountName).getId());

        //Halutaan ottaa vain ne liket, jotka liittyvät näihin skilleihin tällä käyttäjällä. Skillit valittu tuohon skillsiin

        for (Skill skill : skills) {
            skill.setAmountOfLikes(skillLikeRepository.countBySkillId(skill.getId()));
            skillRepository.save(skill);
        }
        model.addAttribute("skills", skills);
        return "profilePage";
    }

    @PostMapping("/accounts/{accountName}")
    public String likeSkill(@RequestParam Long likeSkillId) {
        if (skillLikeRepository.findBySkillAndAccount(skillRepository.getOne(likeSkillId), authenticationService.loggedInAccount()) == null) {
            SkillLike like = new SkillLike(1, skillRepository.getOne(likeSkillId), authenticationService.loggedInAccount());
            skillLikeRepository.save(like);
        }
        return "redirect:/accounts/{accountName}";
    }

}
