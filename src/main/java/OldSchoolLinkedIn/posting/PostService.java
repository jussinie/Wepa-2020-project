package OldSchoolLinkedIn.posting;

import OldSchoolLinkedIn.posting.Post;
import OldSchoolLinkedIn.posting.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> list() {
        return postRepository.findAll();
    }

}
