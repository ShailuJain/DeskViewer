package client;

import client.ui.ChatPage;
import commons.Content;
import commons.ContentType;
import commons.MouseDetails;
import java.awt.AWTException;
import utils.ClipboardHandler;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tarun
 */
public class Sharing {

        /**
         * ************************************************************
         *
         * This class is use for sending the images to the server for Screen
         * Sharing & also receiving the coordinates from the server whatever the
         * other client is click the coordinates & Also manage KEYEVENT
         *
         *************************************************************
         */
        BufferedImage img;
        Robot robot;
        Content content = null;
        private SharingCaller caller = null;
        public ChatPage chatPage;
        public boolean isSharing = false;
        private Timer t = null;
        private ClipboardHandler clipboardHandler = null;
        private Client client = null;

        /**
         * *********************************This all are the getters and setter
         * of the class********************************
         */
        public void setImg(BufferedImage img) {
                this.img = img;
        }

        public void setRobot(Robot robot) {
                this.robot = robot;
        }

        public void setContent(Content content) {
                this.content = content;
        }

        public BufferedImage getImg() {
                return img;
        }

        public Robot getRobot() {
                return robot;
        }

        public Content getContent() {
                return content;
        }

        /**
         * ***********************Constructor*****************************
         */
        /**
         * The below constructor is use for initialization
         *
         * @param: Client Object
         * @return:Nothing
                 *
         */
        public Sharing(Client client) {
                try {
                        robot = new Robot();
                } catch (AWTException ex) {
                        System.out.println("client.Sharing.<init>()" + ex);
                }
                this.client = client;
                caller = new SharingCaller(this);
                chatPage = new ChatPage(this, null);
                initClipboard();
        }

        /**
         * ***********************METHODS*****************************
         */
        /**
         * The below method is use for share the image to server after every
         * 1000 millisecond of the delay
         *
         * @param: Nothing
         * @return:Nothing
                *
         */
        private void initClipboard() {
                clipboardHandler = new ClipboardHandler(Toolkit.getDefaultToolkit().getSystemClipboard(), () -> {
                        String content1 = "";
                        if (clipboardHandler.getCurrDataFlavor() == DataFlavor.stringFlavor) {
                                try {
                                        content1 = (String) clipboardHandler.getClipboardContent(DataFlavor.stringFlavor);
                                } catch (UnsupportedFlavorException ex) {
                                        System.out.println("Sharing.<init>()" + ex);
                                } catch (IOException ex) {
                                        System.out.println("Sharing.<init>()" + ex);
                                }
                        }
                        client.sendContent(new Content(ContentType.CLIPBOARDCONTENT, content1));
                });
        }

        /**
         * The below method is use for share the image to server after every
         * 1000 millisecond of the delay
         *
         * @param: Nothing
         * @return:Nothing
                *
         */
        public void sendImagesToServer() {
                t = new Timer(1000, (ActionEvent e) -> {
//                img = ImageCompression.compressImage(robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())), 0.7f);
                        img = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
//            img = robot.createScreenCapture(new Rectangle(0, 0, 500, 500));
                        client.sendContent(new Content(ContentType.IMAGE, img));
                });
                t.start();
        }

        /**
         * The below method is wait until the server is not receiving the
         * coordinates or KeyCode which is click by the accessed client
         *
         * @param: Nothing
         * @return:Nothing
                *
         */
        public void receiveData() {
                new Thread(() -> {
                        try {
                                while (true) {
                                        content = client.receiveContent();
                                        caller.process(content);
                                }
                        } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Exception : " + ex + " : receive data", "Sharing", JOptionPane.ERROR_MESSAGE);
                        }
                }).start();
        }

        /**
         * The below method is use for append the massage in the chat page
         * (frame)
         *
         * @param: String
         * @return:Nothing
                *
         */
        public void showMessageOnChat(String str) {
                chatPage.append(str);

        }

        /**
         * The below method is use for set the set the string in the clipboard
         *
         * @param: String Object
         * @return:Nothing
                *
         */
        public void setClipboardContent(String s) {
                clipboardHandler.setStringOnClipboard(s);
        }

        /**
         * The below method is use for moving the cursor in the sharing screen
         * with the help of robot class
         *
         * @param: mousedetails object
         * @return:Nothing
                *
         */
        public void mouseMovedToShareScreen(MouseDetails md) {
                robot.mouseMove(md.getMouseCoordinates().x, md.getMouseCoordinates().y);
        }

        /**
         * The below method is use for press mouse right click or left click
         * where the cursor is present 1 is use for left click 3 is use for
         * right click
         *
         * @param: mousedetails object
         * @return:Nothing
                *
         */
        public void mousePressToShareScreen(MouseDetails md) {
                if (md.getButton() == 1) {
                        robot.mousePress(MouseEvent.getMaskForButton(1));
                } else if (md.getButton() == 3) {
                        robot.mousePress(MouseEvent.getMaskForButton(3));
                }
        }

        /**
         * The below method is use for release mouse right click or left click
         * where the cursor is present 1 is use for left click 3 is use for
         * right click
         *
         * @param: mousedetails object
         * @return:Nothing
                *
         */
        public void mouseReleasedToShareScreen(MouseDetails md) {
                if (md.getButton() == 1) {
                        robot.mouseRelease(MouseEvent.getMaskForButton(1));
                } else if (md.getButton() == 3) {
                        robot.mouseRelease(MouseEvent.getMaskForButton(3));
                }
        }

        /**
         * The below method is use for press the key & print that key on the
         * shared screen
         *
         * @param: integer value (ascii code)
         * @return:Nothing
                *
         */
        public void keyPressToShareScreen(Integer keyCode) {
                robot.keyPress(keyCode);
        }

        /**
         * The below method is use for released the key & print that key on the
         * shared screen
         *
         * @param: integer value (ascii code)
         * @return:Nothing
                 *
         */
        public void keyReleasedToShareScreen(Integer keyCode) {
                robot.keyRelease(keyCode);
        }

        /**
         * The below method is use for send the message to the server which is
         * written by shared person
         *
         * @param: string object (message)
         * @return:Nothing
                *
         */
        public void sendMessage(String message) {
                showMessageOnChat(message);
                client.sendContent(new Content(ContentType.MESSAGE, message));
        }
}
