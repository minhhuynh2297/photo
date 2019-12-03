package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Album;
import model.Photo;
import model.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MoveController {

    Photo photo;
    Album fromAlbum;
    User user;

    @FXML
    ImageView moveImageView;

    @FXML
    ChoiceBox<String> toAlbumChoiceBox;

    @FXML
    Label confirmLabel;


    @FXML
    public void myStart() throws FileNotFoundException {

        moveImageView.setImage(new Image(new FileInputStream(photo.getLocation())));
        confirmLabel.setText("Move/Copy " + photo.getCaption() + " from " + fromAlbum.getName());

        for(Album a : user.getAlbums()){
            toAlbumChoiceBox.getItems().add(a.getName());
        }

        toAlbumChoiceBox.getItems().remove(fromAlbum.getName());


    }

    @FXML
    private void moveAction(ActionEvent event){

        if(toAlbumChoiceBox.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Album Selected");
            alert.setContentText("Please select an Album to move into");;
            alert.showAndWait();
            return;
        }

        Album toAlbum = user.getAlbum(toAlbumChoiceBox.getSelectionModel().getSelectedItem());

        toAlbum.addPhoto(photo);
        fromAlbum.removePhoto(photo);
        Stage stage = (Stage) confirmLabel.getScene().getWindow();
        stage.fireEvent( new WindowEvent(
                stage,
                WindowEvent.WINDOW_CLOSE_REQUEST
        ));
    }

    @FXML
    private void copyAction(ActionEvent event){

        if(toAlbumChoiceBox.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Album Selected");
            alert.setContentText("Please select an Album to move into");;
            alert.showAndWait();
            return;
        }

        Album toAlbum = user.getAlbum(toAlbumChoiceBox.getSelectionModel().getSelectedItem());

        toAlbum.addPhoto(photo);
        Stage stage = (Stage) confirmLabel.getScene().getWindow();
        stage.fireEvent( new WindowEvent(
                stage,
                WindowEvent.WINDOW_CLOSE_REQUEST
        ));

    }

    @FXML
    private void cancelAction(){

    }



}
