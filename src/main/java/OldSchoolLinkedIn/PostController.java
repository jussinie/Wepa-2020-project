package OldSchoolLinkedIn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;

@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Autowired
    PostCommentRepository postCommentRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/posts")
    public String showPosts(Model model) {
        model.addAttribute("posts", postService.list());
        model.addAttribute("comments", postCommentRepository.findAll());
        return "posts";
    }

    @PostMapping("/posts")
    public String addPost(@RequestParam String addedPost) {
        postRepository.save(new Post(new Date(), addedPost, new ArrayList<>(), authenticationService.loggedInAccount(), new ArrayList<>()));
        return "redirect:/posts";
    }

}
