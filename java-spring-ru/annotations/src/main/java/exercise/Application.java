package exercise;

import exercise.annotation.Inspect;
import exercise.model.Address;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Method;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        var address = new Address("London", 12345678);

        // BEGIN
        SpringApplication.run(Application.class, args);
        for (Method method : Address.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Inspect.class)) {
                try {
                    method.invoke(address);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Method name: " + method.getName());
                System.out.println("Method return type: " + method.getReturnType());

            }
        }
        // END
    }
}
