package OldSchoolLinkedIn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.tools.FileObject;
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

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String registrate() {

        return "registration";
    }

    @GetMapping("/profile")
    public String home(Model model) {
        model.addAttribute("skills", skillRepository.findByAccountId(authenticationService.getLoggedInId()));
        model.addAttribute("account", authenticationService.loggedInAccount());
        if (profilePictureRepository.findByUserId(authenticationService.getLoggedInId()) != null) {
            model.addAttribute("profilePic", profilePictureRepository.findByUserId(authenticationService.getLoggedInId()));
        }
        model.addAttribute("requests", pendingRequestRepository.findAll());
        return "index";
    }

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("requests", pendingRequestRepository.findAll());
        return "test";
    }


    @PostMapping("/profile")
    public String addSkill(@RequestParam String addedSkill) {
        skillRepository.save(new Skill(addedSkill, authenticationService.loggedInAccount()));
        return "redirect:/profile";
    }

    @PostMapping("/likes")
    public String addLike(@RequestParam Long postIdOfLike) {
        postLikeRepository.save(new PostLike(1, postRepository.getOne(postIdOfLike), authenticationService.loggedInAccount()));
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

    @PostMapping("/accounts/request")
    public String addRequest(@RequestParam Long addedId) {
        pendingRequestRepository.save(new PendingRequest(accountRepository.getOne(Long.valueOf(1)), accountRepository.getOne(addedId),false));
        return "redirect:/accounts";
    }

}
