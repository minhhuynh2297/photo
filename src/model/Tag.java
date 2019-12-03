package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Tag class manages the Tag Objects
 * @author Eric Martz
 * @author Minh Huynh
 */
public class Tag implements Serializable {

    /**
     * Identifier of a Tag object
     */
    private String tagName;

    /**
     * Data structure containing attributes for the Tag object
     */
    private List<String> tagAttributes = new ArrayList<String>();

    /**
     * Creates a Tag object
     * @param s is the tagName
     */
    public Tag(String s){
        tagName = s;
    }

    /**
     * Creates a Tag object with one attribute
     * @param s is the tagName
     * @param a is the attribute
     */
    public Tag(String s, String a){
        tagName = s;
        this.addAttributes(a);
    }

    /**
     * Checks if two Tags are the same
     * @param t is the Tag Object
     * @return true if the two Tag objects are the same, false if not
     */
    public boolean isEqual(Tag t){

        if(this.getTagAttributes().isEmpty()){
            System.out.println("empty");
            return false;
        }

        if(this.getTagName().equals(t.getTagName())){

            if(t.getTagAttributes().contains(this.getTagAttributes().get(0))){
                return true;
            }
        }

        return false;
    }

    /**
     * Adds a new attribute to the data structure
     * @param s is the new attribute
     */
    public void addAttributes(String s){
        tagAttributes.add(s);
    }

    /**
     * Removes an attribute from the data structure
     * @param s is the attribute
     */
    public void removeAttribute(String s){
        tagAttributes.remove(s);
    }

    /**
     * @return the tagName of the Tag object
     */
    public String getTagName(){
        return tagName;
    }

    /**
     * @return the list of attributes of the Tag object
     */
    public List<String> getTagAttributes(){
        return tagAttributes;
    }

}
