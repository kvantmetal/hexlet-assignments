package exercise;

import exercise.model.Post;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = this.posts.stream().toList();
        return ResponseEntity.ok()
            .header("X-Total-Count", String.valueOf(posts.size()))
            .body(posts);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable String id) {
        Optional<Post> optionalPost = posts.stream().filter(post -> post.getId().equals(id)).findFirst();

        return ResponseEntity.of(optionalPost);
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        posts.add(post);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post postData) {
        var optionalPost = posts.stream().filter(post -> post.getId().equals(id)).findFirst();

        if (optionalPost.isPresent()) {
            var post = optionalPost.get();
            post.setId(postData.getId());
            post.setTitle(postData.getTitle());
            post.setBody(postData.getBody());
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(204)).body(postData);
        }
        return ResponseEntity.ok(postData);
    }
    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
