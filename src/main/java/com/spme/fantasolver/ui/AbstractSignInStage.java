package com.spme.fantasolver.ui;

public interface AbstractSignInStage extends AbstractStage{
    void setSignInButtonAbility(boolean ability);
    boolean isSignInEnabled();
    boolean isSignInDisabled();
    void showFailedSignInLabel();
    String getUsername();
}
