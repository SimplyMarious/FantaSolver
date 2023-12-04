package unit.controllers;

import com.spme.fantasolver.controllers.AuthenticationManager;
import com.spme.fantasolver.entity.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationManagerUnitTest {
    private static AuthenticationManager authManager;

    @BeforeAll
    public static void initialize(){
        authManager = AuthenticationManager.getInstance();
    }

    @AfterEach
    public void clean(){
        if (authManager.getUser() != null){
            authManager.signOut();
        }
    }

    @Test
    public void testGetInstance() {
        assertNotNull(authManager);
        assertSame(authManager, AuthenticationManager.getInstance());
    }

    @Test
    public void testSignIn(){
        authManager.signIn(new User("TestUser"));

        User user = authManager.getUser();

        assertNotNull(user);
    }

    @Test
    public void testSignOut(){
        authManager.signOut();

        User user = authManager.getUser();

        assertNull(user);
    }

}
