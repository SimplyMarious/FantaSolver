package unit.controllers;

import com.spme.fantasolver.controllers.SignInController;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.ui.SignInStage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SignInControllerUnitTest {

    private SignInStage mockedSignInStage;
    private SignInController signInController;
    private MockedStatic<DAOFactory> mockedDAOFactory;

    @BeforeEach
    public void setUp() {
        mockedDAOFactory = mockStatic(DAOFactory.class);
        mockedSignInStage = mock(SignInStage.class);
        signInController = SignInController.getInstance();
        signInController.setSignInStage(mockedSignInStage);
    }

    @AfterEach
    public void clean() {
        mockedDAOFactory.close();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(signInController);
        assertSame(signInController, SignInController.getInstance());
    }

    @Test
    public void testHandleInitialization() {
        signInController.handleInitialization(mockedSignInStage);
        try {
            verify(mockedSignInStage, times(1)).initializeStage();
        } catch (IOException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testHandleFieldChangedWithValidFieldsAndDisabledButton() {
        String validUsername = "validUser";
        String validPassword = "validPassword";
        when(mockedSignInStage.isSignInEnable()).thenReturn(false);
        when(mockedSignInStage.isSignInDisable()).thenReturn(true);

        signInController.handleFieldChanged(validUsername, validPassword);

        verify(mockedSignInStage, times(1)).enableSignInButton();
        verify(mockedSignInStage, never()).disableSignInButton();
    }

    @Test
    public void testHandleFieldChangedWithValidFieldsAndEnabledButton() {
        String validUsername = "validUser";
        String validPassword = "validPassword";
        when(mockedSignInStage.isSignInEnable()).thenReturn(true);
        when(mockedSignInStage.isSignInDisable()).thenReturn(false);

        signInController.handleFieldChanged(validUsername, validPassword);

        verify(mockedSignInStage, never()).enableSignInButton();
        verify(mockedSignInStage, never()).disableSignInButton();
    }

    @Test
    public void testHandleFieldChangedWithInvalidFieldsAndDisabledButton() {
        String invalidUsername = "inv";
        String invalidPassword = "inv";
        when(mockedSignInStage.isSignInEnable()).thenReturn(false);
        when(mockedSignInStage.isSignInDisable()).thenReturn(true);

        signInController.handleFieldChanged(invalidUsername, invalidPassword);

        verify(mockedSignInStage, never()).enableSignInButton();
        verify(mockedSignInStage, never()).disableSignInButton();
    }

    @Test
    public void testHandleFieldChangedWithInvalidFieldsAndEnabledButton() {
        String invalidUsername = "inv";
        String invalidPassword = "inv";
        when(mockedSignInStage.isSignInEnable()).thenReturn(true);
        when(mockedSignInStage.isSignInDisable()).thenReturn(false);

        signInController.handleFieldChanged(invalidUsername, invalidPassword);

        verify(mockedSignInStage, never()).enableSignInButton();
        verify(mockedSignInStage, times(1)).disableSignInButton();
    }

    @Test
    public void testHandleFieldChangedWithInvalidUsernameAndDisabledButton() {
        String invalidUsername = "inv";
        String validPassword = "validPassword";
        when(mockedSignInStage.isSignInEnable()).thenReturn(false);
        when(mockedSignInStage.isSignInDisable()).thenReturn(true);

        signInController.handleFieldChanged(invalidUsername, validPassword);

        verify(mockedSignInStage, never()).enableSignInButton();
        verify(mockedSignInStage, never()).disableSignInButton();
    }

    @Test
    public void testHandleFieldChangedWithInvalidUsernameAndEnabledButton() {
        String invalidUsername = "inv";
        String validPassword = "validPassword";
        when(mockedSignInStage.isSignInEnable()).thenReturn(true);
        when(mockedSignInStage.isSignInDisable()).thenReturn(false);


        signInController.handleFieldChanged(invalidUsername, validPassword);

        verify(mockedSignInStage, never()).enableSignInButton();
        verify(mockedSignInStage, times(1)).disableSignInButton();
    }

    @Test
    public void testHandleFieldChangedWithInvalidPasswordAndDisabledButton() {
        String validUsername = "validUsername";
        String invalidPassword = "inv";
        when(mockedSignInStage.isSignInEnable()).thenReturn(false);
        when(mockedSignInStage.isSignInDisable()).thenReturn(true);

        signInController.handleFieldChanged(validUsername, invalidPassword);

        verify(mockedSignInStage, never()).enableSignInButton();
        verify(mockedSignInStage, never()).disableSignInButton();
    }

    @Test
    public void testHandleFieldChangedWithInvalidPasswordAndEnabledButton() {
        String validUsername = "validUsername";
        String invalidPassword = "inv";
        when(mockedSignInStage.isSignInEnable()).thenReturn(true);
        when(mockedSignInStage.isSignInDisable()).thenReturn(false);

        signInController.handleFieldChanged(validUsername, invalidPassword);

        verify(mockedSignInStage, never()).enableSignInButton();
        verify(mockedSignInStage, times(1)).disableSignInButton();
    }
}
