package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.Album;
import model.Photo;
import model.Tag;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class DetailsController {

    public Album userAlbum;
    public Photo selectedPhoto;

    @FXML
    ImageView mainImageView;

    @FXML
    DatePicker editDatePicker;

    @FXML
    Text tagText;

    @FXML
    TextField editCaptionTextField;

    @FXML
    TextField addTagValueTextField;

    @FXML
    TextField addTagNameTextField;

    @FXML
    ChoiceBox tagChoiceBox;


    @FXML
    Text captionText;

    @FXML
    Text dateText;

    @FXML
    public void myStart() throws FileNotFoundException {
        Image newImage = new Image(new FileInputStream(selectedPhoto.getLocation()));
        mainImageView.setImage(newImage);



        String tags = "";
        int x = 1;
        for(Tag t : selectedPhoto.getTags()){

            tags = tags + t.getTagName() + ": ";
            for(String s : t.getTagAttributes()){
                tags = tags + s + ", ";

            }
            tags = tags + "\n";
            x++;
        }
       // tagText.setText("Tags: " + tags.substring(0,tags.length()-2)); //removes last comma

        tagText.setText("Tags: \n" + tags);
        captionText.setText("Caption : " + selectedPhoto.getCaption());



//        File picture = new File(selectedPhoto.getLocation());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        dateText.setText(sdf.format(selectedPhoto.getDate()));


        ObservableList<String> obs = FXCollections.observableArrayList();
        //Set choicebox for all tag pairs
        for(Tag t : selectedPhoto.getTags()){
            for(String s : t.getTagAttributes()){
                obs.add(t.getTagName() + "-" + s);
            }
        }

        tagChoiceBox.setItems(obs);

    }

    @FXML
    private void nextPhotoAction(ActionEvent event) throws FileNotFoundException {

        int selectedIndex = userAlbum.getPhotos().indexOf(selectedPhoto);

        if(selectedIndex == userAlbum.getPhotoCount()-1){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No More Photos");
            alert.showAndWait();
            return;
        }

        selectedPhoto = userAlbum.getPhotos().get(selectedIndex+1);
        myStart();
    }

    @FXML
    private void previousPhotoAction(ActionEvent event) throws FileNotFoundException {

        int selectedIndex = userAlbum.getPhotos().indexOf(selectedPhoto);

        if(selectedIndex == 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No More Photos");
            alert.showAndWait();
            return;
        }

        selectedPhoto = userAlbum.getPhotos().get(selectedIndex-1);
        myStart();
    }

    @FXML
    private void deleteTagAction(ActionEvent event) throws FileNotFoundException {

        if(tagChoiceBox.getSelectionModel().getSelectedItem() == null)
            return;

        String selectString = tagChoiceBox.getSelectionModel().getSelectedItem().toString();

        String tagName = selectString.substring(0,selectString.indexOf('-'));
        String tagAtr = selectString.substring(selectString.indexOf('-')+1);

        selectedPhoto.getTag(tagName).removeAttribute(tagAtr);

        //if there is no more attributes in the tag, delete it
        if(selectedPhoto.getTag(tagName).getTagAttributes().isEmpty()){
            selectedPhoto.getTags().remove(selectedPhoto.getTag(tagName));
        }

//        selectedPhoto.removeTag(tagChoiceBox.getSelectionModel().getSelectedItem().toString());
//        tagChoiceBox.getItems().remove(tagChoiceBox.getSelectionModel().getSelectedItem());
        myStart();

    }

    @FXML
    private void addTagAction(ActionEvent event) throws FileNotFoundException {

        if(addTagNameTextField.getText().isEmpty() || addTagValueTextField.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Tag added");
            alert.setContentText("Please make a tag to use");;
            alert.showAndWait();

            return;
        }



        for(Tag t : selectedPhoto.getTags()){

            if(t.getTagName().equals(addTagNameTextField.getText())){
                //adding to existing tag
                for(String s : t.getTagAttributes()){
                    if(s.equals(addTagValueTextField.getText())){
                        //check to see if the attribute exists
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Tag Already exists");
                        alert.setContentText("Please select an new tag to make");;
                        alert.showAndWait();
                        return;
                    }
                }
                t.addAttributes(addTagValueTextField.getText());
                myStart();
                return;
            }
        }

        //If we are here, then we are have not found a tag name that matches. new tag is being made

        selectedPhoto.addTag(addTagNameTextField.getText());
        selectedPhoto.getTag(addTagNameTextField.getText()).addAttributes(addTagValueTextField.getText());

        myStart();

    }

    @FXML
    private void editCaptionAction(ActionEvent event) throws FileNotFoundException {

        if(editCaptionTextField.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Caption entered");
            alert.setContentText("Please enter a caption to change into");;
            alert.showAndWait();

            return;
        }

        selectedPhoto.setCaption(editCaptionTextField.getText());
        myStart();
    }

    @FXML
    private void editDateAction(ActionEvent event) throws FileNotFoundException, ParseException {


        if(editDatePicker.getValue() == null){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Date Selected");
            alert.setContentText("Please select a date to change");;
            alert.showAndWait();

            return;


        }

        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        selectedPhoto.setDate( editDatePicker.getValue().format(sdf));

       myStart();


    }

}


