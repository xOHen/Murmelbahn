/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lhm;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
/**
 *
 * @author Michael
 */
public class LHM extends Application {

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        
        Label filler = new Label("Fill");
        grid.add(filler, 3, 1);
        
        Button start = new Button();
        start.setText("Start");
        grid.add(start, 3, 25);
        
        Button reset = new Button();
        reset.setText("Reset");
        grid.add(reset, 3, 26);
        
        Button pause = new Button();
        pause.setText("Pause");
        grid.add(pause, 3, 27);
        
        Label geschwind = new Label("Geschwindigkeit");
        grid.add(geschwind, 0, 25);
        
        Slider speed = new Slider();
        grid.add(speed, 0, 26);

        Scene scene = new Scene(grid, 400, 500);

        
        primaryStage.setTitle("Kugelbahn");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(LHM.class.getResource("GUI.css").toExternalForm());
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
