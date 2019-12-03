package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;
import org.w3c.dom.Text;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchController {

    String tag1;
    String value1;
    String tag2;
    String value2;

    Boolean isAnd;

    Tag t1;
    Tag t2;

    String fromDateSearch;
    User user;
    String tagValues;
    String toDateSearch;
    List<Tag> tagSearch = new ArrayList<Tag>();

    Album tempAlbum = new Album("temp");

    @FXML
    ListView<Photo> resultsListView;


    @FXML
    TextField createAlbumTextField;

    @FXML
    public void test() throws ParseException {


        search();
    }

    public void search() throws ParseException {


        //Assume every photo is good
        for(Album a : user.getAlbums()){
            for(Photo p : a.getPhotos()){
                tempAlbum.addPhoto(p);
            }
        }

        fromDateFilter();
        toDateFilter();
        tagFilter();


        ObservableList<Photo> filteredList = FXCollections.observableArrayList();

        for(Photo p : tempAlbum.getPhotos()){
            filteredList.add(p);
        }

        resultsListView.setItems(filteredList);

        resultsListView.setCellFactory(param -> new ListCell<Photo>() {

            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(Photo name, boolean empty) {
                super.updateItem(name, empty);
                if(empty){
                    setText(null);
                    setGraphic(null);
                }else{
                    try {
                        imageView.setImage(new Image(new FileInputStream(name.getLocation())));
                        imageView.setFitHeight(50);
                        imageView.setFitWidth(50);
                        imageView.setPreserveRatio(true);
                        setText(name.toString());
                        setGraphic(imageView);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

    }

    @FXML
    public void deleteAction(){

    }

    @FXML
    public void createAlbumAction(){


        if(createAlbumTextField.getText() == null || createAlbumTextField.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Album Name");
            alert.setContentText("Please input a new album name");;
            alert.showAndWait();

            return;
        }

        if(user.getAlbum(createAlbumTextField.getText()) instanceof Album){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Album Name Taken");
            alert.setContentText("Please input a new album name");;
            alert.showAndWait();

            return;
        }

        tempAlbum.setName(createAlbumTextField.getText());
        user.addAlbum(tempAlbum);

        Stage stage = (Stage) createAlbumTextField.getScene().getWindow();
        stage.fireEvent( new WindowEvent(
                stage,
                WindowEvent.WINDOW_CLOSE_REQUEST
        ));

    }

    @FXML
    public void detailsAction(){

    }

    public void fromDateFilter() throws ParseException {

        if(fromDateSearch == null)
            return;


//        if(fromDateSearch == null){
//            ///no parameters add all
//                for(Album a : user.getAlbums()){
//                    for(Photo p : a.getPhotos()){
//                        tempAlbum.addPhoto(p);
//                    }
//                }
//            return;
//        }

        Album temp = new Album("temp");

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        Date fromDate = sdf.parse(fromDateSearch);


        for(Photo p : tempAlbum.getPhotos()){

            //Date pDate = sdf.parse(p.getDate().toString());
            if(fromDate.compareTo(p.getDate()) < 0){
                //System.out.println("TEST TEST TEST");
                temp.addPhoto(p);
            }
        }

        tempAlbum = temp;




    }

    public void toDateFilter() throws ParseException {

        if(toDateSearch == null)
            return;


        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        Date fromDate = sdf.parse(toDateSearch);

        Album temp = new Album("temp");

        for(Photo p : tempAlbum.getPhotos()){

            //Date pDate = sdf.parse(p.getDate().toString());

            if(fromDate.compareTo(p.getDate()) > 0){
                temp.addPhoto(p);
            }
        }

        tempAlbum = temp;

    }




    public void tagFilter(){


        if(tempAlbum.isEmpty())
            return;

        if(t1 == null)
            return;

        Album newTempAlbum = new Album("temp2");


        for(Photo p : tempAlbum.getPhotos()){
            for(Tag t : p.getTags()){

                if(t1.isEqual(t)){
                    if(isAnd){
                        if(t2.isEqual(t)){
                            newTempAlbum.addPhoto(p);
                            System.out.println("test");
                            break;
                        }
                    }else{
                        newTempAlbum.addPhoto(p);
                        break;
                    }
                }
            }
        }

        tempAlbum = newTempAlbum;
    }
}
