package com.spme.fantasolver.ui;

import com.spme.fantasolver.FantaSolver;
import com.spme.fantasolver.controllers.ProposeLineupController;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.entity.Team;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;

public class ProposeLineupStage {
    private ProposeLineupController proposeLineupController;
    private Team team;

    private Stage stage;
    private FXMLLoader fxmlLoader;

    private TableView<Player> tableViewTeamPlayers;
    private ObservableList<Player> teamPlayers;
    private TableView<Player> tableViewLineupPlayers;
    private ObservableList<Player> lineupPlayers;

    public ProposeLineupStage(Team team){
        this.proposeLineupController = ProposeLineupController.getInstance();
        this.team = team;
        proposeLineupController.setProposeLineupStage(this);
        proposeLineupController.handleInitialization();
    }

    public void initializeStage() throws IOException {
        fxmlLoader = new FXMLLoader(FantaSolver.class.getResource("propose-lineup-stage.fxml"));
        stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 920, 527);
        stage.setScene(scene);
        stage.setTitle("FantaSolver - Proponi una Formazione");

        tableViewTeamPlayers = (TableView<Player>) fxmlLoader.getNamespace().get("tableViewTeamPlayers");
        teamPlayers = FXCollections.observableArrayList();
        teamPlayers.addAll(team.getPlayers());
        tableViewTeamPlayers.setItems(teamPlayers);

        TableColumn<Player, String> tableColumnTeamPlayerName = (TableColumn<Player, String>)
                fxmlLoader.getNamespace().get("tableColumnTeamPlayerName");
        tableColumnTeamPlayerName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, String> tableColumnTeamPlayerRoles = (TableColumn<Player, String>)
                fxmlLoader.getNamespace().get("tableColumnTeamPlayerRoles");
        tableColumnTeamPlayerRoles.setCellValueFactory(cellData -> {
            Set<Role> roles = cellData.getValue().getRoles();
            StringBuilder rolesString = new StringBuilder();
            for (Role role: roles) {
                rolesString.append(role.name()).append(", ");
            }
            if (rolesString.length() > 0) {
                rolesString.setLength(rolesString.length() - 2);
            }
            return new SimpleStringProperty(rolesString.toString());
        });

//        tableViewTeamPlayers.getSelectionModel().selectedItemProperty().addListener(
//                (obs, oldSelection, newSelection) -> onSelectedTableViewTeamPlayer());
    }

    public void show() {
        stage.show();
    }
}
