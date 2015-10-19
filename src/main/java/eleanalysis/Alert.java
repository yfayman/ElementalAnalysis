/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eleanalysis;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class is designed to open a dialog to alert the user that an error/improper
 * entry has occurred.
 * @author Yan
 */
public class Alert{
    
    
    private Alert(){}; // can't be instanced
    public static void loadMessage(String message){
        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Warning message");
        VBox root = new VBox();
        Label messageLabel = new Label(message);
        messageLabel.setPadding(new Insets(10));
       // messageLabel.set
        Button okButton = new Button("Ok");
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(messageLabel, okButton);
        okButton.setOnAction((event)->{
            dialogStage.close();
        });
        
        
        Scene dialogScene = new Scene(root, 350, 100);
        dialogStage.setScene(dialogScene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }
    
}
