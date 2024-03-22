package exercise;

import exercise.model.Post;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public List<Post> index(@RequestParam(defaultValue = "10") Integer limit) {
        return posts.stream().limit(limit).toList();
    }

    @PostMapping("/posts")
    public Post create(@RequestBody Post post) {
        posts.add(post);
        return post;
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> show(@PathVariable String id) {
        return posts.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @PutMapping("/posts/{id}")
    public Post update(@PathVariable String id, @RequestBody Post postData) {
        var optionalPost = posts.stream().filter(p -> p.getId().equals(id)).findFirst();

        if (optionalPost.isPresent()) {
            var post = optionalPost.get();
            post.setId(postData.getId());
            post.setBody(postData.getBody());
            post.setTitle(postData.getTitle());
        }
        return postData;
    }

    @DeleteMapping("/posts/{id}")
    public void delete(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }

    // END
}
