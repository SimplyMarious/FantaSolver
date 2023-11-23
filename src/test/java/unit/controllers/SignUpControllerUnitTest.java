package unit.controllers;

import com.spme.fantasolver.controllers.SignUpController;
import com.spme.fantasolver.dao.UserDAO;
import com.spme.fantasolver.ui.SignUpStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class SignUpControllerUnitTest {

    private UserDAO mockedUserDAO;
    private SignUpStage mockedSignUpStage;
    private SignUpController signUpController;

    @BeforeEach
    void setUp() {
        mockedUserDAO = mock(UserDAO.class);
        mockedSignUpStage = mock(SignUpStage.class);
        signUpController = SignUpController.getInstance();
        signUpController.handleInitialization(mockedSignUpStage);
    }

    @Test
    public void testHandleFieldChangedWithValidFieldsAndDisabledButton() {
        String validUsername = "validUser";
        String validPassword = "validPassword";
        when(mockedSignUpStage.isSignUpEnable()).thenReturn(false);
        when(mockedSignUpStage.isSignUpDisable()).thenReturn(true);

        signUpController.handleFieldChanged(validUsername, validPassword);

        verify(mockedSignUpStage, times(1)).enableSignUpButton();
        verify(mockedSignUpStage, never()).disableSignUpButton();
    }

    @Test
    public void testHandleFieldChangedWithValidFieldsAndEnabledButton() {
        String validUsername = "validUser";
        String validPassword = "validPassword";
        when(mockedSignUpStage.isSignUpEnable()).thenReturn(true);
        when(mockedSignUpStage.isSignUpDisable()).thenReturn(false);

        signUpController.handleFieldChanged(validUsername, validPassword);

        verify(mockedSignUpStage, never()).enableSignUpButton();
        verify(mockedSignUpStage, never()).disableSignUpButton();
    }

    @Test
    public void testHandleFieldChangedWithInvalidFieldsAndDisabledButton() {
        String invalidUsername = "inv";
        String invalidPassword = "inv";
        when(mockedSignUpStage.isSignUpEnable()).thenReturn(false);
        when(mockedSignUpStage.isSignUpDisable()).thenReturn(true);

        signUpController.handleFieldChanged(invalidUsername, invalidPassword);

        verify(mockedSignUpStage, never()).enableSignUpButton();
        verify(mockedSignUpStage, never()).disableSignUpButton();
    }

    @Test
    public void testHandleFieldChangedWithInvalidFieldsAndEnabledButton() {
        String invalidUsername = "inv";
        String invalidPassword = "inv";
        when(mockedSignUpStage.isSignUpEnable()).thenReturn(true);
        when(mockedSignUpStage.isSignUpDisable()).thenReturn(false);

        signUpController.handleFieldChanged(invalidUsername, invalidPassword);

        verify(mockedSignUpStage, never()).enableSignUpButton();
        verify(mockedSignUpStage, times(1)).disableSignUpButton();
    }

    @Test
    public void testHandleFieldChangedWithInvalidUsernameAndDisabledButton() {
        String invalidUsername = "inv";
        String validPassword = "validPassword";
        when(mockedSignUpStage.isSignUpEnable()).thenReturn(false);
        when(mockedSignUpStage.isSignUpDisable()).thenReturn(true);

        signUpController.handleFieldChanged(invalidUsername, validPassword);

        verify(mockedSignUpStage, never()).enableSignUpButton();
        verify(mockedSignUpStage, never()).disableSignUpButton();
    }

    @Test
    public void testHandleFieldChangedWithInvalidUsernameAndEnabledButton() {
        String invalidUsername = "inv";
        String validPassword = "validPassword";
        when(mockedSignUpStage.isSignUpEnable()).thenReturn(true);
        when(mockedSignUpStage.isSignUpDisable()).thenReturn(false);


        signUpController.handleFieldChanged(invalidUsername, validPassword);

        verify(mockedSignUpStage, never()).enableSignUpButton();
        verify(mockedSignUpStage, times(1)).disableSignUpButton();
    }

    @Test
    public void testHandleFieldChangedWithInvalidPasswordAndDisabledButton() {
        String validUsername = "validUsername";
        String invalidPassword = "inv";
        when(mockedSignUpStage.isSignUpEnable()).thenReturn(false);
        when(mockedSignUpStage.isSignUpDisable()).thenReturn(true);

        signUpController.handleFieldChanged(validUsername, invalidPassword);

        verify(mockedSignUpStage, never()).enableSignUpButton();
        verify(mockedSignUpStage, never()).disableSignUpButton();
    }

    @Test
    public void testHandleFieldChangedWithInvalidPasswordAndEnabledButton() {
        String validUsername = "validUsername";
        String invalidPassword = "inv";
        when(mockedSignUpStage.isSignUpEnable()).thenReturn(true);
        when(mockedSignUpStage.isSignUpDisable()).thenReturn(false);

        signUpController.handleFieldChanged(validUsername, invalidPassword);

        verify(mockedSignUpStage, never()).enableSignUpButton();
        verify(mockedSignUpStage, times(1)).disableSignUpButton();
    }

}
