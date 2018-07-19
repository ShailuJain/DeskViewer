
package client;

import commons.Content;
import commons.ContentType;
import java.awt.image.BufferedImage;

/**
 *
 * @author Nikhil
 */

/**
 * This class is used to calls the functions of AccessWindow class according to the content object. 
 */
public class AccessWindowCaller {

    /*----------------------------------Constructor----------------------------------*/
    
    /**
     * This constructor just initializes the accessWindow object.
     */
    public AccessWindowCaller(AccessWindow accessWindow) {
        this.accessWindow = accessWindow;
    }
    
    /*----------------------------------End of constructor----------------------------------*/

    /*----------------------------------Methodd----------------------------------*/
    
    /**
     * This method is used to call the appropriate methods for the content object.
     * @param c the content object for which the method has to be called.
     */
    public void process(Content c) {
        int choice = c.getContentType();
        Object content = c.getContent();
        switch (choice) {
            case ContentType.IMAGE:
                accessWindow.displayImage((BufferedImage)content);
                break;
            case ContentType.MESSAGE:
                accessWindow.showMessageOnChat((String) content);
                break;
            case ContentType.CLIPBOARDCONTENT:
                accessWindow.setClipboardContent((String) content);
        }
    }
    
    /*----------------------------------End of method----------------------------------*/
    
    //Variable declaraations
    private AccessWindow accessWindow = null;
    //End of variable decalartions
}
