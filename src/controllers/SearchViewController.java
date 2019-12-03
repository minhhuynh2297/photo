package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;

import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchViewController {

    User user;



    @FXML
    DatePicker fromDatePicker;

    @FXML
    DatePicker toDatePicker;

    @FXML
    ChoiceBox<String> tag1ChoiceBox;

    @FXML
    TextField value1TextField;

    @FXML
    ChoiceBox<String> andorChoiceBox;

    @FXML
    ChoiceBox<String> tag2ChoiceBox;

    @FXML
    TextField value2TextField;

    public void myStart(){
        initTags();
        andorChoiceBox.getItems().clear();
        andorChoiceBox.getItems().add("And");
        andorChoiceBox.getItems().add("Or");
        andorChoiceBox.getSelectionModel().selectLast();

    }


    @FXML
    private void searchAction() throws IOException {



        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/search.fxml"));

        // primaryStage.setTitle(u.getUserName() + " Dashboard");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        SearchController controller = loader.getController();


        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        if(fromDatePicker.getValue() != null)
            controller.fromDateSearch = fromDatePicker.getValue().format(sdf);

        if(toDatePicker.getValue() != null)
            controller.toDateSearch = toDatePicker.getValue().format(sdf);


        if(tag1ChoiceBox.getValue() != null && value1TextField.getText() != null){
            controller.t1 =(new Tag(tag1ChoiceBox.getValue(),value1TextField.getText()));
        }
        if(tag2ChoiceBox.getValue() != null && value2TextField.getText() != null){
            controller.t2 = (new Tag(tag2ChoiceBox.getValue(),value2TextField.getText()));
        }

        if(andorChoiceBox.getSelectionModel().getSelectedItem().equals("And")){
            controller.isAnd = true;
        }
        else{
            controller.isAnd = false;
        }

        toDatePicker.getScene().getWindow().hide();
        controller.user = user;

        primaryStage.setOnCloseRequest( e -> {

            primaryStage.close();

            Stage stage = (Stage) toDatePicker.getScene().getWindow();
            stage.fireEvent( new WindowEvent(
                    stage,
                    WindowEvent.WINDOW_CLOSE_REQUEST
            ));

        });




        try {
            controller.test();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        primaryStage.show();

    }

    public void initTags() {

        List<String> tager = new ArrayList<String>();

        for (Album a : user.getAlbums()) {
            for (Photo p : a.getPhotos()) {
                for (Tag t : p.getTags()) {
                    tager.add(t.getTagName());
                }
            }
        }

        //Consolidate tags

        tager = tager.stream().distinct().collect(Collectors.toList());

        ObservableList<String> tag1 = FXCollections.observableArrayList();

        for (String s : tager) {
            //System.out.println("tag names " + s);
            tag1.add(s);
        }

        tag1ChoiceBox.setItems(tag1);
        tag2ChoiceBox.setItems(tag1);

    }



}
