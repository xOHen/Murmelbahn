package lhm;

import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.scene.control.Slider;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

import java.util.ArrayList;

public class Berechnung{
    float t=0;
    float sx,sy,sz;
    float sx0=0,sy0=0,sz0=0;
    float vx,vy,vz;
    float vx0,vy0,vz0;
    float ax,ay,az;
    float v;
    
    double[][] points ={{500,233,400},{550,183,400}};

    TranslateTransition translate;
    //KeyFrame[] kf = new KeyFrame[];
    ArrayList<KeyFrame> kf = new ArrayList<KeyFrame>();

    Sphere ball;
    Slider slider;

    public Berechnung(Slider slider, Sphere ball){
        this.ball=ball;
        this.slider=slider;
    }

    public void Punkte(){
        for(int i=0;i<points.length-1;i++){
            if(points[i][0]!=points[i+1][0]&&points[i][1]!=points[i+1][1]){
                rechtsKurve(i);
            }else if(points[i][0]!=points[i+1][0]&&points[i][2]!=points[i+1][2]){

            }else if(points[i][1]!=points[i+1][1]&&points[i][2]!=points[i+1][2]){

            }else if(points[i][0]!=points[i+1][0]){
                if(points[i][0]>points[i+1][0]){
                    geradeXL(i,10,-0.1f);
                }else if (points[i][0]<points[i+1][0]){
                    geradeXR(i,10,-0.1f);
                }
            }else if(points[i][1]!=points[i+1][1]){
                if(points[i][1]>points[i+1][1]){
                    geradeYH(i,10,-0.1f);
                }else if (points[i][1]<points[i+1][1]){
                    geradeYR(i,10,-0.1f);
                }
            }else if(points[i][2]!=points[i+1][2]) {
                geradeZ(i, 10, 9.81f);
            }
            System.out.println(v);
        }
    }


    public void rechtsKurve(int i){
        Point2D p = new Point2D(points[i+1][0],points[i][1]);
        System.out.println(p);
        double[] v1 ={points[i+1][0]-p.getX(),points[i+1][1]-p.getY()};
        double[] v2 ={points[i][0]-p.getX(),points[i][1]-p.getY()};
        System.out.println(v1[0]+" "+v1[1]);
        System.out.println(v2[0]+" "+v2[1]);

        winkelBerechnen(v1,v2);

        //geradeKurve(i);
    }

    public float geradeKurve(int i, float vz0, float az){
        t++;
        sz0=0;
        sz=sz0+(vz0*t)+((0.5f)*az*(t*t));
        System.out.println(ball.getTranslateZ()-sz+"z");
        if((ball.getTranslateZ()-sz)>=points[i+1][2]){
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateZProperty(), ball.getTranslateZ()-sz)));
            geradeZ(i,vz0,az);
        }else{
            v=vz0+az*t;
            return v;
        }
        return vz0;
    }

    public static double winkelBerechnen(double[] v1, double[] v2)
    {
        double skalarprodukt = 0;

        for (int i = 0; i < v1.length; i++) {
            skalarprodukt = skalarprodukt + v1[i] * v2[i];
        }
        System.out.println(skalarprodukt);
        double vl1=Math.sqrt(v1[0]*v1[0]+v1[1]*v1[1]);
        double vl2=Math.sqrt(v2[0]*v2[0]+v2[1]*v2[1]);
        System.out.println(vl1+" "+vl2);
        double winkel = Math.acos(skalarprodukt/(vl1*vl2))*180/Math.PI;
        System.out.println("Winkel:"+winkel);
        return skalarprodukt;
    }

    //Berechnung für Y-Achse

    public float geradeYR(int i,float vy0, float ay){
        berechnungYGerade(vy0,ay);
        System.out.println(ball.getTranslateY()+sy+"y");
        if((ball.getTranslateY()+sy)<=points[i+1][1]){
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), ball.getTranslateY()+sy)));
            geradeYR(i,vy0,ay);
        }else{
            v=vy0+ay*t;
            return v;
        }
        return vy0;
    }

    public float geradeYH(int i,float vy0, float ay){
        berechnungYGerade(vy0,ay);
        System.out.println(ball.getTranslateY()-sy+"y");
        if((ball.getTranslateY()-sy)>=points[i+1][1]){
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), ball.getTranslateY()-sy)));
            geradeYH(i,vy0,ay);
        }else{
            v=vy0+ay*t;
            return v;
        }
        return vy0;
    }

    public void berechnungYGerade(float vy0, float ay){
        t++;
        sy0=0;
        sy=sy0+(vy0*t)+((0.5f)*ay*(t*t));
    }


    //Berechnung der X-Achse

    public float geradeXL(int i,float vx0, float ax){
        berechnungXGerade(vx0,ax);
        System.out.println(ball.getTranslateX()-sx+"x");
        if((ball.getTranslateX()-sx)>=points[i+1][0]){
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateXProperty(), ball.getTranslateX()-sx)));
            geradeXL(i,vx0,ax);
        }else{
            v=vx0+ax*t;
            return v;
        }
        return vx0;
    }

    public float geradeXR(int i,float vx0, float ax){
        berechnungXGerade(vx0,ax);
        System.out.println(ball.getTranslateX()+sx+"x");
        if((ball.getTranslateX()+sx)<=points[i+1][0]){
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateXProperty(), ball.getTranslateX()+sx)));
            geradeXR(i,vx0,ax);
        }else{
            v=vx0+ax*t;
            return v;
        }
        return vx0;
    }

    public void berechnungXGerade(float vx0, float ax){
        t++;
        sx0=0;
        sx=sx0+(vx0*t)+((0.5f)*ax*(t*t));
    }


    //Berechnung der Z-Achse

    public float geradeZ(int i, float vz0, float az){
        t++;
        sz0=0;
        sz=sz0+(vz0*t)+((0.5f)*az*(t*t));
        System.out.println(ball.getTranslateZ()-sz+"z");
        if((ball.getTranslateZ()-sz)>=points[i+1][2]){
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateZProperty(), ball.getTranslateZ()-sz)));
            geradeZ(i,vz0,az);
        }else{
            v=vz0+az*t;
            return v;
        }
        return vz0;
    }



    public void animation(){
        /*translate = new TranslateTransition();
        translate.setDuration(Duration.millis(1000));
        translate.setCycleCount(1);
        translate.setAutoReverse(true);
        translate.setNode(ball);*/
        Punkte();
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        for(int i=0;i<kf.size();i++){
            timeline.getKeyFrames().add(kf.get(i));
        }
        timeline.play();
    }


}