package com.spme.fantasolver.ui;

import com.spme.fantasolver.entity.Lineup;
import com.spme.fantasolver.entity.Team;

public interface StageFactory {
    AbstractStage createHomeStage();
    AbstractStage createManageStage();
    AbstractStage createSignInStage();
    AbstractStage createSignUpStage();
    AbstractStage createProposeLineupStage(Team team);
    AbstractStage createVerifiedLineupStage(Lineup lineup);
}
