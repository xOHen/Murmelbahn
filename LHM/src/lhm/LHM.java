/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lhm;

import javafx.animation.*;
import javafx.application.Application;

import static java.lang.Math.random;
import static java.lang.String.valueOf;
import static javafx.application.Application.launch;
import static jdk.nashorn.internal.objects.NativeMath.round;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.net.ProgressEvent;

import java.awt.*;
import java.awt.image.TileObserver;
import java.lang.reflect.Array;

/**
 *
 * @author $PDEI00-A1GNLU8E0KOC
 */
public class LHM extends Application {

    boolean playstop = true;
    int ebenecounter=0;

    Slider slider = new Slider(0.25,1.5,1);
    Label multilbl = new Label("x1.0");

    Circle ball = new Circle(20);
    Image ballImg = new Image("lhm/Bilder/ball.png");
    ImagePattern imgpattern = new ImagePattern(ballImg);

    final Path pfad = pfadErstellen();
    final PathTransition pfadtrans = generatePathTransition(ball,pfad);

    ImageView[][] felder = new ImageView[5][5];
    ImageView hintergrund = new ImageView("lhm/Bilder/bg-2.png");
    ImageView transblock1 = new ImageView("lhm/Bilder/Ebene1-transparent.png");
    ImageView transblock3 = new ImageView("lhm/Bilder/Ebene3-transparent.png");

    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));

        AnchorPane root = new AnchorPane();

        Scene scene = new Scene(root, 1200,820);

        stage.getIcons().add(new Image("lhm/Bilder/icon.png"));

        ball.setId("ball");
        ball.setFill(imgpattern);

        Group circles = new Group();
        circles.getChildren().add(pfad);
        circles.getChildren().add(ball);

        TilePane tile = new TilePane();
        tile.setAlignment(Pos.CENTER);
        tile.setPrefColumns(5);
        tile.setPrefRows(5);

        int[][] ebenearr= new int[][]{
                {3, 3, 3, 3, 3},
                {3, 2, 2, -1, 4},
                {2, 2, 2, 5, 5},
                {-1, 2, -1, 5, -1},
                {1, 1, -1, 5, 5}
        };

        for (int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                ImageView view = new ImageView("lhm/Bilder/Bahn.png");
                Rectangle2D rec = new Rectangle2D(j*100,i*100,100,100);
                view.setViewport(rec);
                view.setId("view");
                felder[i][j] = view;
                if(ebenearr[i][j]== -1){
                    felder[i][j].setVisible(false);
                }
                tile.getChildren().add(felder[i][j]);
            }
        }


        Button pfeill = new Button();
        pfeill.setId("pfeill");
        pfeill.setMinSize(69,125);
        pfeill.setLayoutX(50);
        pfeill.setLayoutY(450);
        pfeill.setRotate(90);
        pfeill.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(ebenecounter==1){
                    transblock1.setVisible(false);
                    ebenecounter++;
                    Ebenen(ebenecounter,ebenearr);
                }else if(ebenecounter==2){
                    transblock3.setVisible(true);
                    ebenecounter++;
                    Ebenen(ebenecounter,ebenearr);
                }else if(ebenecounter==3){
                    transblock3.setVisible(false);
                    ebenecounter++;
                    Ebenen(ebenecounter,ebenearr);
                }else if(ebenecounter==4){
                    ebenecounter++;
                    Ebenen(ebenecounter,ebenearr);
                }else if(ebenecounter==5){
                    transblock3.setVisible(true);
                    transblock1.setVisible(true);
                    for (int i=0;i<5;i++){
                        for(int j=0;j<5;j++){
                            if(ebenearr[i][j]== -1){
                                felder[i][j].setVisible(false);
                            }else{
                                felder[i][j].setVisible(true);
                            }
                        }
                    }
                    ebenecounter=0;
                }else if(ebenecounter==0){
                    transblock3.setVisible(false);
                    ebenecounter++;
                    Ebenen(ebenecounter,ebenearr);
                }
            }
        });

        Button pfeilr = new Button();
        pfeilr.setId("pfeill");
        pfeilr.setMinSize(69,125);
        pfeilr.setLayoutX(50);
        pfeilr.setLayoutY(360);
        pfeilr.setRotate(-90);
        pfeilr.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(ebenecounter==1){
                    transblock3.setVisible(true);
                    for (int i=0;i<5;i++){
                        for(int j=0;j<5;j++){
                            if(ebenearr[i][j]== -1){
                                felder[i][j].setVisible(false);
                            }else{
                                felder[i][j].setVisible(true);
                            }
                        }
                    }
                    ebenecounter--;
                }else if(ebenecounter==2){
                    transblock1.setVisible(true);
                    ebenecounter--;
                    Ebenen(ebenecounter,ebenearr);
                }else if(ebenecounter==3){
                    transblock3.setVisible(false);
                    ebenecounter--;
                    Ebenen(ebenecounter,ebenearr);
                }else if(ebenecounter==4){
                    transblock3.setVisible(true);
                    ebenecounter--;
                    Ebenen(ebenecounter,ebenearr);
                }else if(ebenecounter==5){
                    ebenecounter--;
                    Ebenen(ebenecounter,ebenearr);
                }else if(ebenecounter==0){
                    transblock3.setVisible(false);
                    transblock1.setVisible(false);
                    ebenecounter=5;
                    Ebenen(ebenecounter,ebenearr);
                }
            }
        });

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

        root.getChildren().addAll(line, hintergrund, tile, ball,pfeill, pfeilr, transblock1, transblock3, geschwindlabel, slider, multilabel,hbox, multilbl);

        root.getStylesheets().add(LHM.class.getResource("GUI.css").toExternalForm());

        stage.setTitle("Kugelbahn");
        stage.setScene(scene);
        stage.show();

    }

    public void Ebenen(int ebenecounter,int[][] ebenearr){
        for (int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(ebenearr[i][j]== ebenecounter){
                    felder[i][j].setVisible(true);
                    System.out.println("1");
                }else{
                    felder[i][j].setVisible(false);
                    System.out.println("0");
                }
            }
        }
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
        final PauseTransition pt = new PauseTransition( Duration.millis( 1000 ) );
        pfad.getElements().add(new MoveTo(0,0));
        pfad.getElements().add(new CubicCurveTo(0, 0, 50, 0, 50, 0));
        pfad.getElements().add(new CubicCurveTo(50, 0, 100, 0, 100, -50));
        pfad.getElements().add(new CubicCurveTo(100, -50, 100, -250, 100, -250));
        pfad.getElements().add(new CubicCurveTo(100, -250, 100, -300, 150, -300));
        pfad.getElements().add(new CubicCurveTo(150, -300, 200, -300, 200, -250));
        pfad.getElements().add(new CubicCurveTo(200, -250, 200, -200, 150, -200));
        pfad.getElements().add(new CubicCurveTo(150, -200, 200, -200, 0, -200));

        pfad.getElements().add(new CubicCurveTo(0, -200, 0, -350, 0, -350));
        pfad.getElements().add(new CubicCurveTo(0, -350, 0, -400, 50, -400));
        pfad.getElements().add(new CubicCurveTo(50, -400, 300, -400, 300, -400));

        pfad.getElements().add(new CubicCurveTo(300, -400, 350, -400, 350, -400));
        pfad.getElements().add(new CubicCurveTo(350, -400, 400, -400, 400, -350));
        pfad.getElements().add(new CubicCurveTo(400, -350, 400, -350, 400, -300));

        pfad.getElements().add(new CubicCurveTo(400, -300, 400, -300, 400, -250));
        pfad.getElements().add(new CubicCurveTo(400, -250, 400, -200, 350, -200));
        pfad.getElements().add(new CubicCurveTo(350, -200, 300, -200, 300, -150));
        pfad.getElements().add(new CubicCurveTo(300, -150, 300, -150, 300, -50));
        pfad.getElements().add(new CubicCurveTo(300, -50, 300, -0, 350, 0));
        pfad.getElements().add(new CubicCurveTo(350, 0, 350, 0, 400, 0));
        pfad.setOpacity(1.0);
        return pfad;
    }

    private PathTransition generatePathTransition(final Shape shape, final Path path)
    {
        final PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(8.0));
        pathTransition.setRate(slider.getValue());
        pathTransition.setPath(path);
        pathTransition.setNode(shape);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        return pathTransition;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
