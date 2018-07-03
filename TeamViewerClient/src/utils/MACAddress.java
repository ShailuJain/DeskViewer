/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Tarun
 */
public class MACAddress {

        private static InetAddress localInetAddr = null;
        private static String macAddress = null;

        static {
                try {
                        localInetAddr = InetAddress.getLocalHost();
                        System.out.println("local" + localInetAddr);
                } catch (UnknownHostException ex) {
                        System.out.println("utils.MACAddress.methodName()" + ex);
                }
        }

        /**
         * The below method is use for get the mac address of particular pc
         *
         * @param:NOthing
         * @return:String   
                *
         */
        public static String getMACAddress() {
                try {
                        if (macAddress == null) {
                                NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localInetAddr);
                                System.out.println("nI" + networkInterface);
                                if (networkInterface != null) {
                                        byte[] mac = networkInterface.getHardwareAddress();
                                        StringBuilder sb = new StringBuilder();
                                        for (int i = 0; i < mac.length; i++) {
                                                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                                        }
                                        macAddress = sb.toString();
                                }
                        }
                } catch (SocketException ex) {
                        System.out.println("utils.MACAddress.getMACAddress()" + ex);
                }
                return macAddress;
        }
}
