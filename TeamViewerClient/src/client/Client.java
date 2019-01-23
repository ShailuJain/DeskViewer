package client;


import client.ui.MainPanel;
import commons.Content;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rp wadhwani
 */
public class Client {
    private int port;
    private Socket socket=null;
    MainPanel mainPanel=null;
    private String ip;
    public Client(String ip,int port,MainPanel mainPanel) {
        this.ip=ip;
        this.port=port;
        this.mainPanel=mainPanel;
        initClient();
    }
    public Content receiveContent(){
        try {
            Content content =(Content)ois.readObject();
            return content;
        } catch (IOException ex) {
            System.out.println("Exception:"+ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("Exception:"+ex);
        }
        return null;
    }

    private void initClient() {
        try {
            System.out.println("Created ");
            socket=new Socket(ip,port);
            initStreams();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    private void initStreams(){
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("Client.initStreams()" + ex);
        }
    }

    public void sendContent(Content content) {
        try {
            oos.writeObject(content);
            oos.flush();
        } catch (IOException ex) {
            System.out.println("exception :"+ex);
        }
    }
    public Socket getSocket(){
        return socket;
    }
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;

    void close() {
        try {
            ois.close();
            oos.close();
        } catch (IOException ex) {
            System.out.println("Client.close()" + ex);
        }
    }
}
