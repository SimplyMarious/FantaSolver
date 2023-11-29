package unit.controllers;

import com.spme.fantasolver.controllers.FXMLLoadException;
import com.spme.fantasolver.controllers.SignUpController;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.dao.UserDAO;
import com.spme.fantasolver.ui.SignUpStage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SignUpControllerUnitTest {

    private UserDAO mockUserDAO;
    private SignUpStage mockSignUpStage;
    private SignUpController signUpController;
    private MockedStatic<DAOFactory> mockDAOFactory;

    @BeforeEach
    public void setUp() {
        mockDAOFactory = mockStatic(DAOFactory.class);
        mockUserDAO = mock(UserDAO.class);
        mockSignUpStage = mock(SignUpStage.class);
        signUpController = SignUpController.getInstance();
        signUpController.setSignUpStage(mockSignUpStage);
    }

    @AfterEach
    public void clean() {
        mockDAOFactory.close();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(signUpController);
        assertSame(signUpController, SignUpController.getInstance());
    }

    @Test
    public void testHandleInitializationWithNotNullSignInStage() throws IOException {
        doNothing().when(mockSignUpStage).initializeStage();

        signUpController.handleInitialization();

        verify(mockSignUpStage, times(1)).initializeStage();
    }

    @Test
    public void testHandleInitializationWithNullSignInStage() throws IOException {
        doNothing().when(mockSignUpStage).initializeStage();
        signUpController.setSignUpStage(null);

        assertThrows(NullPointerException.class,  () -> signUpController.handleInitialization());
    }

    @Test
    public void testHandleInitializationWithExceptionDuringInitialization() throws IOException {
        doThrow(new IOException()).when(mockSignUpStage).initializeStage();

        try(MockedStatic<Logger> loggerMockedStatic = mockStatic(Logger.class)){
            Logger mockedLogger = mock(Logger.class);
            loggerMockedStatic.when(() -> Logger.getLogger("SignUpController")).thenReturn(mockedLogger);
            doNothing().when(mockedLogger).info(any(String.class));

            assertThrows(FXMLLoadException.class, () -> signUpController.handleInitialization());
        }
    }

    @Test
    public void testHandlePressedSignUpButtonWithSuccessfulSignUp() {
        String username = "testUser";
        String password = "testPassword";
        mockDAOFactory.when(DAOFactory::getUserDAO).thenReturn(mockUserDAO);
        when(mockUserDAO.signUp(username, password)).thenReturn(true);

        signUpController.handlePressedSignUpButton(username, password);

        verify(mockSignUpStage).showSuccessfulSignUp();
        verify(mockSignUpStage, never()).showFailedSignUp();
    }

    @Test
    public void testHandlePressedSignUpButtonWithFailureSignUp() {
        String username = "testUser";
        String password = "testPassword";
        mockDAOFactory.when(DAOFactory::getUserDAO).thenReturn(mockUserDAO);
        when(mockUserDAO.signUp(username, password)).thenReturn(false);

        signUpController.handlePressedSignUpButton(username, password);

        verify(mockSignUpStage, never()).showSuccessfulSignUp();
        verify(mockSignUpStage).showFailedSignUp();
    }

    @Test
    public void testHandleFieldChangedWithValidFieldsAndDisabledButton() {
        String validUsername = "validUser";
        String validPassword = "validPassword";
        when(mockSignUpStage.isSignUpEnabled()).thenReturn(false);
        when(mockSignUpStage.isSignUpDisabled()).thenReturn(true);

        signUpController.handleFieldChanged(validUsername, validPassword);

        verify(mockSignUpStage, times(1)).setSignUpButtonAbility(true);
        verify(mockSignUpStage, never()).setSignUpButtonAbility(false);
    }

    @Test
    public void testHandleFieldChangedWithValidFieldsAndEnabledButton() {
        String validUsername = "validUser";
        String validPassword = "validPassword";
        when(mockSignUpStage.isSignUpEnabled()).thenReturn(true);
        when(mockSignUpStage.isSignUpDisabled()).thenReturn(false);

        signUpController.handleFieldChanged(validUsername, validPassword);

        verify(mockSignUpStage, never()).setSignUpButtonAbility(true);
        verify(mockSignUpStage, never()).setSignUpButtonAbility(false);
    }

    @Test
    public void testHandleFieldChangedWithInvalidFieldsAndDisabledButton() {
        String invalidUsername = "inv";
        String invalidPassword = "inv";
        when(mockSignUpStage.isSignUpEnabled()).thenReturn(false);
        when(mockSignUpStage.isSignUpDisabled()).thenReturn(true);

        signUpController.handleFieldChanged(invalidUsername, invalidPassword);

        verify(mockSignUpStage, never()).setSignUpButtonAbility(true);
        verify(mockSignUpStage, never()).setSignUpButtonAbility(false);
    }

    @Test
    public void testHandleFieldChangedWithInvalidFieldsAndEnabledButton() {
        String invalidUsername = "inv";
        String invalidPassword = "inv";
        when(mockSignUpStage.isSignUpEnabled()).thenReturn(true);
        when(mockSignUpStage.isSignUpDisabled()).thenReturn(false);

        signUpController.handleFieldChanged(invalidUsername, invalidPassword);

        verify(mockSignUpStage, never()).setSignUpButtonAbility(true);
        verify(mockSignUpStage, times(1)).setSignUpButtonAbility(false);
    }

    @Test
    public void testHandleFieldChangedWithInvalidUsernameAndDisabledButton() {
        String invalidUsername = "inv";
        String validPassword = "validPassword";
        when(mockSignUpStage.isSignUpEnabled()).thenReturn(false);
        when(mockSignUpStage.isSignUpDisabled()).thenReturn(true);

        signUpController.handleFieldChanged(invalidUsername, validPassword);

        verify(mockSignUpStage, never()).setSignUpButtonAbility(true);
        verify(mockSignUpStage, never()).setSignUpButtonAbility(false);
    }

    @Test
    public void testHandleFieldChangedWithInvalidUsernameAndEnabledButton() {
        String invalidUsername = "inv";
        String validPassword = "validPassword";
        when(mockSignUpStage.isSignUpEnabled()).thenReturn(true);
        when(mockSignUpStage.isSignUpDisabled()).thenReturn(false);


        signUpController.handleFieldChanged(invalidUsername, validPassword);

        verify(mockSignUpStage, never()).setSignUpButtonAbility(true);
        verify(mockSignUpStage, times(1)).setSignUpButtonAbility(false);
    }

    @Test
    public void testHandleFieldChangedWithInvalidPasswordAndDisabledButton() {
        String validUsername = "validUsername";
        String invalidPassword = "inv";
        when(mockSignUpStage.isSignUpEnabled()).thenReturn(false);
        when(mockSignUpStage.isSignUpDisabled()).thenReturn(true);

        signUpController.handleFieldChanged(validUsername, invalidPassword);

        verify(mockSignUpStage, never()).setSignUpButtonAbility(true);
        verify(mockSignUpStage, never()).setSignUpButtonAbility(false);
    }

    @Test
    public void testHandleFieldChangedWithInvalidPasswordAndEnabledButton() {
        String validUsername = "validUsername";
        String invalidPassword = "inv";
        when(mockSignUpStage.isSignUpEnabled()).thenReturn(true);
        when(mockSignUpStage.isSignUpDisabled()).thenReturn(false);

        signUpController.handleFieldChanged(validUsername, invalidPassword);

        verify(mockSignUpStage, never()).setSignUpButtonAbility(true);
        verify(mockSignUpStage, times(1)).setSignUpButtonAbility(false);
    }

}
