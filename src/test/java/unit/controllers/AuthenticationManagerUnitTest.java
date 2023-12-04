package unit.controllers;

import com.spme.fantasolver.controllers.AuthenticationManager;
import com.spme.fantasolver.entity.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationManagerUnitTest {
    private static AuthenticationManager authManager;

    @BeforeAll
    static void initialize(){
        authManager = AuthenticationManager.getInstance();
    }

    @AfterEach
    void clean(){
        if (authManager.getUser() != null){
            authManager.signOut();
        }
    }

    @Test
    void testGetInstance() {
        assertNotNull(authManager);
        assertSame(authManager, AuthenticationManager.getInstance());
    }

    @Test
    void testSignIn(){
        authManager.signIn(new User("TestUser"));

        User user = authManager.getUser();

        assertNotNull(user);
    }

    @Test
    void testSignOut(){
        authManager.signOut();

        User user = authManager.getUser();

        assertNull(user);
    }

}
