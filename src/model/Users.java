package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/** The Users class manages the Users Objects, and its relations with the User object
 * @author Eric Martz
 * @author Minh Huynh
 */
public class Users implements Serializable {

    /**
     * Data structure containing User objects
     */
    public List<User>userList = new ArrayList<User>();

    /**
     * Creates a Users object
     */
    public Users(){

    }

    /**
     * Saves information onto a text file
     * @throws IOException if the text file is not found or cannot be read / written
     */
    public void save() throws IOException {

        File f = new File("Obj.txt");
        FileOutputStream fos = new FileOutputStream(f);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);

    }



}
