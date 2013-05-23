package com.tju.CanMonitor.Windows;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.tju.CanCommunication.Operation.CanInformation;
import com.tju.CanCommunication.Operation.Initialization;
import com.tju.CanCommunication.Operation.Settings;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Start extends Application{
	
	public static void main(String[] args){
		Application.launch(args);
	}
	
	public void start(Stage stage) throws Exception{
		CanInformation.setDefault();
		initCanBus();
		
		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		Scene scene = new Scene(root,screenSize.width-50,screenSize.height-100);
		stage.initStyle(StageStyle.DECORATED);
		stage.setScene(scene);
		stage.setTitle("MainWindow");
		stage.show();
	}
	
	public void initCanBus(){
		Settings mySetting = new Settings();
		mySetting.clearAndInit();
	}

}
