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
        model.addAttribute("accounts", accountRepository.findByIdNot(authenticationService.getLoggedInId()));
        model.addAttribute("requests", pendingRequestRepository.findAll());
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
        SkillLike like = new SkillLike(1, skillRepository.getOne(likeSkillId), authenticationService.loggedInAccount());
        skillLikeRepository.save(like);
        return "redirect:/accounts/{accountName}";
    }
}
