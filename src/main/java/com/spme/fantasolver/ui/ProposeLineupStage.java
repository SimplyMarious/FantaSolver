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
public class  ProposeLineupStage implements AbstractProposeLineupStage{
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
        proposeLineupController.setStageFactory(new JavaFXStageFactory());
        proposeLineupController.setProposeLineupStage(this);
        proposeLineupController.handleInitialization();
    }

    @Override
    public void initializeStage() throws IOException {
        fxmlLoader = new FXMLLoader(FantaSolver.class.getResource("propose-lineup-stage.fxml"));
        stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 920, 527);
        stage.setScene(scene);
        stage.setTitle("FantaSolver - Proponi una Formazione");
        FantaSolver.setIcon(stage);

        initializeTables();
        initializeButtons();
    }

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

    private void onSelectedTableViewTeamPlayer() {
        proposeLineupController.handleSelectedTableViewTeamPlayer(lineupPlayers.size());
    }

    private void onPressedAddPlayerToLineupButton() {
        proposeLineupController.handlePressedAddPlayerToLineupButton(
                tableViewTeamPlayers.getSelectionModel().getSelectedItem());
    }

    private void onSelectedLineupTableViewPlayer() {
        proposeLineupController.handleSelectedTableViewLineupPlayer();
    }

    private void onPressedRemovePlayerFromLineupButton() {
        proposeLineupController.handlePressedRemovePlayerFromLineupButton(
                tableViewLineupPlayers.getSelectionModel().getSelectedItem()
        );
    }

    private void onLineupTableViewChanged() {
        proposeLineupController.handleLineUpTableViewChanged(lineupPlayers.size());
    }

    private void onPressedVerifyLineupButton() {
        proposeLineupController.handlePressedVerifyLineupButton(new HashSet<>(lineupPlayers));
    }

    @Override
    public void setAddPlayerToLineupButtonAbility(boolean ability) {
        buttonAddPlayerToLineup.setDisable(!ability);
    }

    @Override
    public void setRemovePlayerFromLineupButtonAbility(boolean ability) {
        buttonRemovePlayerFromLineup.setDisable(!ability);
    }

    @Override
    public void setVerifyLineupButtonAbility(boolean ability) {
        buttonVerifyLineup.setDisable(!ability);
    }

    @Override
    public void highlightPlayerInTeamTableView(Player player) {
        tableViewLineupPlayers.getSelectionModel().select(player);
    }

    @Override
    public void addPlayerToLineupTableView(Player player) {
        lineupPlayers.add(player);
    }

    @Override
    public void removePlayerFromLineupTableView(Player player) {
        lineupPlayers.remove(player);
    }

    @Override
    public List<Player> getLineupPlayers() {
        return lineupPlayers;
    }

    @Override
    public void show() {
        stage.show();
    }
}
