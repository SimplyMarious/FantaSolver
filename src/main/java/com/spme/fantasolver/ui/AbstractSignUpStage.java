package com.spme.fantasolver.ui;

public interface AbstractSignUpStage extends AbstractStage {
    void showSuccessfulSignUp();
    void showFailedSignUp();
    boolean isSignUpDisabled();
    boolean isSignUpEnabled();
    void setSignUpButtonAbility(boolean ability);
}
