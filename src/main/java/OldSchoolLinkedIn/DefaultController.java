package OldSchoolLinkedIn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("skills", skillRepository.findAll());
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
        postCommentRepository.save(new PostComment(commentContent, accountRepository.getOne(Long.valueOf(1)), postRepository.getOne(postId)));
        return "redirect:/posts";
    }


    @GetMapping("/accounts")
    public String showAccounts() {
        return "accounts";
    }

    @PostMapping("/accounts")
    public String addAccount(@RequestParam String username, @RequestParam String password, @RequestParam String realName, @RequestParam String accountName) {
        //Tänhän voisi tehdä sillä modelilla!
        accountRepository.save(new Account(username, password, realName, accountName, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        return "redirect:/accounts";
    }

}
