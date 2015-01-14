package com.trackback.raspirator.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.trackback.raspirator.console.ServerBridg;
import com.trackback.raspirator.tools.D;

class Server extends Thread implements ServerBridg {
	private Socket socket;
	private int connetctionCounter;
	private final static String TAG = "Server";
	private onServerGetRequest listner;
	private OutputStream os;
	
	public Server(int num, Socket s) {
		super();
		this.connetctionCounter = num;
		this.socket = s;

		setDaemon(true);
		setPriority(NORM_PRIORITY);
		start();
	}
	
	public boolean isListened(){
		return (listner == null)? false : true;
	}
	
	public void setListner(onServerGetRequest listner){
		this.listner = listner;
	}

	@Override
	public void run() {
		try {
			InputStream is = socket.getInputStream();
			os = socket.getOutputStream();
			while (true) {

				byte buf[] = new byte[64 * 1024];
				int r = is.read(buf);

				String data = new String(buf, 0, r);
				D.log(TAG, "Master sad: " + data);
				if(isListened()){
					listner.onGetRequest(data);
				}
				if (data.equals("exit")) {
					D.log(TAG, "Good by!");
					break;
				}
				if(data.equals("die")){
					D.log(TAG, "Die");
					System.exit(0);
					break;
				}
			}
			is.close();
			os.close();
			socket.close();
		} catch (Exception e) {
			D.log(TAG, "I'm frame my Master!");
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendResponse(String data){
		try {
			D.log(data);
			String[] args = data.split("\\r?\\n");
			for (String string : args) {
				D.log(string);
				if(string.getBytes() != null && os != null){
					os.write(string.getBytes());
				}else{
					D.log("Connection stil down!");
				}
						
			}
		} catch (Exception e) {
			D.log("Master can't hear me");
			e.printStackTrace();
		}
	}

	@Override
	public void sendResponseToClient(String data) {
		sendResponse(data);
	}
}
