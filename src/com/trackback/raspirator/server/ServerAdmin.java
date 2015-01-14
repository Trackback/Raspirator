package com.trackback.raspirator.server;

import java.net.ServerSocket;

import com.trackback.raspirator.console.Interpreter;
import com.trackback.raspirator.tools.D;

public class ServerAdmin {
	

    public static void init(Interpreter interpreter, int port){
        try{
            int i = 0;
<<<<<<< HEAD
            ServerSocket server = new ServerSocket(port, 0);
=======
            ServerSocket server = new ServerSocket(port, 0, InetAddress.getByName("localhost"));
>>>>>>> 849731cb6b4e1dfc4b46ca61a9a98d9c2619683c

            D.log("Black Admin", "I'm waiting for the master");
            while(true){
                Server s =   new Server(i, server.accept());
                s.setListner(interpreter.getGetRequestListner());
                interpreter.setServerBridg(s);
                i++;
            }
        }
        catch(Exception e){
        	D.log("Black Admin", "Opps! Master is die");
        	e.printStackTrace();
        }
    }
}
