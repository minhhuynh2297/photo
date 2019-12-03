package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Album;
import model.Photo;

import javafx.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ConfirmMoveController {

    Photo photo;
    Album toAlbum;
    Album fromAlbum;

    @FXML
    ImageView moveImageView;

    @FXML
    Label confirmLabel;


    @FXML
    public void myStart() throws FileNotFoundException {

        moveImageView.setImage(new Image(new FileInputStream(photo.getLocation())));
        confirmLabel.setText("Move/Copy " + photo.getCaption() + " to " + toAlbum.getName());
    }

    @FXML
    private void moveAction(ActionEvent event){

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

        toAlbum.addPhoto(photo);
        Stage stage = (Stage) confirmLabel.getScene().getWindow();
        stage.fireEvent( new WindowEvent(
                stage,
                WindowEvent.WINDOW_CLOSE_REQUEST
        ));

    }



}
