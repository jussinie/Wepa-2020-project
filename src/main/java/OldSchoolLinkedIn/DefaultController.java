package OldSchoolLinkedIn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

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

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute("account", accountRepository.getOne(Long.valueOf(2)));
       // model.addAttribute("profilePic", profilePictureRepository.getOne(Long.valueOf(8)));
        return "index";
    }

    @PostMapping("/")
    public String addSkill(@RequestParam String addedSkill) {
        skillRepository.save(new Skill(addedSkill, accountRepository.getOne(Long.valueOf(1))));
        return "redirect:/";
    }

    @GetMapping("/posts")
    public String showPosts(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        model.addAttribute("comments", postCommentRepository.findAll());
        return "posts";
    }

    @PostMapping("/posts")
    public String addPost(@RequestParam String addedPost) {
        postRepository.save(new Post(new Date(), addedPost, new ArrayList<>(), accountRepository.getOne(Long.valueOf(1)), new ArrayList<>()));
        return "redirect:/posts";
    }

    @PostMapping("/likes")
    public String addLike(@RequestParam Long postIdOfLike) {
        postLikeRepository.save(new PostLike(1, postRepository.getOne(postIdOfLike), accountRepository.getOne(Long.valueOf(1))));
        return "redirect:/posts";
    }

    @PostMapping("/comments")
    public String addComment(@RequestParam String commentContent, @RequestParam Long postId) {
        postCommentRepository.save(new PostComment(commentContent, accountRepository.getOne(Long.valueOf(2)), postRepository.getOne(postId)));
        return "redirect:/posts";
    }

    @GetMapping("/accounts")
    public String showAccounts(Model model) {
        model.addAttribute("accounts", accountRepository.findAll());
        model.addAttribute("requests", pendingRequestRepository.findAll());
        return "accounts";
    }

    @PostMapping("/accounts")
    public String addAccount(@RequestParam String username, @RequestParam String password, @RequestParam String realName, @RequestParam String accountName) {
        //Tänhän voisi tehdä sillä modelilla!
        accountRepository.save(new Account(username, password, realName, accountName, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        return "redirect:/accounts";
    }

    @GetMapping("/accounts/{accountName}")
    public String showAccount(Model model, @PathVariable String accountName) {
        model.addAttribute("account", accountRepository.findByAccountName(accountName));
        model.addAttribute("profilePic", profilePictureRepository.getOne(Long.valueOf(3)));
        return "profilePage";
    }

    @PostMapping("/profilePictures")
    public String save(@RequestParam("file") MultipartFile image) throws IOException {
        ProfilePicture io = new ProfilePicture();

        io.setName(image.getOriginalFilename());
        io.setMediaType(image.getContentType());
        io.setContentLength(image.getSize());
        io.setContent(image.getBytes());
        io.setUserId(Long.valueOf(1));

        profilePictureRepository.save(io);
        return "redirect:/";
    }

    @PostMapping("/accounts/request")
    public String addRequest(@RequestParam Long addedId) {
        pendingRequestRepository.save(new PendingRequest(accountRepository.getOne(Long.valueOf(1)), accountRepository.getOne(addedId),false));
        return "redirect:/accounts";
    }

}
