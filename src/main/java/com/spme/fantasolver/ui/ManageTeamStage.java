package com.spme.fantasolver.ui;

import com.spme.fantasolver.Application;
import com.spme.fantasolver.controllers.ManageTeamController;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Role;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ManageTeamStage {
    private ManageTeamController manageTeamController;
    private Stage stage;
    private FXMLLoader fxmlLoader;

    private TextField textFieldTeamName;
    private TextField textFieldPlayerName;
    private ComboBox<String> comboBoxPlayerFirstRole;
    private ComboBox<String> comboBoxPlayerSecondRole;
    private ComboBox<String> comboBoxPlayerThirdRole;
    private Button buttonAddPlayer;
    private TableView<Player> tableViewPlayers;
    private ObservableList<Player> players;
    private Button buttonRemovePlayer;
    private Button buttonConfirm;


    public ManageTeamStage(){
        this.manageTeamController = ManageTeamController.getInstance();
        manageTeamController.setManageTeamStage(this);
        manageTeamController.handleInitialization();
    }

    public void initializeStage() throws IOException {
        fxmlLoader = new FXMLLoader(Application.class.getResource("manage-team-stage.fxml"));
        stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.setTitle("FantaSolver - Gestisci la tua Rosa");

        initializeTeamNameTextField();

        initializePlayerNameTextField();
        initializeRolesComboBoxes();
        buttonAddPlayer = (Button)fxmlLoader.getNamespace().get("buttonAddPlayer");
        buttonAddPlayer.setOnAction(actionEvent ->
                manageTeamController.handlePressedAddPlayerButton(
                        textFieldPlayerName.getText(),
                        comboBoxPlayerFirstRole.getValue(),
                        comboBoxPlayerSecondRole.getValue(),
                        comboBoxPlayerThirdRole.getValue()));
        buttonAddPlayer.setDisable(true);

        initializeTeamTable();

        buttonRemovePlayer = (Button)fxmlLoader.getNamespace().get("buttonRemovePlayer");
        buttonRemovePlayer.setDisable(true);
        buttonRemovePlayer.setOnAction(actionEvent ->
                manageTeamController.handlePressedRemovePlayerButton(
                        tableViewPlayers.getSelectionModel().getSelectedItem()));

        buttonConfirm = (Button)fxmlLoader.getNamespace().get("buttonConfirm");
        buttonConfirm.setDisable(true);
        buttonConfirm.setOnAction(actionEvent ->
                manageTeamController.handlePressedConfirmButton(
                        textFieldTeamName.getText(),
                        players));

    }

    private void initializeTeamNameTextField() {
        textFieldTeamName = (TextField)fxmlLoader.getNamespace().get("textFieldTeamName");
        textFieldTeamName.setOnKeyTyped(keyEvent ->
                manageTeamController.handleTeamPropertyChanged(textFieldTeamName.getText(), players.size()));
    }

    private void initializePlayerNameTextField() {
        textFieldPlayerName = (TextField)fxmlLoader.getNamespace().get("textFieldPlayerName");
        textFieldPlayerName.setOnKeyTyped(keyEvent ->
                manageTeamController.handlePlayerPropertyChanged(
                        textFieldPlayerName.getText(),
                        comboBoxPlayerFirstRole.getValue(),
                        comboBoxPlayerSecondRole.getValue(),
                        comboBoxPlayerThirdRole.getValue()));
    }

    private void initializeRolesComboBoxes() {
        comboBoxPlayerFirstRole = (ComboBox<String>)fxmlLoader.getNamespace().get("comboBoxPlayerFirstRole");
        comboBoxPlayerSecondRole = (ComboBox<String>)fxmlLoader.getNamespace().get("comboBoxPlayerSecondRole");
        comboBoxPlayerThirdRole = (ComboBox<String>)fxmlLoader.getNamespace().get("comboBoxPlayerThirdRole");
        comboBoxPlayerFirstRole.setOnAction(actionEvent ->
                manageTeamController.handlePlayerPropertyChanged(
                        textFieldPlayerName.getText(),
                        comboBoxPlayerFirstRole.getValue(),
                        comboBoxPlayerSecondRole.getValue(),
                        comboBoxPlayerThirdRole.getValue()));
        comboBoxPlayerSecondRole.setOnAction(actionEvent ->
                manageTeamController.handlePlayerPropertyChanged(
                        textFieldPlayerName.getText(),
                        comboBoxPlayerFirstRole.getValue(),
                        comboBoxPlayerSecondRole.getValue(),
                        comboBoxPlayerThirdRole.getValue()));
        comboBoxPlayerThirdRole.setOnAction(actionEvent ->
                manageTeamController.handlePlayerPropertyChanged(
                        textFieldPlayerName.getText(),
                        comboBoxPlayerFirstRole.getValue(),
                        comboBoxPlayerSecondRole.getValue(),
                        comboBoxPlayerThirdRole.getValue()));

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.add("Nessuno");
        for(Role role: Role.values()){
            observableList.add(role.toString());
        }

        comboBoxPlayerFirstRole.setItems(observableList);
        comboBoxPlayerSecondRole.setItems(observableList);
        comboBoxPlayerThirdRole.setItems(observableList);

        comboBoxPlayerFirstRole.getSelectionModel().select(1);
        comboBoxPlayerSecondRole.getSelectionModel().select(0);
        comboBoxPlayerThirdRole.getSelectionModel().select(0);
    }

    private void initializeTeamTable() {
        tableViewPlayers = (TableView<Player>) fxmlLoader.getNamespace().get("tableViewPlayers");
        players = FXCollections.observableArrayList();
        players.addListener((ListChangeListener<? super Player>) change ->
                manageTeamController.handleTeamPropertyChanged(textFieldTeamName.getText(), players.size())
                );
        tableViewPlayers.setItems(players);

        TableColumn<Player, String> tableColumnPlayerName = (TableColumn<Player, String>)
                fxmlLoader.getNamespace().get("tableColumnPlayerName");
        tableColumnPlayerName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, String> tableColumnPlayerRoles = (TableColumn<Player, String>)
                fxmlLoader.getNamespace().get("tableColumnPlayerRoles");
        tableColumnPlayerRoles.setCellValueFactory(cellData -> {
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

        tableViewPlayers.getColumns().set(0, tableColumnPlayerName);
        tableViewPlayers.getColumns().set(1, tableColumnPlayerRoles);

        tableViewPlayers.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null){
                manageTeamController.handleSelectedPlayerFromTableView();
            }
        });

    }

    public void setTextFieldTeamName(String name) {
        textFieldTeamName.setText(name);
    }


    public void setAddPlayerButtonAbility(boolean ability) {
        buttonAddPlayer.setDisable(!ability);
    }

    public void loadPlayersInTable(Set<Player> players) {
        this.players.addAll(players);
    }

    public void addPlayerToTableView(Player player) {
        players.add(player);
    }

    public void highlightPlayerInTableView(Player player){
        tableViewPlayers.getSelectionModel().select(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setRemovePlayerButtonAbility(boolean ability) {
        buttonRemovePlayer.setDisable(!ability);
        System.out.println("Setto a " + ability);
    }

    public void removePlayerFromTableView(Player player) {
        players.remove(player);
    }

    public void setConfirmButtonAbility(boolean ability) {
        buttonConfirm.setDisable(!ability);
    }

    public void show(){
        stage.show();
    }

    public void close() {
        stage.close();
    }
}
