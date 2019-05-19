/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lhm;

import javafx.animation.*;
import javafx.application.Application;

import static java.lang.Math.random;
import static javafx.application.Application.launch;
import static jdk.nashorn.internal.objects.NativeMath.round;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.awt.*;

/**
 *
 * @author $PDEI00-A1GNLU8E0KOC
 */
public class LHM extends Application {

    boolean playstop = true;
    Slider slider = new Slider(0,1.5,1);
    Label multilbl = new Label("x1.0");
    Circle ball = new Circle(20);
    final Path pfad = pfadErstellen();
    final PathTransition pfadtrans = generatePathTransition(ball,pfad);


    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));

        AnchorPane root = new AnchorPane();

        Scene scene = new Scene(root, 1200,800);

        Group circles = new Group();

        ball.setLayoutX(400);
        ball.setLayoutY(483);
        ball.setId("ball");


        circles.getChildren().add(pfad);
        circles.getChildren().add(ball);

        pfadtrans.play();

        Label bahn = new Label();
        bahn.setMinSize(1200,570);
        bahn.setId("bahn1");

        /*for (Node circle: circles.getChildren()) {
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(circle.translateXProperty(), 0),
                            new KeyValue(circle.translateYProperty(), 0),
                            new KeyValue(circle.translateZProperty(),0)
                    ),
                    new KeyFrame(new Duration(10000),
                            new KeyValue(circle.translateXProperty(), 800),
                            new KeyValue(circle.translateYProperty(), 0),
                            new KeyValue(circle.translateZProperty(),100)
                    )
            );
            timeline.setCycleCount(Animation.INDEFINITE);
        }*/

        Line line = new Line(0,570,1200,570);
        Line line2 = new Line(350,525,450,525);


        Label geschwindlabel = new Label("xx km/h");
        geschwindlabel.setId("multilbl");
        geschwindlabel.setLayoutX(1050);
        geschwindlabel.setLayoutY(480);

        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);
        slider.setMajorTickUnit(0.25f);
        slider.setBlockIncrement(0.05f);
        slider.setMinorTickCount(4);
        slider.setLayoutX(50);
        slider.setLayoutY(620);
        slider.setPrefWidth(1000);


        Label multilabel = new Label("Multiplikator:");
        multilabel.setLayoutX(57);
        multilabel.setLayoutY(585);

        multilbl.setId("multilbl");
        multilbl.setLayoutX(1070);
        multilbl.setLayoutY(630);

        sliderSpeed();

        HBox hbox = new HBox();
        hbox.setLayoutY(720);
        hbox.setLayoutX(480);
        hbox.setSpacing(100);

        Button playbreakbtn = new Button();
        playbreakbtn.setId("playbreakbtn");
        playbreakbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(playstop==true){
                    System.out.println(playstop);
                    playbreakbtn.setId("playbreakbtn1");
                    pfadtrans.setRate(slider.getValue());
                    pfadtrans.play();
                    sliderSpeed();
                    playstop=false;
                }else{
                    System.out.println(playstop);
                    playstop=true;
                    playbreakbtn.setId("playbreakbtn");
                    pfadtrans.pause();
                }
            }
        });

        Button stopbtn = new Button();
        stopbtn.setId("stopbtn");
        stopbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                pfadtrans.stop();
                playstop=true;
                playbreakbtn.setId("playbreakbtn");
            }
        });

        hbox.getChildren().addAll(playbreakbtn, stopbtn);

        root.getChildren().addAll(line, bahn, geschwindlabel, slider, multilabel,hbox, multilbl, ball, line2);

        root.getStylesheets().add(LHM.class.getResource("GUI.css").toExternalForm());

        stage.setTitle("Kugelbahn");
        stage.setScene(scene);
        stage.show();

    }

    public void sliderSpeed(){
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                String number = String.format("%.2f", slider.getValue());
                //System.out.println(number);
                multilbl.setText("x"+number);
                if(playstop==false){
                    pfadtrans.setRate(slider.getValue());
                    pfadtrans.play();
                }
            }
        });
    }

    private Path pfadErstellen(){
        Path pfad = new Path();
        pfad.getElements().add(new MoveTo(0,0));
        pfad.getElements().add(new CubicCurveTo(0, 0, 50, 0, 50, 0));
        pfad.getElements().add(new CubicCurveTo(50, 0, 100, 0, 100, -50));
        pfad.setOpacity(1.0);
        return pfad;
    }

    private PathTransition generatePathTransition(final Shape shape, final Path path)
    {
        final PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(8.0));
        pathTransition.setDelay(Duration.seconds(2.0));
        pathTransition.setPath(path);
        pathTransition.setNode(shape);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        return pathTransition;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
