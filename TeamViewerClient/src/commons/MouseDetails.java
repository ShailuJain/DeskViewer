
package commons;

import java.awt.Point;
import java.io.Serializable;

/**
 * @author Nikhil
 */

/**
 * This class just stores the mouse details.
 */
public class MouseDetails implements Serializable{
    
    /*----------------------------------Constructors----------------------------------*/
    
    /**
     * This constructor just initializes the Point object.
     * @param p value of the point object.
     */
    public MouseDetails(Point p){
        this.mouseCoordinates = p;
    }
    
    /**
     * This constructor just initializes the Point object and button variable.
     * @param p value of the point object.
     * @param button value of button.
     */
    public MouseDetails(Point p, int button){
        this.mouseCoordinates = p;
        this.button = button;
    }
    
    /*----------------------------------End of construcotrs----------------------------------*/
    
    /*----------------------------------Methods----------------------------------*/
    
    /**
     * This method returns the Point object.
     * @return the point object to be returned
     */
    public Point getMouseCoordinates(){
        return mouseCoordinates;
    }
    
    /**
     * This method returns the button value.
     * @return the button value to be returned
     */
    public int getButton(){
        return button;
    }
    
    /*----------------------------------End of Methods----------------------------------*/
    
    //Variable declarations
    private Point mouseCoordinates = null;
    private int button = -1;
    //ENd of variable declarations
}
