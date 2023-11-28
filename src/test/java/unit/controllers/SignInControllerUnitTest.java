package unit.controllers;

import com.spme.fantasolver.controllers.FXMLLoadException;
import com.spme.fantasolver.controllers.SignInController;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.ui.SignInStage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SignInControllerUnitTest {

    private SignInStage mockedSignInStage;
    private SignInController signInController;

    @BeforeEach
    public void setUp() {
        mockedSignInStage = mock(SignInStage.class);
        signInController = SignInController.getInstance();
        signInController.setSignInStage(mockedSignInStage);
    }

    @Test
    public void testGetInstance() {
        assertNotNull(signInController);
        assertSame(signInController, SignInController.getInstance());
    }

    @Test
    public void testHandleInitializationWithNotNullSignInStage() throws IOException {
        doNothing().when(mockedSignInStage).initializeStage();

        signInController.handleInitialization();

        verify(mockedSignInStage, times(1)).initializeStage();
    }

    @Test
    public void testHandleInitializationWithNullSignInStage() throws IOException {
        doNothing().when(mockedSignInStage).initializeStage();
        signInController.setSignInStage(null);

        assertThrows(NullPointerException.class,  () -> signInController.handleInitialization());
    }

    @Test
    public void testHandleInitializationWithExceptionDuringInitialization() throws IOException {
        doThrow(new IOException()).when(mockedSignInStage).initializeStage();

        try(MockedStatic<Logger> loggerMockedStatic = mockStatic(Logger.class)){
            Logger mockedLogger = mock(Logger.class);
            loggerMockedStatic.when(() -> Logger.getLogger("SignInController")).thenReturn(mockedLogger);
            doNothing().when(mockedLogger).info(any(String.class));

            assertThrows(FXMLLoadException.class, () -> signInController.handleInitialization());
        }
    }

    @Test
    public void testHandleFieldChangedWithValidFieldsAndDisabledButton() {
        String validUsername = "validUser";
        String validPassword = "validPassword";
        when(mockedSignInStage.isSignInEnabled()).thenReturn(false);
        when(mockedSignInStage.isSignInDisabled()).thenReturn(true);

        signInController.handleFieldChanged(validUsername, validPassword);

        verify(mockedSignInStage, times(1)).setSignInButtonAbility(true);
        verify(mockedSignInStage, never()).setSignInButtonAbility(false);
    }

    @Test
    public void testHandleFieldChangedWithValidFieldsAndEnabledButton() {
        String validUsername = "validUser";
        String validPassword = "validPassword";
        when(mockedSignInStage.isSignInEnabled()).thenReturn(true);
        when(mockedSignInStage.isSignInDisabled()).thenReturn(false);

        signInController.handleFieldChanged(validUsername, validPassword);

        verify(mockedSignInStage, never()).setSignInButtonAbility(true);
        verify(mockedSignInStage, never()).setSignInButtonAbility(false);
    }

    @Test
    public void testHandleFieldChangedWithInvalidFieldsAndDisabledButton() {
        String invalidUsername = "inv";
        String invalidPassword = "inv";
        when(mockedSignInStage.isSignInEnabled()).thenReturn(false);
        when(mockedSignInStage.isSignInDisabled()).thenReturn(true);

        signInController.handleFieldChanged(invalidUsername, invalidPassword);

        verify(mockedSignInStage, never()).setSignInButtonAbility(true);
        verify(mockedSignInStage, never()).setSignInButtonAbility(false);
    }

    @Test
    public void testHandleFieldChangedWithInvalidFieldsAndEnabledButton() {
        String invalidUsername = "inv";
        String invalidPassword = "inv";
        when(mockedSignInStage.isSignInEnabled()).thenReturn(true);
        when(mockedSignInStage.isSignInDisabled()).thenReturn(false);

        signInController.handleFieldChanged(invalidUsername, invalidPassword);

        verify(mockedSignInStage, never()).setSignInButtonAbility(true);
        verify(mockedSignInStage, times(1)).setSignInButtonAbility(false);
    }

    @Test
    public void testHandleFieldChangedWithInvalidUsernameAndDisabledButton() {
        String invalidUsername = "inv";
        String validPassword = "validPassword";
        when(mockedSignInStage.isSignInEnabled()).thenReturn(false);
        when(mockedSignInStage.isSignInDisabled()).thenReturn(true);

        signInController.handleFieldChanged(invalidUsername, validPassword);

        verify(mockedSignInStage, never()).setSignInButtonAbility(true);
        verify(mockedSignInStage, never()).setSignInButtonAbility(false);
    }

    @Test
    public void testHandleFieldChangedWithInvalidUsernameAndEnabledButton() {
        String invalidUsername = "inv";
        String validPassword = "validPassword";
        when(mockedSignInStage.isSignInEnabled()).thenReturn(true);
        when(mockedSignInStage.isSignInDisabled()).thenReturn(false);

        signInController.handleFieldChanged(invalidUsername, validPassword);

        verify(mockedSignInStage, never()).setSignInButtonAbility(true);
        verify(mockedSignInStage, times(1)).setSignInButtonAbility(false);
    }

    @Test
    public void testHandleFieldChangedWithInvalidPasswordAndDisabledButton() {
        String validUsername = "validUsername";
        String invalidPassword = "inv";
        when(mockedSignInStage.isSignInEnabled()).thenReturn(false);
        when(mockedSignInStage.isSignInDisabled()).thenReturn(true);

        signInController.handleFieldChanged(validUsername, invalidPassword);

        verify(mockedSignInStage, never()).setSignInButtonAbility(true);
        verify(mockedSignInStage, never()).setSignInButtonAbility(false);
    }

    @Test
    public void testHandleFieldChangedWithInvalidPasswordAndEnabledButton() {
        String validUsername = "validUsername";
        String invalidPassword = "inv";
        when(mockedSignInStage.isSignInEnabled()).thenReturn(true);
        when(mockedSignInStage.isSignInDisabled()).thenReturn(false);

        signInController.handleFieldChanged(validUsername, invalidPassword);

        verify(mockedSignInStage, never()).setSignInButtonAbility(true);
        verify(mockedSignInStage, times(1)).setSignInButtonAbility(false);
    }
}
