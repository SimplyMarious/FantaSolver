package com.spme.fantasolver.ui;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.entity.Lineup;
import com.spme.fantasolver.entity.Team;

@Generated
public class JavaFXStageFactory implements StageFactory{
    @Override
    public AbstractStage createHomeStage() {
        return new HomeStage();
    }

    @Override
    public AbstractStage createManageStage() {
        return new ManageTeamStage();
    }

    @Override
    public AbstractStage createSignInStage() {
        return new SignInStage();
    }

    @Override
    public AbstractStage createSignUpStage() {
        return new SignUpStage();
    }

    @Override
    public AbstractStage createProposeLineupStage(Team team) {
        return new ProposeLineupStage(team);
    }

    @Override
    public AbstractStage createVerifiedLineupStage(Lineup lineup) {
        return new VerifiedLineupStage(lineup);
    }
}
