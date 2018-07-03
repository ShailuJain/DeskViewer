/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commons;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.imageio.ImageIO;

/**
 *
 * @author Shailesh
 */
public class Content implements Serializable {

    private byte[] content = null;
    private int contentType = -1;

    public Content(int contentType, Object content) {
        this.contentType = contentType;
        this.content = objectToByteArray(content);
    }

    public Object getContent() {
        return byteArrayToObject(content);
    }

    private byte[] objectToByteArray(Object o) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            if (contentType == ContentType.IMAGE) {
                ImageIO.write((BufferedImage) o, "jpg", baos);
            } else {
                oos = new ObjectOutputStream(baos);
                oos.writeObject(o);
            }
        } catch (IOException ex) {
            System.out.println("commons.Content.objectToByteArray()" + ex);
        } finally {
            try {
                oos.close();
            } catch (IOException ex) {
                System.out.println("commons.Content.objectToByteArray()" + ex);
            }
        }
        return baos.toByteArray();
    }

    private Object byteArrayToObject(byte[] b) {
        ObjectInputStream ois = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            if (contentType == ContentType.IMAGE) {
                return ImageIO.read(bais);
            }
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (IOException ex) {
            System.out.println("commons.Content.byteArrayToObject()" + ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("commons.Content.byteArrayToObject()" + ex);
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
                System.out.println("commons.Content.byteArrayToObject()" + ex);
            }
        }
        return null;
    }

    public int getContentType() {
        return contentType;
    }
}
