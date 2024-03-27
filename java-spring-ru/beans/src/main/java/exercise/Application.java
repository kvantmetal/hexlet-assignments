package exercise;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

import exercise.daytime.Daytime;
import exercise.daytime.Day;
import exercise.daytime.Night;
import org.springframework.context.annotation.Bean;

// BEGIN

// END

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @PostConstruct
    public void testPostConstruct() {
        System.out.println("Hello testPostConstruct" );
    }

    @PreDestroy
    public void testPreDestoy() {
        System.out.println("Hello testPreDestoy" );
    }

    @Bean
    public Daytime initDayTime() {
        int hour = LocalDateTime.now().getHour();
        if (hour >= 6 && hour <= 22) {
            return new Day();
        }
        return new Night();
    }
    // END
}
