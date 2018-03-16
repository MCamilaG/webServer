/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.webserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danie
 */
public class webServer implements Runnable{

    private Socket socketClient;
    
    webServer(Socket accept) {
        this.socketClient = accept;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            String source = in.readLine();
            String format;
            byte[] byt;
            String output;            
            
            if(source != null){
                source = source.split(" ")[1];
                if(source.contains(".html")){
                    byt = Files.readAllBytes(new File("./"+source).toPath());
                    output = "" + byt.length;
                    format = "text/html";                   
                }else if(source.contains(".jpg")){
                    byt = Files.readAllBytes(new File("./imagen.html"+source).toPath());
                    output = "" + byt.length;
                    format = "text/html";                                   
                }else{
                    byt = Files.readAllBytes(new File("./index.html").toPath());
                    output = "" + byt.length;
                    format = "text/html";                   
                }
            }else{
                byt = Files.readAllBytes(new File("./index.html"+source).toPath());
                output = "" + byt.length;
                format = "text/html";                               
            }
            
            String encabezado = "HTTP/1.1 200 OK\r\n" 
                                + "Content-Type: " + format + "\r\n"
                                + "Content-Length: " + output
                                + "\r\n\r\n";
            
            byte [] bytes = encabezado.getBytes();
            byte[] respuesta = new byte[byt.length + bytes.length];
            for (int i = 0; i < bytes.length; i++) {respuesta[i] = bytes[i];}
            for (int i = bytes.length; i < bytes.length + byt.length; i++) {
                respuesta[i] = byt[i - bytes.length];
            }            
            socketClient.getOutputStream().write(respuesta);
            socketClient.close();                                                                                                             
            
        } catch (IOException ex) {
            Logger.getLogger(webServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
