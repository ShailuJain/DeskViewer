package client;

import commons.Content;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author Nikhil
 */
public class ReceiveContent {

    private Client client = null;
    private AccessWindowCaller caller = null;

    public ReceiveContent(AccessWindow aw,Client client) {
        System.out.println("in cons of rI");
        this.accessWindow = aw;
        this.client = client;
        caller = new AccessWindowCaller(accessWindow);
        System.out.println("after initStream");
    }

    public void recieveData() {

        new Thread(() -> {
            System.out.println("Before while 0 ");
            try {
                while (true) {
                    
                    System.out.println("client in rec" + client);
                    content = client.receiveContent();
                    System.out.println("caller " + caller);
                    caller.process(content);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("client.ReceiveImage.recieveData()" + e);
            }

        }).start();
    }

    //Variable declarations
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    private Content content = null;
    private AccessWindow accessWindow = null;
}
