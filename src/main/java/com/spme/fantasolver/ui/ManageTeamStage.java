package com.spme.fantasolver.ui;

import com.spme.fantasolver.Application;
import com.spme.fantasolver.controllers.ManageTeamController;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Role;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ManageTeamStage {
    private ManageTeamController manageTeamController;
    private Stage stage;
    private FXMLLoader fxmlLoader;

    private TextField textFieldTeamName;
    private TableView<Player> tableViewPlayers;
    ObservableList<Player> players;

    public ManageTeamStage(){
        this.manageTeamController = ManageTeamController.getInstance();
        manageTeamController.handleInitialization(this);
    }

    public void initializeStage() throws IOException {
        fxmlLoader = new FXMLLoader(Application.class.getResource("manage-team-stage.fxml"));
        stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.setTitle("FantaSolver - Gestisci la tua Rosa");

        textFieldTeamName = (TextField)fxmlLoader.getNamespace().get("textFieldTeamName");
        textFieldTeamName.setOnKeyTyped(keyEvent ->
                manageTeamController.handleTextFieldTeamNameChanged(textFieldTeamName.getText()));

        initializeTeamTable();

    }

    private void initializeTeamTable() {
        // Creazione di alcuni giocatori di esempio
        Player player1 = new Player("Rosati", Set.of(Role.POR));
        Player player2 = new Player("Aronica", Set.of(Role.DS, Role.DC));

        // Creazione della TableView
        tableViewPlayers = (TableView<Player>) fxmlLoader.getNamespace().get("tableViewPlayers");
        players = FXCollections.observableArrayList();
        tableViewPlayers.setItems(players);

        TableColumn<Player, String> tableColumnPlayerName = (TableColumn<Player, String>)
                fxmlLoader.getNamespace().get("tableColumnPlayerName");
        tableColumnPlayerName.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Creazione della colonna "Ruoli"
        TableColumn<Player, String> tableColumnPlayerRoles = (TableColumn<Player, String>)
                fxmlLoader.getNamespace().get("tableColumnPlayerRoles");
        tableColumnPlayerRoles.setCellValueFactory(cellData -> {
            Set<Role> roles = cellData.getValue().getRoles();
            StringBuilder rolesString = new StringBuilder();
            for (Role role: roles) {
                rolesString.append(role.name()).append(", ");
            }
            // Rimuovi l'ultima virgola e lo spazio
            if (rolesString.length() > 0) {
                rolesString.setLength(rolesString.length() - 2);
            }
            return new SimpleStringProperty(rolesString.toString());
        });

        // Aggiunta delle colonne alla TableView
//        tableViewPlayers.getColumns().addAll(playerColumn, rolesColumn);
        tableViewPlayers.getColumns().set(0, tableColumnPlayerName);
        tableViewPlayers.getColumns().set(1, tableColumnPlayerRoles);
    }

    public void show(){
        stage.show();
    }

    public void loadPlayersInTable(Set<Player> players) {
        this.players.addAll(players);
    }
}
