package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Optional;


public class UserDashboardController {

    User user;
    Users users;


    @FXML
    TextField addAlbumTextField;


    @FXML
    Label welcomeLabel;

    @FXML
    TextField renameAlbumTextField;

    @FXML
    ListView<Photo> photoList;

    @FXML
    ListView<Album> albumList;


    @FXML
    private void initPhotos() throws FileNotFoundException {

        Album selectedAlbum = albumList.getSelectionModel().getSelectedItem();


        ObservableList<Photo> songObsList = FXCollections.observableArrayList();

        if(user.getAlbums().isEmpty()){
            photoList.setItems(songObsList); //set empty
            return;
        }

        if(selectedAlbum == null)
            return;




        for(Photo p : selectedAlbum.getPhotos()){
            //System.out.println(p.getCaption());
            songObsList.add(p);
        }

        photoList.setItems(songObsList);


        if(selectedAlbum.getPhotos().isEmpty()){
            System.out.println("empty album");
            return;
        }

        photoList.setCellFactory(param -> new ListCell<Photo>() {

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
                        imageView.setFitHeight(60);
                        imageView.setFitWidth(60);
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
    private void addAlbumAction(ActionEvent event) throws FileNotFoundException {

        if(addAlbumTextField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No album name");
            alert.setContentText("Please put in a album name!");;
            alert.showAndWait();
            return;
        }

        if(user.getAlbum(addAlbumTextField.getText()) instanceof  Album){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Album already exists");
            alert.setContentText("Please choose a unique album name!");;
            alert.showAndWait();
            //System.out.println("Album exists");
            return;
        }

        user.addAlbum(new Album(addAlbumTextField.getText()));
        initAlbums();
        albumList.getSelectionModel().selectLast();
        initPhotos();

        addAlbumTextField.clear();
    }

    @FXML
    private void addPhotoAction(ActionEvent event) throws Exception{


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Add Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(photoList.getScene().getWindow());


        if(selectedFile == null){
            System.out.println("Aborted add photo");
            return;
        }

        String selctedAlbumName = albumList.getSelectionModel().getSelectedItem().getName();
        //Album selectedAlbum = ;

        for(Photo p : user.getAlbum(selctedAlbumName).getPhotos()){
            if(p.getCaption().equals(selectedFile.getName())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Photo Already exists in album");
                alert.setContentText("Please add a new photo");;
                alert.showAndWait();
                //System.out.println("Photo already exists");
                return;
            }

        }

        Photo newPhoto = new Photo(selectedFile.getName(),selectedFile.getAbsolutePath());

        File pictureDate = new File(newPhoto.getLocation());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        newPhoto.setDate(sdf.format(pictureDate.lastModified()));

        user.getAlbum(selctedAlbumName).addPhoto(newPhoto);

        Album a = albumList.getSelectionModel().getSelectedItem();

        //var index = albumList.getSelectionModel().getSelectedIndex();
        refresh();
        //ttesalbumList.getSelectionModel().select(a);

    }

    @FXML
    public void deleteAlbumAction() throws FileNotFoundException {

        Album albumName = albumList.getSelectionModel().getSelectedItem();

        if(albumName == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Album Selected");
            alert.setContentText("Please select an Album to delete");;
            alert.showAndWait();

            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to delete Album: " + albumList.getSelectionModel().getSelectedItem().getName());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            user.removeAlbum(albumName);
            refresh();
        } else {
            // ... user chose CANCEL or closed the dialog
        }

        //user.printAlbums();


       // refresh();



    }

    @FXML
    public void renameAlbumAction(){

        String selectedAlbum = albumList.getSelectionModel().getSelectedItem().toString();

        if(selectedAlbum == null){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Album Selected");
            alert.setContentText("Please select an Album to rename");;
            alert.showAndWait();
            return;
        }

        if(renameAlbumTextField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No rename album field");
            alert.setContentText("Please input a new name for the album");;
            alert.showAndWait();

            return;
        }

        if(user.getAlbum(renameAlbumTextField.getText()) instanceof  Album){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Name already taken");
            alert.setContentText("Please choose a unique name");;
            alert.showAndWait();

            System.out.println("Album exists");
            //renameAlbumTextField.clear();
            return;
        }

        albumList.getSelectionModel().getSelectedItem().setName(renameAlbumTextField.getText());
        //user.getAlbum(selectedAlbum).setName(renameAlbumTextField.getText());

        initAlbums();
        //albumList.getSelectionModel().select(user.getAlbum(renameAlbumTextField.getText()));
        albumList.getSelectionModel().select(user.getAlbum(renameAlbumTextField.getText()));
        renameAlbumTextField.clear();

    }

    @FXML
    public void logoutAction(){

        Stage stage = (Stage) albumList.getScene().getWindow();
        stage.fireEvent( new WindowEvent(
                stage,
                WindowEvent.WINDOW_CLOSE_REQUEST
        ));
    }

    @FXML
    public void makeSearchAction() throws IOException {


        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SearchView.fxml"));

        //primaryStage.setTitle(u.getUserName() + " Dashboard");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.initModality(Modality.APPLICATION_MODAL);

        SearchViewController controller = loader.getController();
        controller.user = user;

        primaryStage.setOnCloseRequest( e -> {

            try {
                refresh();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            primaryStage.close();
        });

        controller.myStart();

        primaryStage.show();




    }






    @FXML
    public void deletePhotoAction() throws FileNotFoundException {

        Photo selectedPhoto = photoList.getSelectionModel().getSelectedItem();

        if(selectedPhoto == null){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Photo Selected");
            alert.setContentText("Please select an Photo to delete");;
            alert.showAndWait();

            return;
        }

        albumList.getSelectionModel().getSelectedItem().removePhoto(selectedPhoto);

        //user.getAlbum(albumList.getSelectionModel().getSelectedItem().toString()).removePhoto(selectedPhotoName);
        initPhotos();
    }


    public void initAlbums(){




        ObservableList<Album> obAlbumList = FXCollections.observableArrayList();
        if(user.getAlbums().isEmpty()){
            albumList.setItems(obAlbumList); //set empty
            return;
        }

        for(Album a : user.getAlbums()){
            obAlbumList.add(a);
        }

        albumList.setItems(obAlbumList);
       // albumList.getSelectionModel().select(0);
    }



    public void refresh() throws FileNotFoundException {

        initAlbums();
        initPhotos();


    }

    public void myStart() throws FileNotFoundException {

        welcomeLabel.setText("Welcome: " + user.getUserName());

        initAlbums();
        if(albumList.getItems().isEmpty())
            return;
        albumList.getSelectionModel().selectFirst();

        initPhotos();



    }

    @FXML
    public void handleRecDragOver(DragEvent event){
        if(event.getDragboard().hasString()){
            event.acceptTransferModes(TransferMode.ANY);
        }
    }


    @FXML
    public void handleDragOver(DragEvent event){
        if(event.getDragboard().hasString()){
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    public void handleDrop(DragEvent event) throws IOException {




        ListCell<Album> targetCell = (ListCell<Album>) event.getTarget();







        if(!(targetCell.getItem() instanceof Album))
            return;



        if(targetCell.getItem().equals(albumList.getSelectionModel().getSelectedItem().getName())) {
            System.out.println("same albium");
            return;
        }
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/confirmMove.fxml"));

        // primaryStage.setTitle(u.getUserName() + " Dashboard");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.initModality(Modality.APPLICATION_MODAL);

        ConfirmMoveController controller = loader.getController();

        controller.toAlbum = targetCell.getItem();
        controller.fromAlbum = albumList.getSelectionModel().getSelectedItem();

        controller.photo = photoList.getSelectionModel().getSelectedItem();
        //controller.photo = albumList.getSelectionModel().getSelectedItem().getPhoto(photoList.getSelectionModel().getSelectedItem());
        //controller.photo = user.getAlbum(albumList.getSelectionModel().getSelectedItem().toString()).getPhoto(photoList.getSelectionModel().getSelectedItem());


        primaryStage.setOnCloseRequest( e -> {
            try {
                refresh();
                //albumList.getSelectionModel().select(targetCell.getItem());
                initPhotos();

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }


            primaryStage.close();

        });

        controller.myStart();

        primaryStage.show();


    }

    @FXML
    private void moveButtonAction() throws IOException {


        if(photoList.getSelectionModel().getSelectedItem() == null){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Photo Selected");
            alert.setContentText("Please select an Photo to move");;
            alert.showAndWait();
            return;
        }

        if(user.getAlbums().size() <= 1){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Only One album");
            alert.setContentText("Need at least two albums to make a move");;
            alert.showAndWait();
            return;
        }

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/moveview.fxml"));

        primaryStage.setTitle("Confirm Move");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.initModality(Modality.APPLICATION_MODAL);

        MoveController controller = loader.getController();
        controller.fromAlbum = albumList.getSelectionModel().getSelectedItem();
        controller.photo = photoList.getSelectionModel().getSelectedItem();
        controller.user = user;

        primaryStage.setOnCloseRequest( e -> {

            primaryStage.close();
            try {
                refresh();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        controller.myStart();

        primaryStage.show();



    }



    @FXML
    public void handleDragDetected(MouseEvent event){
        Dragboard db = photoList.startDragAndDrop(TransferMode.ANY);

        ClipboardContent cp = new ClipboardContent();
        cp.putString(photoList.getSelectionModel().getSelectedItem().toString());
        db.setContent(cp);
        event.consume();
    }


    @FXML
    private void detailAction() throws IOException {



        if(photoList.getSelectionModel().getSelectedItem() == null){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Photo Selected");
            alert.setContentText("Please select a photo to view details");;
            alert.showAndWait();

            //System.out.println("no photo selected");
            return;
        }

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/detailview.fxml"));

       // primaryStage.setTitle(u.getUserName() + " Dashboard");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        DetailsController controller = loader.getController();



        //controller.userAlbum = user.getAlbum(albumList.getSelectionModel().getSelectedItem().toString());
        controller.userAlbum = albumList.getSelectionModel().getSelectedItem();

        //controller.selectedPhoto = user.getAlbum(albumList.getSelectionModel().getSelectedItem().toString()).getPhoto(photoList.getSelectionModel().getSelectedItem());
        //controller.selectedPhoto = albumList.getSelectionModel().getSelectedItem().getPhoto(photoList.getSelectionModel().getSelectedItem());
        controller.selectedPhoto = photoList.getSelectionModel().getSelectedItem();

        controller.myStart();


        primaryStage.show();

        //photoList.getScene().getWindow().hide();


        primaryStage.setOnCloseRequest(event -> {
            try {
                refresh();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            primaryStage.close();

        });




    }






}
