package OldSchoolLinkedIn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

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

    @GetMapping("/accounts")
    public String showAccounts(Model model) {
        model.addAttribute("accounts", accountService.list());
        model.addAttribute("requests", pendingRequestRepository.findAll());
        return "accounts";
    }

    @PostMapping("/accounts")
    public String addAccount(@RequestParam String username, @RequestParam String password, @RequestParam String realName, @RequestParam String accountName) {
        //Tänhän voisi tehdä sillä modelilla!
        accountRepository.save(new Account(username, passwordEncoder.encode(password), realName, accountName, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        return "redirect:/accounts";
    }

    @GetMapping("/accounts/{accountName}")
    public String showAccount(Model model, @PathVariable String accountName) {
        model.addAttribute("account", accountRepository.findByAccountName(accountName));
        model.addAttribute("profilePic", profilePictureRepository.getOne(Long.valueOf(3)));
        return "profilePage";
    }

}
