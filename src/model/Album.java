package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Album class manages the Album Objects, and relations with the Photo Object
 * @author Eric Martz
 * @author Minh Huynh
 */
public class Album implements Serializable {

    /**
     * name of the album
     */
    private String name;

    /**
     * Data structure used to manage and store Photo objects of an Album object
     */
    private List<Photo> photoList = new ArrayList<Photo>();;

    /**
     * Creates an Album object with a name
     * @param s Name of the Album
     */
    public Album(String s){
        name = s;
    }

    /**
     * Returns all information of an album, if there are photos
     * @return name of the album, the size of the album, and date range of the pictures, if applicable. N/A if no photos
     */
    @Override
    public String toString() {
        try {

            return String.format("%-15s - %-3d - %-15s",name,photoList.size(),getDateRange());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    /**
     * Returns date range of photos in Album
     * @return date range of photos
     * @throws ParseException if there are no photos
     */
    private String getDateRange() throws ParseException {

        if(photoList.isEmpty())
            return "N/A";

        Date dLower = photoList.get(0).getDate();
        Date dUpper = photoList.get(0).getDate();

        for(Photo p : photoList){
            if(p.getDate().compareTo(dLower) < 0){
                dLower = p.getDate();
            }
            if(p.getDate().compareTo(dUpper) > 0){
                dUpper = p.getDate();
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(dLower) + " - " + sdf.format(dUpper);
    }

    /**
     * Sets the name of album
     * @param s is the new name of the album
     */
    public void setName(String s){
        name = s;
    }

    /**
     * removes a photo if it exists and returns a boolean
     * @param s is the photo caption
     * @return true if it exists, false if it does not
     */
    public boolean removePhoto(String s){

        for(Photo p : photoList){
            if(p.getCaption().equals(s)){
                photoList.remove(p);
                return true;
            }
        }
        return false;
    }

    /**
     * @return name of album
     */
    public String getName(){
        return name;
    }

    /**
     * Adds photo into album
     * @param p is the Photo object added
     */
    public void addPhoto(Photo p){
        photoList.add(p);
    }

    /**
     * Retrieves photo from Album
     * @param s is the caption of the Photo
     * @return the Photo Object, or null if it odes not exist
     */
    public Photo getPhoto(String s){
        for(Photo p : photoList){
            if(p.getCaption().equals(s)){
                return p;
            }
        }
        return null;
    }

    /**
     * Removes Photo object from album
     * @param p is the Photo object
     */
    public void removePhoto(Photo p){
        photoList.remove(p);
    }


    /**
     * @return data structure used to manage and store Photo objects
     */
    public List<Photo> getPhotos(){
        return photoList;
    }

    /**
     * @return true if the album is empty,  false if it is not
     */
    public boolean isEmpty(){
        if(photoList.isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * @return amount of photos in the album
     */
    public int getPhotoCount(){
        return photoList.size();
    }


}
