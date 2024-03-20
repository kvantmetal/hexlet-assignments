package exercise;

import exercise.annotation.Inspect;
import exercise.model.Address;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Method;
import java.util.Set;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        var address = new Address("London", 12345678);

        // BEGIN
//        SpringApplication.run(Application.class, args);
        for (Method method : Address.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Inspect.class)) {
                try {
                    method.invoke(address);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("Method " + method.getName() + " returns a value of type: " + method.getReturnType());

            }
        }
        // END
    }
}
