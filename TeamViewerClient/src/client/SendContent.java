package client;

import commons.ContentType;
import commons.Content;
import commons.MouseDetails;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author Nikhil
 */
public class SendContent {

    private AccessWindow aw;
    private Client client = null;

    public SendContent(Client client) {
        this.client = client;
    }

    public void sendCordinates(int contentType, Point coordinates, int button) {
        client.sendContent(new Content(contentType, new MouseDetails(coordinates, button)));
    }

    public void sendKeyCode(int contentType, int keycode) {
        client.sendContent(new Content(contentType, keycode));
        System.out.println("key sent " + keycode);
    }

    public void sendMessage(String message) {
        client.sendContent(new Content(ContentType.MESSAGE, message));
    }
    
    public void sendImage(BufferedImage img){
        client.sendContent(new Content(ContentType.IMAGE, img));
    }

    public void sendClipboardContent(String str){
        client.sendContent(new Content(ContentType.CLIPBOARDCONTENT, str));
    }
}
