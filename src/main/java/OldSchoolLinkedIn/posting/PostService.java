package OldSchoolLinkedIn.posting;

import OldSchoolLinkedIn.accounts.AccountService;
import OldSchoolLinkedIn.posting.Post;
import OldSchoolLinkedIn.posting.PostRepository;
import OldSchoolLinkedIn.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationService authenticationService;

    public List<Post> list() {
        return postRepository.findAll();
    }

    public List<Post> listPostsFromFriendsAndOwn() {
        List<Post> allPosts = postRepository.findAll();
        List<Post> postsFromFriendsAndOwn = new ArrayList<>();
        for (Post post : allPosts) {
            if (accountService.friendList().contains(post.getAccount())) {
                postsFromFriendsAndOwn.add(post);
            } else if (post.getAccount().equals(authenticationService.loggedInAccount())) {
                postsFromFriendsAndOwn.add(post);
            }
        }
        return postsFromFriendsAndOwn;
    }

}
