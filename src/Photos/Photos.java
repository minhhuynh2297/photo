package Photos;


import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import model.Album;
import model.Photo;
import model.User;
import model.Users;


import java.io.*;
import java.text.ParseException;

public class Photos extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{




        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));

        //primaryStage.setTitle(u.getUserName() + " Dashboard");
        primaryStage.setScene(new Scene(loader.load()));

        //primaryStage.initModality(Modality.APPLICATION_MODAL);

        LoginController controller = loader.getController();
        controller.loginStage = primaryStage;

        primaryStage.show();

    }


    public static void main(String[] args) throws IOException, ParseException {



//        Users users = new Users();
//
//        User admin = new User("admin");
//        users.userList.add(admin);
//
//        User stockUser = new User("stock");
//        Album testAlbum = new Album("stock");
//        Photo a1 = new Photo("apple.png","src\\pics\\apple.png","11/18/2019");
//        Photo a2 = new Photo("banana.jpg","src\\pics\\banana.jpg","11/18/2019");
//        Photo a3 = new Photo("goose.jpg","src\\pics\\goose.jpg","11/14/2019");
//        Photo a4 = new Photo("orange.png","src\\pics\\orange.png","11/15/2019");
//        Photo a5 = new Photo("watermelon.jpg","src\\pics\\watermelon.jpg","11/17/2019");
//        testAlbum.addPhoto(a1);
//        testAlbum.addPhoto(a2);
//        testAlbum.addPhoto(a3);
//        testAlbum.addPhoto(a4);
//        testAlbum.addPhoto(a5);
//
//        stockUser.addAlbum(testAlbum);
//        users.userList.add(stockUser);


//        Photo apple = new Photo("apple.png","src\\pics\\apple.png","1/1/2020");
//        apple.addTag("color");
//        apple.getTag("color").addAttributes("red");
//
//        Photo orange = new Photo("orange.png","src\\pics\\orange.png","10/1/2020");
//        orange.addTag("color");
//        orange.getTag("color").addAttributes("orange");
//        orange.getTag("color").addAttributes("bright");
//
//        orange.addTag("taste");
//        orange.getTag("taste").addAttributes("sour");
//
//        testAlbum.addPhoto(apple);
//        testAlbum.addPhoto(orange);
//
//        Album album2 = new Album("Album2");
//        album2.addPhoto(new Photo("orange.png","src\\pics\\orange.png","1/1/2020"));
//
//        testUser.addAlbum(testAlbum);
//        testUser.addAlbum(album2);
//
//        users.userList.add(testUser);

//        File f = new File("Obj.txt");
//        FileOutputStream fos = new FileOutputStream(f);
//        ObjectOutputStream oos = new ObjectOutputStream(fos);
//        oos.writeObject(users);
//        fos.close();
//        oos.close();


        launch(args);
    }
}
