package OldSchoolLinkedIn;

import OldSchoolLinkedIn.accounts.AccountRepository;
import OldSchoolLinkedIn.accounts.ProfilePicture;
import OldSchoolLinkedIn.accounts.ProfilePictureRepository;
import OldSchoolLinkedIn.posting.*;
import OldSchoolLinkedIn.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class DefaultController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostCommentRepository postCommentRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    ProfilePictureRepository profilePictureRepository;

    @Autowired
    PendingRequestRepository pendingRequestRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String registrate() {

        return "registration";
    }

    @GetMapping("/home")
    public String home(Model model) {
        Pageable firstPageable = PageRequest.of(0, 3, Sort.by("amountOfLikes").descending());
        Pageable secondPageable = PageRequest.of(0, 6, Sort.by("amountOfLikes").descending());
        model.addAttribute("top3skills", skillRepository.findByAccountId(authenticationService.getLoggedInId()));
        model.addAttribute("otherSkills", skillRepository.findByAccountId(authenticationService.getLoggedInId()));
        model.addAttribute("account", authenticationService.loggedInAccount());
        if (profilePictureRepository.findByUserId(authenticationService.getLoggedInId()) != null) {
            model.addAttribute("profilePic", profilePictureRepository.findByUserId(authenticationService.getLoggedInId()));
        }
        model.addAttribute("requests", pendingRequestRepository.findAll());
        model.addAttribute("accounts", accountRepository.findAll());
        return "home";
    }

    @GetMapping("/me")
    public String me(Model model) {
        Pageable firstPageable = PageRequest.of(0, 3, Sort.by("amountOfLikes").descending());
        Pageable secondPageable = PageRequest.of(0, 6, Sort.by("amountOfLikes").descending());
        model.addAttribute("top3skills", skillRepository.findByAccountId(authenticationService.getLoggedInId()));
        model.addAttribute("otherSkills", skillRepository.findByAccountId(authenticationService.getLoggedInId()));
        model.addAttribute("account", authenticationService.loggedInAccount());
        if (profilePictureRepository.findByUserId(authenticationService.getLoggedInId()) != null) {
            model.addAttribute("profilePic", profilePictureRepository.findByUserId(authenticationService.getLoggedInId()));
        }
        model.addAttribute("requests", pendingRequestRepository.findAll());
        model.addAttribute("accounts", accountRepository.findAll());
        return "index";
    }

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("requests", pendingRequestRepository.findAll());
        model.addAttribute("accounts", accountRepository.findAll());
        return "test";
    }

    // Alla testataan test-sivun toiminnallisuutta requestin hyväksymiseen tai poistamiseen

    @PostMapping("/deleteRequest")
    public String deleteConnection(@RequestParam Long deleteRequestId) {
        pendingRequestRepository.delete(pendingRequestRepository.getOne(deleteRequestId));
        return "redirect:/test";
    }

    @PostMapping("/profile")
    public String addSkill(@RequestParam String addedSkill) {
        skillRepository.save(new Skill(addedSkill, authenticationService.loggedInAccount(), new ArrayList<>(),Long.valueOf(0)));
        return "redirect:/profile";
    }

    @PostMapping("/likes")
    public String addLike(@RequestParam Long postIdOfLike) {
        if (postLikeRepository.findByPostAndAccount(postRepository.getOne(postIdOfLike), authenticationService.loggedInAccount()) == null) {
            postLikeRepository.save(new PostLike(1, postRepository.getOne(postIdOfLike), authenticationService.loggedInAccount()));
        }
        return "redirect:/posts";
    }

    @PostMapping("/comments")
    public String addComment(@RequestParam String commentContent, @RequestParam Long postId) {
        postCommentRepository.save(new PostComment(commentContent, authenticationService.loggedInAccount(), postRepository.getOne(postId)));
        return "redirect:/posts";
    }

    @GetMapping(path = "/profilePictures/{id}/content", produces = "image/gif")
    @ResponseBody
    public byte[] get(@PathVariable long id) {
        return profilePictureRepository.getOne(id).getContent();
    }

    @PostMapping("/profilePictures")
    public String save(@RequestParam("file") MultipartFile image) throws IOException {
        ProfilePicture io = new ProfilePicture();

        io.setName(image.getOriginalFilename());
        io.setMediaType(image.getContentType());
        io.setContentLength(image.getSize());
        io.setContent(image.getBytes());
        io.setUserId(authenticationService.getLoggedInId());

        profilePictureRepository.save(io);
        return "redirect:/";
    }

    @PostMapping("/accounts/addRequest")
    public String addRequest(@RequestParam Long addedId) {
        pendingRequestRepository.save(new PendingRequest(accountRepository.getOne(addedId), accountRepository.getOne(authenticationService.getLoggedInId()),false));
        return "redirect:/accounts";
    }

    @PostMapping("/accounts/removeRequest")
    public String removeRequest(@RequestParam Long removedId) {
        pendingRequestRepository.delete(pendingRequestRepository.findByAccountAddedIdAndAccountAddedById(removedId, authenticationService.getLoggedInId()));
        return "redirect:/accounts";
    }

    @PostMapping("/accounts/cancelRequest")
    public String cancelRequest(@RequestParam Long cancelId) {
        pendingRequestRepository.delete(pendingRequestRepository.findByAccountAddedIdAndAccountAddedById(authenticationService.getLoggedInId(), cancelId));
        return "redirect:/accounts";
    }

    @PostMapping("/accounts/acceptRequest")
    public String acceptRequest(@RequestParam Long acceptRequest) {
        // Tähän toiminnallisuus uuden rivin luomiseksi connections-tauluun!
        PendingRequest pendingRequest = pendingRequestRepository.findByAccountAddedIdAndAccountAddedById(authenticationService.getLoggedInId(), acceptRequest);
        pendingRequest.setAccepted(true);
        pendingRequestRepository.save(pendingRequest);
        return "redirect:/accounts";
    }

}
