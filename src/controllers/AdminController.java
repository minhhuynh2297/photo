package controllers;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;
import model.Users;

import java.io.IOException;
import java.util.Optional;


public class AdminController {

    Users users;

    @FXML
    ListView<User> usersListView;

    @FXML
    TextField addUserTextField;



    ObservableList<User> userNames = FXCollections.observableArrayList();
    @FXML
    public void myStart(){

        userNames.clear();
        for(User s : users.userList){
            if(!s.getUserName().equals("admin"))
                userNames.add(s);
        }
        usersListView.setItems(userNames);

    }

    @FXML
    private void addUserButton(ActionEvent event) throws Exception{

        if(addUserTextField.getText().isEmpty())
            return;


        for(User u : users.userList){
            if(u.getUserName().equals(addUserTextField.getText()) || addUserTextField.getText().equals("admin")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Username already exists");
                alert.setContentText("Pick a new username");;
                alert.showAndWait();
                return;
            }
        }


        User newUser = new User(addUserTextField.getText());
        users.userList.add(newUser);
        addUserTextField.clear();

        myStart();

    }

    @FXML
    private void deleteUserAction(){



        if(usersListView.getSelectionModel().getSelectedItem() == null)
            return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to delete User: " + usersListView.getSelectionModel().getSelectedItem());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            users.userList.remove(usersListView.getSelectionModel().getSelectedItem());
            myStart();
        } else {
            myStart();
            // ... user chose CANCEL or closed the dialog
        }



    }

    @FXML
    private void logoutAction() throws IOException {

        Stage stage = (Stage) usersListView.getScene().getWindow();
        stage.fireEvent( new WindowEvent(
                stage,
                WindowEvent.WINDOW_CLOSE_REQUEST
        ));


    }



}
