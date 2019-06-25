/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lhm;

import javafx.application.Application;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author $PDEI00-A1GNLU8E0KOC
 */
public class LHM extends Application {
    boolean playstop = true;
    int ebenecounter=0;

    Slider slider = new Slider(0.25,1.5,1);

    Sphere ball = new Sphere(20);

    Button pfeill,pfeilr;

    ImageView[][] felder = new ImageView[5][5];
    ImageView hintergrund = new ImageView("lhm/Bilder/bg-2.png");
    ImageView transblock1 = new ImageView("lhm/Bilder/Ebene1-transparent.png");
    ImageView transblock3 = new ImageView("lhm/Bilder/Ebene3-transparent.png");

    Label multilbl = new Label("x1.0");
    //Label geschwindlabel = new Label("xx km/h");
    Label multilabel = new Label("Multiplikator:");

    HBox hbox = new HBox();

    Pfadberechnung pb=new Pfadberechnung(ball,slider);
    Felder f=new Felder(felder);
    Berechnung b = new Berechnung(slider,ball);

    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));

        AnchorPane root = new AnchorPane();

        Scene scene = new Scene(root, 1200,820);

        stage.getIcons().add(new Image("lhm/Bilder/icon.png"));

        f.felderBauen();

        /*Group circles = new Group();
        circles.getChildren().add(pb.pfad);
        circles.getChildren().add(ball);*/

        Line line = new Line(0,570,1200,570);

        ball();

        labels();

        slider();

        buttonEbene();

        buttonsPlayStop();

        root.getChildren().addAll(line, hintergrund, f.tile, ball,pfeill, pfeilr, transblock1, transblock3, multilabel, slider,hbox, multilbl);

        root.getStylesheets().add(LHM.class.getResource("GUI.css").toExternalForm());

        stage.setTitle("Kugelbahn");
        stage.setScene(scene);
        stage.show();
    }

    public void ball(){
        ball.setId("ball");
        ball.setTranslateX(400);
        ball.setTranslateY(483);
        ball.setTranslateZ(500);

        PhongMaterial phongMaterial= new PhongMaterial();
        phongMaterial.setDiffuseColor(Color.rgb(197,17,68));
        phongMaterial.setSpecularColor(Color.rgb(89,12,38));

        ball.setMaterial(phongMaterial);
    }

    public void buttonEbene(){
        pfeill = new Button();
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
                    Ebenen(ebenecounter,f.ebenearr);

                }else if(ebenecounter==2){

                    transblock3.setVisible(true);
                    ebenecounter++;
                    Ebenen(ebenecounter,f.ebenearr);

                }else if(ebenecounter==3){

                    transblock3.setVisible(false);
                    ebenecounter++;
                    Ebenen(ebenecounter,f.ebenearr);

                }else if(ebenecounter==4){

                    ebenecounter++;
                    Ebenen(ebenecounter,f.ebenearr);

                }else if(ebenecounter==5){

                    transblock3.setVisible(true);
                    transblock1.setVisible(true);
                    ebenecounter=0;
                    Ebenen(ebenecounter,f.ebenearr);

                }else if(ebenecounter==0){

                    transblock3.setVisible(false);
                    ebenecounter++;
                    Ebenen(ebenecounter,f.ebenearr);

                }
            }
        });

        pfeilr = new Button();
        pfeilr.setId("pfeill");
        pfeilr.setMinSize(69,125);
        pfeilr.setLayoutX(50);
        pfeilr.setLayoutY(360);
        pfeilr.setRotate(-90);

        pfeilr.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(ebenecounter==1){

                    transblock3.setVisible(true);
                    ebenecounter--;
                    Ebenen(ebenecounter,f.ebenearr);

                }else if(ebenecounter==2){

                    transblock1.setVisible(true);
                    ebenecounter--;
                    Ebenen(ebenecounter,f.ebenearr);

                }else if(ebenecounter==3){

                    transblock3.setVisible(false);
                    ebenecounter--;
                    Ebenen(ebenecounter,f.ebenearr);

                }else if(ebenecounter==4){

                    transblock3.setVisible(true);
                    ebenecounter--;
                    Ebenen(ebenecounter,f.ebenearr);

                }else if(ebenecounter==5){

                    ebenecounter--;
                    Ebenen(ebenecounter,f.ebenearr);

                }else if(ebenecounter==0){

                    transblock3.setVisible(false);
                    transblock1.setVisible(false);
                    ebenecounter=5;
                    Ebenen(ebenecounter,f.ebenearr);

                }
            }
        });
    }

    public void labels(){
        /*geschwindlabel.setId("multilbl");
        geschwindlabel.setLayoutX(1050);
        geschwindlabel.setLayoutY(480);*/

        multilabel.setLayoutX(57);
        multilabel.setLayoutY(585);

        multilbl.setId("multilbl");
        multilbl.setLayoutX(1070);
        multilbl.setLayoutY(630);
    }

    public void buttonsPlayStop(){
        hbox.setLayoutY(720);
        hbox.setLayoutX(480);
        hbox.setSpacing(100);

        Button playbreakbtn = new Button();
        playbreakbtn.setId("playbreakbtn");

        playbreakbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(playstop==true){
                    playbreakbtn.setId("playbreakbtn1");

                    //pb.pfadtrans.setRate(slider.getValue());
                    //pfadtrans.play();

                    b.animation();
                    b.timeline.setRate(slider.getValue());
                    sliderSpeed();

                    playstop=false;
                }else{
                    playbreakbtn.setId("playbreakbtn");

                    //pb.pfadtrans.pause();

                    b.timeline.pause();

                    playstop=true;
                }
            }
        });
        Button stopbtn = new Button();
        stopbtn.setId("stopbtn");
        stopbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                playbreakbtn.setId("playbreakbtn");

                //pb.pfadtrans.stop();

                b.timeline.jumpTo(Duration.millis(0));
                b.timeline.stop();

                playstop=true;
            }
        });

        hbox.getChildren().addAll(playbreakbtn, stopbtn);
    }

    public void Ebenen(int ebenecounter,int[][] ebenearr){
        for (int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(ebenearr[i][j]== ebenecounter){

                    felder[i][j].setVisible(true);

                }else if(ebenecounter==0){
                    if(f.ebenearr[i][j]== -1){

                        felder[i][j].setVisible(false);

                    }else{

                        felder[i][j].setVisible(true);

                    }
                }else{

                    felder[i][j].setVisible(false);

                }
            }
        }
    }

    public void slider(){
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);
        slider.setMajorTickUnit(0.25f);
        slider.setBlockIncrement(0.05f);
        slider.setMinorTickCount(4);
        slider.setLayoutX(50);
        slider.setLayoutY(620);
        slider.setPrefWidth(1000);

        sliderSpeed();
    }

    public void sliderSpeed(){
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                String number = String.format("%.2f", slider.getValue());
                multilbl.setText("x"+number);

                if(playstop==false){
                    //pb.pfadtrans.setRate(slider.getValue());
                    //pb.pfadtrans.play();

                    b.timeline.setRate(slider.getValue());
                    b.timeline.play();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
