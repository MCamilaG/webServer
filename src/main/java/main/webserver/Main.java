/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Camila
 */
public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(new Integer(System.getenv("PORT")));
        ExecutorService executor = Executors.newFixedThreadPool(1);
        while (true){
            executor.execute(new webServer(serverSocket.accept()));           
        }
    }  
}
