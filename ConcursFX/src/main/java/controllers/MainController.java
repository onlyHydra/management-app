package controllers;

import concurs.model.Concurs;
import concurs.model.Inscris;
import concurs.model.Participant;
import concurs.model.User;
import concurs.services.ConcursException;
import concurs.services.IConcursObserver;
import concurs.services.IConcursServices;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController extends UnicastRemoteObject implements Initializable, IConcursObserver, Serializable {

    private IConcursServices server;
    private User user;

    @FXML
    TableView<Concurs> tableConcurs;
    @FXML
    ComboBox<Concurs> comboBoxConcurs;
    @FXML
    TextField teamField;
    @FXML
    TableView<Participant> tableParticip;
    @FXML
    ComboBox<Concurs> comboBoxCap;
    @FXML
    TextField teamNameField;
    @FXML
    TextField nameField;
    @FXML
    Button inscrieBtn;
    @FXML
    Label raceLabel;
    @FXML
    Button logoutBtn;
    @FXML Button findBtn;

    Concurs selectedRace;

    ObservableList<Participant> participants = FXCollections.observableArrayList();
    ObservableList<Concurs> curse = FXCollections.observableArrayList();

    public MainController() throws RemoteException {
    }

    @Override
    public void participantInscris(Participant participant) throws ConcursException {
        participants.add(participant);
        tableParticip.setItems(participants);
        updateCursa(participant);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleInscrie(){
        String err = "";
        System.out.println("Sunt aici");
        if(selectedRace != null){
            if(selectedRace.getCapacitate().equals(comboBoxCap.getValue().getCapacitate())){
                Participant participant = new Participant(0, nameField.getText(), teamNameField.getText(), selectedRace.getCapacitate(), selectedRace.getId());
                Inscris inscris = new Inscris(participant, selectedRace);
                try {
                    server.inscriere(inscris);
                }catch (ConcursException ex){
                    err+="No race was selected";
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Error");
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }
            }
            else err += "Wrong capacity";
        }
        else {
            err+="No race was selected";
        }
        if (err != "") {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error");
            alert.setContentText(err);
            alert.showAndWait();
        }

    }

    public void handleLogout(){
        logout();
        try {
            Platform.exit();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void minimize(ActionEvent actionEvent){
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void logout() {
        try {
            server.logout(user, this);
        } catch (ConcursException e) {
            System.out.println("Logout error " + e);
        }
    }

    private boolean existCap(Concurs c, ArrayList<Concurs> l){
        for(Concurs concurs:l){
            if(c.getCapacitate().equals(concurs.getCapacitate()))
                return true;
        }
        return false;
    }

    public void handleChangeTeam(){
        reloadParticip();
    }

    public void handleChangeCap(){
        reloadCurse();
    }

    public void reloadCurse(){
        curse.clear();
        try {
            Concurs[] arr;
            if(comboBoxConcurs.getValue() == null)
                arr = server.getAllConcurs();
            else {
                arr = server.getConcursByCap(comboBoxConcurs.getValue().getCapacitate());
            }
            for(int i=0; i<arr.length; i++)
                curse.add(arr[i]);
            tableConcurs.setItems(curse);
        }catch (ConcursException ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error");
            alert.setContentText("Eroare la setarea curselor");
            alert.showAndWait();
        }

    }

    private void updateCursa(Participant participant) {
        /*ObservableList<Concurs> newCurse = FXCollections.observableArrayList();
        for (Concurs concurs : curse){
            if(concurs.getId().equals(participant.getIdCursa())){
                Concurs nc = concurs;
                nc.setNumar(concurs.getNumar() + 1);
                newCurse.add(concurs);
            }else newCurse.add(concurs);
        }
        curse = newCurse;
        tableConcurs.setItems(curse);*/
        Platform.runLater(()->{
            /*ObservableList<Concurs> newCurse = FXCollections.observableArrayList();
            for (Concurs concurs : curse){
                if(concurs.getId().equals(participant.getIdCursa())){
                    Concurs nc = concurs;
                    nc.setNumar(concurs.getNumar() + 1);
                    newCurse.add(concurs);
                }else newCurse.add(concurs);
            }
            curse = newCurse;
            tableConcurs.setItems(curse);*/
            reloadCurse();
        });

    }

    private class UpdateRunnable implements Runnable{
        Participant participant;
        @Override
        public void run() {
            ObservableList<Concurs> newCurse = FXCollections.observableArrayList();
            for (Concurs concurs : curse){
                if(concurs.getId().equals(participant.getIdCursa())){
                    Concurs nc = concurs;
                    nc.setNumar(concurs.getNumar() + 1);
                    newCurse.add(concurs);
                }else newCurse.add(concurs);
            }
            curse = newCurse;
            tableConcurs.setItems(curse);
        }

        public void setParticip(Participant participant){
            this.participant = participant;
        }
    }

    public void reloadParticip(){
        participants.clear();
        try {
            Participant[] arr;
            if (teamField.getText().isEmpty()){
                arr = server.getAllParticip();
            }else {
                arr = server.getParticipantByTeam(teamField.getText());
            }
            for (int i=0; i<arr.length; i++)
                participants.add(arr[i]);
            tableParticip.setItems(participants);
        }catch (ConcursException ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    public void setParticip(){
        reloadParticip();
    }

    public void setCurse(){
        try {
            Concurs[] arr = server.getAllConcurs();
            System.out.println(arr.length);
            ArrayList<Concurs> toAddinCombo = new ArrayList<>();
            for(int i=0; i<arr.length; i++){
                curse.add(arr[i]);
                if(existCap(arr[i], toAddinCombo) == false){
                    toAddinCombo.add(arr[i]);
                }
            }
            ObservableList<Concurs> forComborBox = FXCollections.observableArrayList(toAddinCombo);
            comboBoxCap.setItems(forComborBox);
            comboBoxConcurs.setItems(forComborBox);
            reloadCurse();

            tableConcurs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Concurs>() {
                @Override
                public void changed(ObservableValue<? extends Concurs> observable, Concurs oldValue, Concurs newValue) {
                    raceLabel.setText("");
                    if (tableConcurs.getSelectionModel().getSelectedItem() != null) {
                        raceLabel.setText(tableConcurs.getSelectionModel().getSelectedItem().getNume());
                        selectedRace = tableConcurs.getSelectionModel().getSelectedItem();
                    }
                }
            });


        }catch (ConcursException ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error");
            alert.setContentText("Eroare la setarea curselor");
            alert.showAndWait();
        }

    }

    public void setServer(IConcursServices server) {
        this.server = server;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
