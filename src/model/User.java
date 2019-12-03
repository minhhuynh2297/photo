package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

 /** The User class manages the User Objects, and its relations with the Album object
  * @author Eric Martz
  * @author Minh Huynh
  */
public class User implements Serializable {

     /**
      * Identifier for User object
      */
    private String userName;

     /**
      * Data structure used to store Album objects
      */
    private List<Album> userAlbums = new ArrayList<Album>();;

     /**
      * Creates a User object
      * @param n is the userName of the User object
      */
    public User(String n){
        userName = n;
    }

     /**
      * Adds an Album object into the data structure
      * @param a is the Album object
      */
    public void addAlbum(Album a){
        userAlbums.add(a);
    }

     /**
      * @return the data structure of Album objects
      */
    public List<Album> getAlbums(){
        return userAlbums;
    }

     /**
      * Prints out the name of each Album in the data structure
      */
    public void printAlbums(){
        for(Album t: userAlbums){
            System.out.println("Album print " + t.getName());
        }
    }

     /**
      * @return userName of the User
      */
    @Override
    public String toString(){
        return userName;
    }

     /**
      * @param s is the name of the Album
      * @return the Album in the data structure, null if it does not exist
      */
    public Album getAlbum(String s){

        for(Album t : userAlbums){
            if(t.getName().equals(s))
                return t;
        }
        return null;
    }

     /**
      * Removes the Album from the data structure
      * @param s is the name of the Album
      * @return true if it is removed, false if it is not found
      */
    public boolean removeAlbum(String s){

        for(Album t : userAlbums){
            if(t.getName().equals(s)) {
                userAlbums.remove(t);
                return true;
            }
        }
        return false;
    }

     /**
      * removes the Album from the data structure
      * @param a is the name of hte Album
      */
    public void removeAlbum(Album a){
        userAlbums.remove(a);
    }

     /**
      * @return userName of the User object
      */
    public String getUserName(){
        return userName;
    }



}
