package OldSchoolLinkedIn.posting;

import OldSchoolLinkedIn.Skill;
import OldSchoolLinkedIn.accounts.AccountRepository;
import OldSchoolLinkedIn.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    PostLikeRepository postLikeRepository;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/posts")
    public String showPosts(Model model) {
        List<Post> posts = postService.listPostsFromFriendsAndOwn();
        for (Post post : posts) {
            post.setAmountOfLikes(postLikeRepository.countByPostId(post.getId()));
            postRepository.save(post);
        }

        // Postauslistassa tulee näkyä 25 uusinta viestiä YHTEYDESSÄ OLEVILTA HENKILÖILTÄ!

        model.addAttribute("posts", posts);
        //Tänne pitää tehdä listaus kommenteista, jotka ladataan - maksimissaan viisi per post
        //Haetaan ensin yhteydet, sen jälkeen näytettävät postaukset ja lopuksi niihin tietty määrä kommentteja
        Pageable pageable = PageRequest.of(0,5, Sort.by("id"));
        model.addAttribute("comments", postCommentRepository.findAll());
        model.addAttribute("postLikes", postLikeRepository.findAll());
        return "posts";
    }

    @PostMapping("/posts")
    public String addPost(@RequestParam String addedPost) {
        postRepository.save(new Post(new Date(), addedPost, new ArrayList<>(), authenticationService.loggedInAccount(), new ArrayList<>(), Long.valueOf(0)));
        return "redirect:/posts";
    }

}
