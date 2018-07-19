
package commons;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author Nikhil
 */

/**
 * This class is used for converting the object into byte array and byte array to object.
 */
public class Content implements Serializable {

    /*----------------------------------Constructor----------------------------------*/
    
    /**
     * This constructor initializes the contentType variable and the content object.
     * @param contentType value of the contentType
     * @param content content object 
     */
    public Content(int contentType, Object content) {
        this.contentType = contentType;
        this.content = objectToByteArray(content);
    }
    
    /*----------------------------------End of constructor----------------------------------*/
    
    /*----------------------------------Methods----------------------------------*/
    
    /**
     * This methods will convert the given object to a byte array by using ByteArrayOutputStream.
     * @param object the object to be converted into byte array.
     * @return this method will return the byte array.
     */
    private byte[] objectToByteArray(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            if (contentType == ContentType.IMAGE) {
                ImageIO.write((BufferedImage) object, "jpg", baos);
            } else {
                oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"commons.Content.objectToByteArray()" + ex);
        } finally {
            try {
                if(oos!=null)
                    oos.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"commons.Content.objectToByteArray()" + ex);
            }
        }
        return baos.toByteArray();
    }

    /**
     * This methods will convert the given byte array to a Object by using ByteArrayInputStream.
     * @param byteArray the byte array to be converted into Object.
     * @return this method will return the Object.
     */
    private Object byteArrayToObject(byte[] byteArray) {
        ObjectInputStream ois = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
            if (contentType == ContentType.IMAGE) {
                return ImageIO.read(bais);
            }
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("commons.Content.byteArrayToObject()" + ex);
        } finally {
            try {
                if(ois!=null)
                    ois.close();
            } catch (IOException ex) {
                System.out.println("commons.Content.byteArrayToObject()" + ex);
            }
        }
        return null;
    }
    
    /*Getters*/
    
    /**
     * This method will return the contentType variable.
     */
    public int getContentType() {
        return contentType;
    }
    
    /**
     * This method will return the content object.
     */
    public Object getContent() {
        return byteArrayToObject(content);
    }
    
    /*End of getters*/
    
    /*----------------------------------End of methods----------------------------------*/
    
    //Variable declarations
    private byte[] content = null;
    private int contentType = -1;
    //End of variable declarations
}
