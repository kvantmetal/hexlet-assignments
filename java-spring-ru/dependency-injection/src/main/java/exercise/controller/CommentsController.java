package exercise.controller;

import exercise.model.Comment;
import jakarta.persistence.EntityListeners;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import exercise.repository.CommentRepository;

import java.util.List;

// BEGIN
@RestController
@EntityListeners(AuditingEntityListener.class)
@RequestMapping("/comments")
public class CommentsController {
    private final CommentRepository commentRepository;

    public CommentsController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Comment>> findAllComments() {
        return ResponseEntity.ok(commentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> findCommentById(@PathVariable Long id) {
        return ResponseEntity.of(commentRepository.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentRepository.save(commentRepository.save(comment)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        comment.setId(id);
        return ResponseEntity.ok(commentRepository.save(commentRepository.save(comment)));
    }

    @DeleteMapping("/{id}")
    public void deleteCommentById(@PathVariable Long id) {
        commentRepository.deleteById(id);
    }
}
// END
