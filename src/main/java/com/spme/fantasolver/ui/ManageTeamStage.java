package com.spme.fantasolver.ui;

import com.spme.fantasolver.FantaSolver;
import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.controllers.ManageTeamController;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Role;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Generated
public class ManageTeamStage implements AbstractManageTeamStage{
    private final ManageTeamController manageTeamController;
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

    @Override
    public void initializeStage() throws IOException {
        fxmlLoader = new FXMLLoader(FantaSolver.class.getResource("manage-team-stage.fxml"));
        stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.setTitle("FantaSolver - Gestisci la tua Rosa");
        FantaSolver.setIcon(stage);

        initializeTeamNameTextField();

        initializePlayerNameTextField();
        initializeRolesComboBoxes();
        initializeAddPlayerButton();

        initializeTeamTable();
        initializeRemovePlayerButton();

        initializeConfirmButton();
    }

    private void initializeTeamNameTextField() {
        textFieldTeamName = (TextField)fxmlLoader.getNamespace().get("textFieldTeamName");
        textFieldTeamName.setOnKeyTyped(keyEvent -> onTeamPropertyChanged());
    }

    private void initializePlayerNameTextField() {
        textFieldPlayerName = (TextField)fxmlLoader.getNamespace().get("textFieldPlayerName");
        textFieldPlayerName.setOnKeyTyped(keyEvent -> onPlayerPropertyChanged());
    }

    private void initializeRolesComboBoxes() {
        comboBoxPlayerFirstRole = (ComboBox<String>)fxmlLoader.getNamespace().get("comboBoxPlayerFirstRole");
        comboBoxPlayerSecondRole = (ComboBox<String>)fxmlLoader.getNamespace().get("comboBoxPlayerSecondRole");
        comboBoxPlayerThirdRole = (ComboBox<String>)fxmlLoader.getNamespace().get("comboBoxPlayerThirdRole");
        comboBoxPlayerFirstRole.setOnAction(actionEvent -> onPlayerPropertyChanged());
        comboBoxPlayerSecondRole.setOnAction(actionEvent -> onPlayerPropertyChanged());
        comboBoxPlayerThirdRole.setOnAction(actionEvent ->  onPlayerPropertyChanged());

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

    private void initializeAddPlayerButton() {
        buttonAddPlayer = (Button)fxmlLoader.getNamespace().get("buttonAddPlayer");
        buttonAddPlayer.setOnAction(actionEvent ->
                onPressedButtonAddPlayer());
        buttonAddPlayer.setDisable(true);
    }

    private void initializeTeamTable() {
        tableViewPlayers = (TableView<Player>) fxmlLoader.getNamespace().get("tableViewPlayers");
        players = FXCollections.observableArrayList();
        players.addListener((ListChangeListener<? super Player>) change ->
                onTeamPropertyChanged());
        tableViewPlayers.setItems(players);

        TableColumn<Player, String> tableColumnPlayerName = (TableColumn<Player, String>)
                fxmlLoader.getNamespace().get("tableColumnPlayerName");
        tableColumnPlayerName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, String> tableColumnPlayerRoles = (TableColumn<Player, String>)
                fxmlLoader.getNamespace().get("tableColumnPlayerRoles");
        tableColumnPlayerRoles.setCellValueFactory(cellData ->
                Role.getFormattedRoles(cellData.getValue().getRoles()));

        tableViewPlayers.getColumns().set(0, tableColumnPlayerName);
        tableViewPlayers.getColumns().set(1, tableColumnPlayerRoles);

        tableViewPlayers.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null){
                onSelectedTableViewPlayer();
            }
        });
    }

    private void initializeRemovePlayerButton() {
        buttonRemovePlayer = (Button)fxmlLoader.getNamespace().get("buttonRemovePlayer");
        buttonRemovePlayer.setDisable(true);
        buttonRemovePlayer.setOnAction(actionEvent -> onPressedButtonRemovePlayer());
    }

    private void initializeConfirmButton() {
        buttonConfirm = (Button)fxmlLoader.getNamespace().get("buttonConfirm");
        buttonConfirm.setDisable(true);
        buttonConfirm.setOnAction(actionEvent -> onPressedButtonConfirm());
    }

    private void onTeamPropertyChanged() {
        manageTeamController.handleTeamPropertyChanged(textFieldTeamName.getText(), players.size());
    }

    private void onPlayerPropertyChanged() {
        manageTeamController.handlePlayerPropertyChanged(
                textFieldPlayerName.getText(),
                comboBoxPlayerFirstRole.getValue(),
                comboBoxPlayerSecondRole.getValue(),
                comboBoxPlayerThirdRole.getValue());
    }

    private void onPressedButtonAddPlayer() {
        manageTeamController.handlePressedAddPlayerButton(
                textFieldPlayerName.getText(),
                comboBoxPlayerFirstRole.getValue(),
                comboBoxPlayerSecondRole.getValue(),
                comboBoxPlayerThirdRole.getValue());
    }

    private void onSelectedTableViewPlayer() {
        manageTeamController.handleSelectedPlayerFromTableView();
    }

    private void onPressedButtonRemovePlayer() {
        manageTeamController.handlePressedRemovePlayerButton(
                tableViewPlayers.getSelectionModel().getSelectedItem());
    }

    private void onPressedButtonConfirm() {
        manageTeamController.handlePressedConfirmButton(textFieldTeamName.getText(), players);
    }

    @Override
    public void setTextFieldTeamName(String name) {
        textFieldTeamName.setText(name);
    }

    @Override
    public void setAddPlayerButtonAbility(boolean ability) {
        buttonAddPlayer.setDisable(!ability);
    }

    @Override
    public void setRemovePlayerButtonAbility(boolean ability) {
        buttonRemovePlayer.setDisable(!ability);
    }

    @Override
    public void setConfirmButtonAbility(boolean ability) {
        buttonConfirm.setDisable(!ability);
    }

    @Override
    public void highlightPlayerInTableView(Player player){
        tableViewPlayers.getSelectionModel().select(player);
    }

    @Override
    public void loadPlayersInTable(Set<Player> players) {
        this.players.addAll(players);
    }

    @Override
    public void addPlayerToTableView(Player player) {
        players.add(player);
    }

    @Override
    public void removePlayerFromTableView(Player player) {
        players.remove(player);
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public void show(){
        stage.show();
    }

    @Override
    public void close() {
        stage.close();
    }
}
