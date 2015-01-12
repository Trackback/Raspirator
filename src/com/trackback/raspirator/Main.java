package com.trackback.raspirator;

import javafx.application.Application;
import javafx.stage.Stage;

import com.trackback.raspirator.settings.Settings;
import com.trackback.raspirator.system.Boot;

public class Main extends Application {
	private static int port = Settings.SERVER_PORT;
	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if(args[i].equals("-p")){
				if(args.length > i + 1){
					int newPort = Integer.parseInt(args[i + 1]);
					if(newPort > 1000) port = newPort;
				}
			}
		}
		launch(args);
	}

	@Override
	public void start(Stage arg0){
		new Boot(port);
	}

}
