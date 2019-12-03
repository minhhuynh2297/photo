package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;
import model.Users;

import java.io.*;

public class LoginController {



    @FXML
    Button loginButton;

    @FXML
    TextField userTextbox;

    public Stage loginStage;


    @FXML
    private void loginAction(ActionEvent event) throws Exception{


        File f = new File("Obj.txt");
        FileInputStream fis = new FileInputStream(f);
        ObjectInputStream ois = new ObjectInputStream(fis);

        Users users = (Users)ois.readObject();

        ois.close();
        fis.close();

        User currentUser = null;

        if(userTextbox.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No login username");
            alert.setContentText("Please put in a username");;
            alert.showAndWait();
            return;
        }



        for(User t : users.userList){
            System.out.println(t.getUserName());
            if(t.getUserName().equals(userTextbox.getText())){
                currentUser = t;
                System.out.println("Match");
                break;

            }
        }

        if(currentUser == null){
            System.out.println("No match");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Username found");
            alert.setContentText("Please try again");;
            alert.showAndWait();
            return;
        }

        if(currentUser.getUserName().equals("admin")){
            System.out.println("Admin");
            //In admin
            loadAdmin(users);
        }else{
            loadUser(currentUser,users);
        }



    }


    @FXML
    public void loadAdmin(Users u) throws Exception{


        loginButton.getScene().getWindow().hide();

        //Creates pop up Window
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/admindash.fxml"));

        primaryStage.setTitle("Admin Dashboard");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.initModality(Modality.APPLICATION_MODAL);

        //Sets Error Message
        AdminController controller = loader.getController();
        controller.users = u;
        controller.myStart();


        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {

            try {
                u.save();
                loginStage.show();



            } catch (IOException e) {
                e.printStackTrace();
            }
            //Platform.exit();

        });

    }

    @FXML
    public void loadUser(User u, Users users) throws IOException {

        loginButton.getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserDashboard.fxml"));

        primaryStage.setTitle(u.getUserName() + " Dashboard");
        primaryStage.setScene(new Scene(loader.load()));
        //primaryStage.initModality(Modality.APPLICATION_MODAL);
        UserDashboardController controller = loader.getController();
        controller.user = u;
        controller.users = users;
        controller.myStart();


        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {

            try {
                users.save();
                loginStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }


        });





    }


}
