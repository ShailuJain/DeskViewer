/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commons;

import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Shailesh
 */
public class MouseDetails implements Serializable{
    private Point mouseCoordinates = null;
    private int button = -1;
    public MouseDetails(Point p){
        this.mouseCoordinates = p;
    }
    public MouseDetails(Point p, int button){
        this.mouseCoordinates = p;
        this.button = button;
    }
    public Point getMouseCoordinates(){
        return mouseCoordinates;
    }
    public int getButton(){
        return button;
    }
}
