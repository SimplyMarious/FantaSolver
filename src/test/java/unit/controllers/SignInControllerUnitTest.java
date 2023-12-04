package unit.controllers;

import com.spme.fantasolver.controllers.FXMLLoadException;
import com.spme.fantasolver.controllers.SignInController;
import com.spme.fantasolver.ui.SignInStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SignInControllerUnitTest {

    private SignInStage mockSignInStage;
    private SignInController signInController;

    @BeforeEach
    void setUp() {
        mockSignInStage = mock(SignInStage.class);
        signInController = SignInController.getInstance();
        signInController.setSignInStage(mockSignInStage);
    }

    @Test
    void testGetInstance() {
        assertNotNull(signInController);
        assertSame(signInController, SignInController.getInstance());
    }

    @Test
    void testHandleInitializationWithNotNullSignInStage() throws IOException {
        doNothing().when(mockSignInStage).initializeStage();

        signInController.handleInitialization();

        verify(mockSignInStage, times(1)).initializeStage();
    }

    @Test
    void testHandleInitializationWithNullSignInStage() throws IOException {
        doNothing().when(mockSignInStage).initializeStage();
        signInController.setSignInStage(null);

        assertThrows(NullPointerException.class,  () -> signInController.handleInitialization());
    }

    @Test
    void testHandleInitializationWithExceptionDuringInitialization() throws IOException {
        doThrow(new IOException()).when(mockSignInStage).initializeStage();

        try(MockedStatic<Logger> loggerMockedStatic = mockStatic(Logger.class)){
            Logger mockedLogger = mock(Logger.class);
            loggerMockedStatic.when(() -> Logger.getLogger("SignInController")).thenReturn(mockedLogger);
            doNothing().when(mockedLogger).info(any(String.class));

            assertThrows(FXMLLoadException.class, () -> signInController.handleInitialization());
        }
    }

    @Test
    void testHandleFieldChangedWithValidFieldsAndDisabledButton() {
        String validUsername = "validUser";
        String validPassword = "validPassword";
        when(mockSignInStage.isSignInEnabled()).thenReturn(false);
        when(mockSignInStage.isSignInDisabled()).thenReturn(true);

        signInController.handleFieldChanged(validUsername, validPassword);

        verify(mockSignInStage, times(1)).setSignInButtonAbility(true);
        verify(mockSignInStage, never()).setSignInButtonAbility(false);
    }

    @Test
    void testHandleFieldChangedWithValidFieldsAndEnabledButton() {
        String validUsername = "validUser";
        String validPassword = "validPassword";
        when(mockSignInStage.isSignInEnabled()).thenReturn(true);
        when(mockSignInStage.isSignInDisabled()).thenReturn(false);

        signInController.handleFieldChanged(validUsername, validPassword);

        verify(mockSignInStage, never()).setSignInButtonAbility(true);
        verify(mockSignInStage, never()).setSignInButtonAbility(false);
    }

    @Test
    void testHandleFieldChangedWithInvalidFieldsAndDisabledButton() {
        String invalidUsername = "inv";
        String invalidPassword = "inv";
        when(mockSignInStage.isSignInEnabled()).thenReturn(false);
        when(mockSignInStage.isSignInDisabled()).thenReturn(true);

        signInController.handleFieldChanged(invalidUsername, invalidPassword);

        verify(mockSignInStage, never()).setSignInButtonAbility(true);
        verify(mockSignInStage, never()).setSignInButtonAbility(false);
    }

    @Test
    void testHandleFieldChangedWithInvalidFieldsAndEnabledButton() {
        String invalidUsername = "inv";
        String invalidPassword = "inv";
        when(mockSignInStage.isSignInEnabled()).thenReturn(true);
        when(mockSignInStage.isSignInDisabled()).thenReturn(false);

        signInController.handleFieldChanged(invalidUsername, invalidPassword);

        verify(mockSignInStage, never()).setSignInButtonAbility(true);
        verify(mockSignInStage, times(1)).setSignInButtonAbility(false);
    }

    @Test
    void testHandleFieldChangedWithInvalidUsernameAndDisabledButton() {
        String invalidUsername = "inv";
        String validPassword = "validPassword";
        when(mockSignInStage.isSignInEnabled()).thenReturn(false);
        when(mockSignInStage.isSignInDisabled()).thenReturn(true);

        signInController.handleFieldChanged(invalidUsername, validPassword);

        verify(mockSignInStage, never()).setSignInButtonAbility(true);
        verify(mockSignInStage, never()).setSignInButtonAbility(false);
    }

    @Test
    void testHandleFieldChangedWithInvalidUsernameAndEnabledButton() {
        String invalidUsername = "inv";
        String validPassword = "validPassword";
        when(mockSignInStage.isSignInEnabled()).thenReturn(true);
        when(mockSignInStage.isSignInDisabled()).thenReturn(false);

        signInController.handleFieldChanged(invalidUsername, validPassword);

        verify(mockSignInStage, never()).setSignInButtonAbility(true);
        verify(mockSignInStage, times(1)).setSignInButtonAbility(false);
    }

    @Test
    void testHandleFieldChangedWithInvalidPasswordAndDisabledButton() {
        String validUsername = "validUsername";
        String invalidPassword = "inv";
        when(mockSignInStage.isSignInEnabled()).thenReturn(false);
        when(mockSignInStage.isSignInDisabled()).thenReturn(true);

        signInController.handleFieldChanged(validUsername, invalidPassword);

        verify(mockSignInStage, never()).setSignInButtonAbility(true);
        verify(mockSignInStage, never()).setSignInButtonAbility(false);
    }

    @Test
    void testHandleFieldChangedWithInvalidPasswordAndEnabledButton() {
        String validUsername = "validUsername";
        String invalidPassword = "inv";
        when(mockSignInStage.isSignInEnabled()).thenReturn(true);
        when(mockSignInStage.isSignInDisabled()).thenReturn(false);

        signInController.handleFieldChanged(validUsername, invalidPassword);

        verify(mockSignInStage, never()).setSignInButtonAbility(true);
        verify(mockSignInStage, times(1)).setSignInButtonAbility(false);
    }
}
