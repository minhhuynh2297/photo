package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Photo class manages the Photo Objects, and its relations with the Tag object
 * @author Eric Martz
 * @author Minh Huynh
 */
public class Photo implements Serializable {

    /**
     * Identifiers of a Photo object
     */
    private String caption;
    private String location;
    private Date date;

    /**
     * Data structure to manage and store Tag objects of a Photo object
     */
    private List<Tag>tags = new ArrayList<Tag>();

    /**
     * Creates a Photo object without date identifier
     * @param c is the caption
     * @param l is the location
     */
    public Photo(String c, String l){
        caption = c;
        location = l;
    }

    /**
     * Creates a Photo object with all three identifiers, date as a Date
     * @param c is the caption
     * @param l is the location
     * @param d is the date as a Date object
     */
    public Photo(String c, String l, Date d){
        caption = c;
        location = l;
        date = d;
    }

    /**
     * @return caption of the Photo object
     */
    @Override
    public String toString(){

        return caption;

    }

    /**
     * Creates a Photo object if date is entered as a String
     * @param c is the caption
     * @param l is the location
     * @param d is the date as a String object
     * @throws ParseException if date is entered in a wrong format
     */
    public Photo(String c, String l, String d) throws ParseException {
        caption = c;
        location = l;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        date = sdf.parse(d);
    }

    /**
     * @return date of Photo object
     */
    public Date getDate(){
        return date;
    }

    /**
     * Sets a new date, using a String formatted date
     * @param s is the new date, as a String object
     * @throws ParseException if the date is entered in the wrong format
     */
    public void setDate(String s) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        date = sdf.parse(s);
    }

    /**
     * sets a new date, using a Date formatted date
     * @param s is the new date, as a Date object
     */
    public void setDate(Date s){
        date = s;
    }

    /**
     * Adds a another Tag object to the Tag data structure
     * @param s is the name of the new Tag using the Tag class constructor
     */
    public void addTag(String s ){
        tags.add(new Tag(s));
    }

    /**
     * Removes a Tag object from the Tag data structure
     * @param s is the name of the Tag object
     */
    public void removeTag(String s){
        tags.remove(s);
    }

    /**
     * @return the Tag data structure
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * @param s is the name of the tag
     * @return a Tag object if it exists, null if it does not
     */
    public Tag getTag(String s){

        for(Tag t : tags){
            if(t.getTagName().equals(s)){
                return t;
            }
        }
        return null;
    }

    /**
     * @return caption of the Photo object
     */
    public String getCaption(){
        return caption;
    }

    /**
     * @return location of the Photo object
     */
    public String getLocation(){
        return location;
    }

    /**
     * Sets a new caption for the Photo object
     * @param s is the new caption of the Photo object
     */
    public void setCaption(String s){
        caption = s;
    }


}
