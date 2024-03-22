package exercise;

import exercise.component.UserProperties;
import exercise.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class Application {

    // Все пользователи
    private List<User> users = Data.getUsers();
    @Autowired
    private UserProperties userProperties;

    // BEGIN
    @GetMapping("/admins")
    public ResponseEntity<List<String>> getAdmins() {
        List<String> admins = userProperties.getAdmins();
        List<String> adminsName = users.stream().distinct().filter(p -> admins.contains(p.getEmail())).map(p -> p.getName()).toList();
        return ResponseEntity.ok(adminsName);
    }

    // END

    @GetMapping("/users")
    public List<User> index() {
        return users;
    }

    @GetMapping("/users/{id}")
    public Optional<User> show(@PathVariable Long id) {
        var user = users.stream()
            .filter(u -> u.getId() == id)
            .findFirst();
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
