/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import commons.Content;
import commons.ContentType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Shailesh
 */
public class TVServer {

    private final int PORT;
    private ServerSocket server = null;
    private Map<Integer, ClientHandler> activeClients = null;
    private Random r = new Random(1000);

    public TVServer(int port) {
        PORT = port;
        activeClients = new HashMap<>();
    }

    public void start() throws IOException {
        server = new ServerSocket(PORT);
        System.out.println("start");
        acceptClients();
    }

    private void acceptClients() throws IOException {

        new Thread(() -> {
            try {
                while (true) {
                    System.out.println("acceptClients");
                    Socket client = server.accept();
                    System.out.println("client connected");
                    new ClientHandler(client);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ex" + e);
            }
        }).start();
    }

    private class ClientHandler {

        private Socket socket = null;
        private ObjectInputStream ois = null;
        private ObjectOutputStream oos = null;
        private boolean isSharingScreen = false;
        private int myAccessCode = -1;
        private List<ClientHandler> sharingWith = null;
        private List<ClientHandler> accessingTo = null;
        private String name = null;
        

        ClientHandler(Socket s) {
            System.out.println("in clientHandler");
            socket = s;
            sharingWith = new ArrayList<>();
            accessingTo = new ArrayList<>();
            initStreams();
            recognizeClient();
        }

        private void recognizeClient() {
            new Thread(() -> {
                try {
                    Content nameContent = ((Content) ois.readObject());
                    if(nameContent != null)
                        name = (String)nameContent.getContent();
                    System.out.println("name of client " + name);
                    Content data = (Content) ois.readObject();
                    if (data == null) {
//                        Content macAddr = (Content) ois.readObject();
//                        macAddr 
                        System.out.println("in if of recognizeClient");
                        Content dataToInitClient = null;
                        int rNum = -1;
                        rNum = r.nextInt(9999);
                        myAccessCode = rNum;
                        activeClients.put(rNum, this);
                        dataToInitClient = new Content(ContentType.ACCESSCODE, rNum);
                        writeObject(dataToInitClient);
                    } else if(data.getContentType() == ContentType.ACCESSCODE){
                        int accessCode = (Integer)data.getContent();
                        ClientHandler ch = activeClients.get(accessCode);
                        System.out.println("in else of rC");
                        System.out.println("access code " + accessCode);
                        if (activeClients.containsKey(accessCode) && ch != null) {
                            System.out.println("in if");
                            ch.sharingWith.add(this);
                            System.out.println(name+" added in sharing with of " + ch.name);
                            accessingTo.add(ch);
                            System.out.println(ch.name+" added in accessing to of " + name);
                            if (!ch.isSharingScreen) {
                                ch.start();
                            }
                            start();
                            ch.isSharingScreen = true;
                        } else {
                            close();
                        }
                    }
                } catch (IOException ex) {
                    System.out.println("ex recognizeClient" + ex);
                    close();
                } catch (ClassNotFoundException ex) {
                    System.out.println("ex recognizeClient" + ex);
                    close();
                }
            }).start();
        }

        public void sendToAccessingToClientHandler(Object o) throws IOException {
            for (ClientHandler cH : accessingTo) {
                cH.writeObject(o);
            }
        }

        public void sendToSharingWithClientHandler(Object o) throws IOException {
            for (ClientHandler cH : sharingWith) {
                cH.writeObject(o);
            }
        }

        public void start() {
            try {
                this.writeObject(null);
                System.out.println("in start");
                new Thread(() -> {
                    try {
                        Object obj;
                        while (true) {
                            obj = ois.readObject();
                            if (accessingTo.size() > 0) {
                                sendToAccessingToClientHandler(obj);
                            }
                            if (sharingWith.size() > 0) {
                                sendToSharingWithClientHandler(obj);
                            }
                        }
                    } catch (IOException ex) {
                        System.out.println("IO start" + ex);
                        close();
                    } catch (ClassNotFoundException ex) {
                        System.out.println("CNFE start" + ex);
                        close();
                    }
                }).start();
            } catch (IOException ex) {
                System.out.println("ex start" + ex);
                close();
            }

        }

        public void writeObject(Object o) throws IOException {
            oos.writeObject(o);
            oos.flush();
        }

        private void close() {
            System.out.println("in clsose" + myAccessCode);
            activeClients.remove(myAccessCode);
            if (isSharingScreen) {
                for (ClientHandler c : sharingWith) {
                    c.removeFromAccessingTo(this);
                }
            }
            for (ClientHandler c : accessingTo) {
                c.removeFromSharingWith(this);
            }
            try {
                if (oos != null && ois != null) {
                    oos.close();
                    ois.close();
                }
            } catch (IOException ex) {
                System.out.println("ex in close" + ex);
            }
        }

        public void removeFromSharingWith(ClientHandler ch) {
            sharingWith.remove(ch);
            System.out.println("removed " + ch.name + " from " + name + "'s sharingWith") ;
            if (sharingWith.size() < 1) {
                System.out.println("not sharing with anyone");
                isSharingScreen = false;
                close();
            }
        }

        public void removeFromAccessingTo(ClientHandler ch) {
            accessingTo.remove(ch);
            System.out.println("removed " + ch.name + " from " + name + "'s accessingTo") ;
        }

        private void initStreams() {
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.flush();
                ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {
                System.out.println("ex initStreams" + ex);
                close();
            }
        }
    }
}
