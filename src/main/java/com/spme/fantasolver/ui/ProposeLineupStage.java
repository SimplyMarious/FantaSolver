package com.spme.fantasolver.ui;

import com.spme.fantasolver.FantaSolver;
import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.controllers.ProposeLineupController;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.entity.Team;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@Generated
public class ProposeLineupStage {
    private final ProposeLineupController proposeLineupController;
    private final Team team;

    private Stage stage;
    private FXMLLoader fxmlLoader;

    private TableView<Player> tableViewTeamPlayers;
    private Button buttonAddPlayerToLineup;
    private TableView<Player> tableViewLineupPlayers;
    private ObservableList<Player> lineupPlayers;
    private Button buttonRemovePlayerFromLineup;
    private Button buttonVerifyLineup;

    public ProposeLineupStage(Team team){
        this.proposeLineupController = ProposeLineupController.getInstance();
        this.team = team;
        proposeLineupController.setProposeLineupStage(this);
        proposeLineupController.handleInitialization();
    }

    @Generated
    public void initializeStage() throws IOException {
        fxmlLoader = new FXMLLoader(FantaSolver.class.getResource("propose-lineup-stage.fxml"));
        stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 920, 527);
        stage.setScene(scene);
        stage.setTitle("FantaSolver - Proponi una Formazione");

        initializeTables();
        initializeButtons();
    }

    @Generated
    private void initializeTables() {
        tableViewTeamPlayers = (TableView<Player>) fxmlLoader.getNamespace().get("tableViewTeamPlayers");
        ObservableList<Player> teamPlayers = FXCollections.observableArrayList();
        teamPlayers.addAll(team.getPlayers());
        tableViewTeamPlayers.setItems(teamPlayers);

        TableColumn<Player, String> tableColumnTeamPlayerName = (TableColumn<Player, String>)
                fxmlLoader.getNamespace().get("tableColumnTeamPlayerName");
        tableColumnTeamPlayerName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, String> tableColumnTeamPlayerRoles = (TableColumn<Player, String>)
                fxmlLoader.getNamespace().get("tableColumnTeamPlayerRoles");
        tableColumnTeamPlayerRoles.setCellValueFactory(cellData ->
                Role.getFormattedRoles(cellData.getValue().getRoles())
        );


        tableViewTeamPlayers.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> onSelectedTableViewTeamPlayer());


        tableViewLineupPlayers = (TableView<Player>) fxmlLoader.getNamespace().get("tableViewLineupPlayers");
        lineupPlayers = FXCollections.observableArrayList();
        tableViewLineupPlayers.setItems(lineupPlayers);

        TableColumn<Player, String> tableColumnLineupPlayerName = (TableColumn<Player, String>)
                fxmlLoader.getNamespace().get("tableColumnLineupPlayerName");
        tableColumnLineupPlayerName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, String> tableColumnLineupPlayerRoles = (TableColumn<Player, String>)
                fxmlLoader.getNamespace().get("tableColumnLineupPlayerRoles");
        tableColumnLineupPlayerRoles.setCellValueFactory(cellData ->
                        Role.getFormattedRoles(cellData.getValue().getRoles()));

        tableViewLineupPlayers.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null){
                onSelectedLineupTableViewPlayer();
            }
        });

        lineupPlayers.addListener((ListChangeListener<? super Player>) change ->
                onLineupTableViewChanged());
    }

    @Generated
    private void initializeButtons() {
        buttonAddPlayerToLineup = (Button) fxmlLoader.getNamespace().get("buttonAddPlayerToLineup");
        buttonAddPlayerToLineup.setOnAction(actionEvent -> onPressedAddPlayerToLineupButton());
        buttonAddPlayerToLineup.setDisable(true);

        buttonRemovePlayerFromLineup = (Button) fxmlLoader.getNamespace().get("buttonRemovePlayerFromLineup");
        buttonRemovePlayerFromLineup.setOnAction(actionEvent -> onPressedRemovePlayerFromLineupButton());
        buttonRemovePlayerFromLineup.setDisable(true);

        buttonVerifyLineup = (Button) fxmlLoader.getNamespace().get("buttonVerifyLineup");
        buttonVerifyLineup.setOnAction(actionEvent -> onPressedVerifyLineupButton());
    }

    @Generated
    private void onSelectedTableViewTeamPlayer() {
        proposeLineupController.handleSelectedTableViewTeamPlayer(lineupPlayers.size());
    }

    @Generated
    private void onPressedAddPlayerToLineupButton() {
        proposeLineupController.handlePressedAddPlayerToLineupButton(
                tableViewTeamPlayers.getSelectionModel().getSelectedItem());
    }

    @Generated
    private void onSelectedLineupTableViewPlayer() {
        proposeLineupController.handleSelectedTableViewLineupPlayer();
    }

    @Generated
    private void onPressedRemovePlayerFromLineupButton() {
        proposeLineupController.handlePressedRemovePlayerFromLineupButton(
                tableViewLineupPlayers.getSelectionModel().getSelectedItem()
        );
    }

    @Generated
    private void onLineupTableViewChanged() {
        proposeLineupController.handleLineUpTableViewChanged(lineupPlayers.size());
    }

    @Generated
    private void onPressedVerifyLineupButton() {
        proposeLineupController.handlePressedVerifyLineupButton(new HashSet<>(lineupPlayers));
    }

    public void setAddPlayerToLineupButtonAbility(boolean ability) {
        buttonAddPlayerToLineup.setDisable(!ability);
    }

    public void setRemovePlayerFromLineupButtonAbility(boolean ability) {
        buttonRemovePlayerFromLineup.setDisable(!ability);
    }

    public void setVerifyLineupButtonAbility(boolean ability) {
        buttonVerifyLineup.setDisable(!ability);
    }

    public void highlightPlayerInTeamTableView(Player player) {
        tableViewLineupPlayers.getSelectionModel().select(player);
    }

    public void addPlayerToLineupTableView(Player player) {
        lineupPlayers.add(player);
    }

    public void removePlayerFromLineupTableView(Player player) {
        lineupPlayers.remove(player);
    }

    public List<Player> getLineupPlayers() {
        return lineupPlayers;
    }

    public void show() {
        stage.show();
    }
}
