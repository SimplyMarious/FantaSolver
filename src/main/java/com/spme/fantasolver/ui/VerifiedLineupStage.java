package com.spme.fantasolver.ui;

import com.spme.fantasolver.FantaSolver;
import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.controllers.VerifiedLineupController;
import com.spme.fantasolver.entity.Lineup;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

@Generated
public class VerifiedLineupStage implements AbstractVerifiedLineupStage{

    private Stage stage;
    private FXMLLoader fxmlLoader;

    private Label labelLineupFormation;
    private ObservableList<Player> lineupPlayers;

    public VerifiedLineupStage(Lineup lineup){
        VerifiedLineupController verifiedLineupController = VerifiedLineupController.getInstance();
        verifiedLineupController.setVerifiedLineupStage(this);
        verifiedLineupController.handleInitialization(lineup);
    }

    @Override
    public void initializeStage() throws IOException {
        fxmlLoader = new FXMLLoader(FantaSolver.class.getResource("verified-lineup-stage.fxml"));
        stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 651, 569);
        stage.setScene(scene);
        stage.setTitle("FantaSolver - Formazione Verificata");
        FantaSolver.setIcon(stage);

        initializeLabel();
        initializeTable();
    }

    private void initializeLabel() {
        labelLineupFormation = (Label) fxmlLoader.getNamespace().get("labelLineupFormation");
    }

    private void initializeTable() {
        TableView<Player> tableViewLineupPlayers = (TableView<Player>) fxmlLoader.getNamespace().get("tableViewLineupPlayers");
        lineupPlayers = FXCollections.observableArrayList();
        tableViewLineupPlayers.setItems(lineupPlayers);

        TableColumn<Player, String> tableColumnLineupPlayerName = (TableColumn<Player, String>)
                fxmlLoader.getNamespace().get("tableColumnLineupPlayerName");
        tableColumnLineupPlayerName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, String> tableColumnLineupPlayerRoles = (TableColumn<Player, String>)
                fxmlLoader.getNamespace().get("tableColumnLineupPlayerRoles");
        tableColumnLineupPlayerRoles.setCellValueFactory(cellData ->
                Role.getFormattedRoles(cellData.getValue().getRoles()));
    }

    @Override
    public void setLineupFormationLabelText(String text){
        labelLineupFormation.setText(text);
    }

    @Override
    public void loadPlayersInTable(Player[] players) {
        lineupPlayers.addAll(players);
    }

    @Override
    public void show() {
        stage.show();
    }

}
