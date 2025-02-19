import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTests {

    @Autowired
    private UserService userService;

    @Test
    public void testRegister() {
        User user = new User("testuser", "password", "test@example.com");
        userService.register(user);
        // Assert user registration
    }

    @Test
    public void testLogin() {
        User user = new User("testuser", "password");
        String result = userService.login(user);
        // Assert login success
    }
}
