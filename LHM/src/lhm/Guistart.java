package lhm;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class Guistart extends Application {

    @Override
    public void start(Stage primaryStage) {

        Label ueberschrift = new Label();
        ueberschrift.setId("ueberschrift");
        ueberschrift.setMinSize(553, 176);

        Button start = new Button("START");
        start.setId("startbutton");
        Button info = new Button("INFO");
        info.setId("infobutton");
        Button end = new Button("EXIT");
        end.setId("endbutton");

        BorderPane root = new BorderPane();
        root.setId("root");
        root.setPadding(new Insets(100,100,100,15));
        VBox vbox = new VBox(10, start, info, end);
        vbox.setPadding(new Insets(0, 0, 0, 225));
        vbox.setSpacing(30.0);
        root.setTop(ueberschrift);
        root.setCenter(vbox);



        start.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //System.out.println("Hello World!");
            }
        });

        info.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Label secondLabel = new Label("Eine Murmel die in einer Bahn rollt");

                StackPane secondaryLayout = new StackPane();
                secondaryLayout.getChildren().add(secondLabel);

                Scene secondScene = new Scene(secondaryLayout, 230, 100);

                Stage newWindow = new Stage();
                newWindow.setTitle("Info");
                newWindow.setScene(secondScene);

                newWindow.setX(primaryStage.getX() + 200);
                newWindow.setY(primaryStage.getY() + 100);

                newWindow.getIcons().add(new Image("lhm/Bilder/icon.png"));

                newWindow.show();
            }
        });

        end.setOnAction(e -> Platform.exit());




        Scene scene = new Scene(root, 600, 570);

        primaryStage.getIcons().add(new Image("lhm/Bilder/icon.png"));

        primaryStage.setTitle("LHM");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        scene.getStylesheets().add(Guistart.class.getResource("GUI.css").toExternalForm());
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}