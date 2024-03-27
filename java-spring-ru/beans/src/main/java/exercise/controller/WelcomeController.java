package exercise.controller;

import exercise.daytime.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// BEGIN
@RestController
@RequestMapping("/welcome")
public class WelcomeController {

    @Autowired
    Daytime daytime;

    @GetMapping("")
    public String getWelcomeString() {
        String daytimeName = daytime.getName();
        return "It is %s now! Welcome to Spring!".formatted(daytimeName);
    }
}
// END
