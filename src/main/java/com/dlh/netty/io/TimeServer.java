package com.dlh.netty.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: dulihong
 * @date: 2019/3/25 15:05
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8081;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("the time server is start in port:" + port);
            Socket socket;
            while (true) {
                socket = server.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (server != null) {
                    System.out.println("the  time server close...");
                    server.close();
                    server = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
