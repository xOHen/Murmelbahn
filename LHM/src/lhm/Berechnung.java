package lhm;

import javafx.animation.*;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import lhm.LHM;

import java.util.Timer;
import java.util.TimerTask;

public class Berechnung extends LHM {
    Timeline timeline;
    TranslateTransition tt;
    Circle cir;


    public void animation(){
        /*timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(t*100),
                        new KeyValue(ball.translateXProperty(), ball.getLayoutX()+sx)));*/

        System.out.println(ball.getTranslateX());
    }


}