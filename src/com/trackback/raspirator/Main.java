package com.trackback.raspirator;

import javafx.application.Application;
import javafx.stage.Stage;

import com.trackback.raspirator.system.Boot;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0){
		Boot boot = new Boot();
	}

}
