package lhm;

import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

import java.util.ArrayList;

public class Berechnung{
    float s,v,a;
    float s0=0;
    float v0=0;
    float afall;

    float zeit=0;
    float t=0;

    static double winkel;

    float l=0;

    final Timeline timeline = new Timeline();

    float k=0,j=0;

    double[][] points ={{400,483,500},{450,483,450},{500,433,450},
            {500,433,400},{500,233,400},{550,183,400},{600,233,400},
            {550,283,400},{400,283,400}, {400,233,350},{400,233,300},
            {400,133,300},{450,83,300},{700,83,300}, {750,83,250},
            {800,133,250},{800,133,200},{800,183,200},{800,233,150},
            {800,233,100},{750,283,100},{700,333,100},{700,433,100},
            {750,483,100},{800,483,100},{750,483,0}};

    ArrayList<Float> g = new ArrayList<>();
    ArrayList<Float> g2 = new ArrayList<>();
    ArrayList<KeyFrame> kf = new ArrayList<KeyFrame>();

    Sphere ball;
    Slider slider;
    Label geschwindigkeitlabel;

    public Berechnung(Slider slider, Sphere ball, Label geschwindigkeitlabel){
        this.ball=ball;
        this.slider=slider;
        this.geschwindigkeitlabel=geschwindigkeitlabel;
    }

    public void Punkte(){
        for(int i=0;i<points.length-1;i++){
            if(points[i][0]!=points[i+1][0]&&points[i][1]!=points[i+1][1]){

                rechtsKurve(i,v,-0.5f);

            }else if(points[i][0]!=points[i+1][0]&&points[i][2]!=points[i+1][2]){

                fallKurveX(i,v,-0.5f);

            }else if(points[i][1]!=points[i+1][1]&&points[i][2]!=points[i+1][2]){

                fallKurveY(i,v,-0.5f);

            }else if(points[i][0]!=points[i+1][0]){

                if(points[i][0]>points[i+1][0]){
                    geradeXL(i,v,-0.5f);
                }else if (points[i][0]<points[i+1][0]){
                    geradeXR(i,v,-0.5f);
                }

            }else if(points[i][1]!=points[i+1][1]){

                if(points[i][1]>points[i+1][1]){
                    geradeYH(i,v,-0.5f);
                }else if (points[i][1]<points[i+1][1]){
                    geradeYR(i,v,-0.5f);
                }

            }else if(points[i][2]!=points[i+1][2]) {

                geradeZ(i, v, 9.81f);

            }

            ball.setTranslateX(points[i+1][0]);
            ball.setTranslateY(points[i+1][1]);
            ball.setTranslateZ(points[i+1][2]);

            zeit=0;
            s=0;
        }
    }



    //Fallende Kurve in y-Richtung

    public void fallKurveY(int i,float v0,float a){
        Point3D p;
        double[] v1=new double[2];
        double[] v2=new double[2];

        p = new Point3D(points[i+1][0],points[i+1][1],points[i][2]);

        v1[0]=points[i+1][1]-p.getY()+k;
        v1[1]=points[i+1][2]-p.getZ()-j;
        v2[0]=points[i][1]-p.getY();
        v2[1]=points[i][2]-p.getZ();

        winkelBerechnen(v1,v2);

        if(points[i][1]>points[i+1][1]){

            yKurvefallRunter(i,v0,a);

        }else{

            yKurvefallHoch(i,v0,a);

        }

    }

    public float yKurvefallHoch(int i, float v0, float a){
        kurveBerechnen(i,v0,a);

        if((ball.getTranslateY()-j)<=points[i+1][1]){

            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateZProperty(), ball.getTranslateZ()-k+v0/2)));
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), ball.getTranslateY()-j)));
            fallKurveY(i,v0,a);

        }else{

            kf.add(new KeyFrame(Duration.millis(((t*50)-45)/slider.getValue()), new KeyValue (ball.translateZProperty(), points[i+1][2])));
            kf.add(new KeyFrame(Duration.millis(((t*50)-45)/slider.getValue()), new KeyValue (ball.translateYProperty(), points[i+1][1])));

            winkel=90;
            j=0;
            k=0;
            v=v0+afall/2*zeit;

            return v;
        }

        return v0;
    }

    public float yKurvefallRunter(int i, float v0, float a){
        kurveBerechnen(i,v0,a);

        if((ball.getTranslateY()-j)>=points[i+1][1]){

            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateZProperty(), ball.getTranslateZ()-k+v0/2)));
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), ball.getTranslateY()-j)));
            fallKurveY(i,v0,a);

        }else{

            kf.add(new KeyFrame(Duration.millis(((t*50)-45)/slider.getValue()), new KeyValue (ball.translateZProperty(), points[i+1][2])));
            kf.add(new KeyFrame(Duration.millis(((t*50)-45)/slider.getValue()), new KeyValue (ball.translateYProperty(), points[i+1][1])));
            
            winkel=90;
            j=0;
            k=0;
            v=v0+afall/2*zeit;

            return v;
        }

        return v0;
    }



    //Fallende Kurve in x-Richtung

    public void fallKurveX(int i,float v0,float a){
        Point3D p;
        double[] v1=new double[2];
        double[] v2=new double[2];
            p = new Point3D(points[i+1][0],points[i+1][1],points[i][2]);
            v1[0]=points[i+1][0]-p.getX()-k;
            v1[1]=points[i+1][2]-p.getZ()-j;
            v2[0]=points[i][0]-p.getX();
            v2[1]=points[i][2]-p.getZ();
            winkelBerechnen(v1,v2);

            xKurvefall(i,v0,a);

    }

    public float xKurvefall(int i, float v0, float a){
        kurveBerechnen(i,v0,a);

        if((ball.getTranslateX()+j)<=points[i+1][0]){
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateZProperty(), ball.getTranslateZ()-k+a/2)));
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateXProperty(), ball.getTranslateX()+j)));
            fallKurveX(i,v0,a);
        }else{
            kf.add(new KeyFrame(Duration.millis(((t*50)-45)/slider.getValue()), new KeyValue (ball.translateZProperty(), points[i+1][2])));
            kf.add(new KeyFrame(Duration.millis(((t*50)-45)/slider.getValue()), new KeyValue (ball.translateXProperty(), points[i+1][0])));
            winkel=90;
            j=0;
            k=0;
            if(l==1){
                v=v0+afall/2*zeit;
            }else{
                v=v0+afall*zeit;
            }
            l++;
            return v;
        }
        return v0;
    }




    //Kurven nach links & rechts

    public void rechtsKurve(int i,float v0,float a){
        Point2D p;
        double[] v1=new double[2];
        double[] v2=new double[2];
        if(points[i][0]<points[i+1][0]&&points[i][1]>points[i+1][1]){
            p = new Point2D(points[i+1][0],points[i][1]);

            v1[0]=points[i+1][0]-p.getX()-k;
            v1[1]=points[i+1][1]-p.getY()+j;
            v2[0]=points[i][0]-p.getX();
            v2[1]=points[i][1]-p.getY();

            winkelBerechnen(v1,v2);

            rKurveR(i,v0,a);
        }else if(points[i][0]<points[i+1][0]&&points[i][1]<points[i+1][1]){
            p = new Point2D(points[i][0],points[i+1][1]);

            v1[0]=points[i+1][0]-p.getX()-j;
            v1[1]=points[i+1][1]-p.getY()-k;
            v2[0]=points[i][0]-p.getX();
            v2[1]=points[i][1]-p.getY();

            winkelBerechnen(v1,v2);

            rKurveA(i,v0,a);
        }else if(points[i][0]>points[i+1][0]&&points[i][1]<points[i+1][1]){
            p = new Point2D(points[i][0],points[i+1][1]);

            v1[0]=points[i+1][0]-p.getX()+j;
            v1[1]=points[i+1][1]-p.getY()-k;
            v2[0]=points[i][0]-p.getX();
            v2[1]=points[i][1]-p.getY();

            winkelBerechnen(v1,v2);

            rKurveZ(i,v0,a);
        }
    }

    public float rKurveR(int i, float v0, float a){
        kurveBerechnen(i,v0,a);

        if((ball.getTranslateX()+j)<=points[i+1][0]){
            int rest= (int) (points[i][0]%100);
            if(rest==0){
                kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateXProperty(), ball.getTranslateX()+j)));
                kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), ball.getTranslateY()-k+v0/2)));
            }else{
                kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateXProperty(), ball.getTranslateX()+k-v0/2)));
                kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), ball.getTranslateY()-j)));
            }
            rechtsKurve(i,v0,a);
        }else{
            kf.add(new KeyFrame(Duration.millis(((t*50)-45)/slider.getValue()), new KeyValue (ball.translateXProperty(), points[i+1][0])));
            kf.add(new KeyFrame(Duration.millis(((t*50)-45)/slider.getValue()), new KeyValue (ball.translateYProperty(), points[i+1][1])));
            winkel=90;
            j=0;
            k=0;
            v=v0+a*zeit;
            return v;
        }
        return v0;
    }

    public float rKurveA(int i, float v0, float a){
        kurveBerechnen(i,v0,a);

        if((ball.getTranslateY()+j)<=points[i+1][1]){
            int rest= (int) (points[i][0]%100);
            if(rest!=0){
                kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateXProperty(), ball.getTranslateX()+k-(v0/2))));
                kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), ball.getTranslateY()+j)));
            }else{
                kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateXProperty(), ball.getTranslateX()+j)));
                kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), ball.getTranslateY()+k-v0/2)));
            }

            rechtsKurve(i,v0,a);
        }else{
            kf.add(new KeyFrame(Duration.millis(((t*50)-45)/slider.getValue()), new KeyValue (ball.translateXProperty(), points[i+1][0])));
            kf.add(new KeyFrame(Duration.millis(((t*50)-45)/slider.getValue()), new KeyValue (ball.translateYProperty(), points[i+1][1])));
            winkel=90;
            j=0;
            k=0;
            v=v0+a*zeit;
            return v;
        }
        return v0;
    }

    public float rKurveZ(int i, float v0, float a){
        kurveBerechnen(i,v0,a);

        if((ball.getTranslateY()+j)<=points[i+1][1]){
            int rest= (int) (points[i][0]%100);
            if(rest==0){
                kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateXProperty(), ball.getTranslateX()-j)));
                kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), ball.getTranslateY()+k-(v0/2))));
            }else{
                kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateXProperty(), ball.getTranslateX()-k+v0/2)));
                kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), ball.getTranslateY()+j)));
            }
            rechtsKurve(i,v0,a);
        }else{
            kf.add(new KeyFrame(Duration.millis(((t*50)-45)/slider.getValue()), new KeyValue (ball.translateXProperty(), points[i+1][0])));
            kf.add(new KeyFrame(Duration.millis(((t*50)-45)/slider.getValue()), new KeyValue (ball.translateYProperty(), points[i+1][1])));
            winkel=90;
            j=0;
            k=0;
            v=v0+a*zeit;
            return v;
        }
        return v0;
    }


    //Kurvenstückberechnung

    public void kurveBerechnen(int i, float v0, float a){
        float m=s;

        double faktorx =1-(winkel/0.9)/100;
        double faktory =(winkel/0.9)/100;

        if(points[i][2]==points[i+1][2]){
            berechnungGerade(v0,a);
        }else{
            fallkurveBerechnen(v0,a,faktorx,faktory);
        }

        g.add(s-m);
        g2.add(s-m);

        for(int n=0;n<g.size();n++){
            k= (float) (k+g.get(n)*faktory);
            j= (float) (j+g.get(n)*faktorx);

            g.removeAll(g);

            g2.removeAll(g2);
        }
    }




    //Winkelberechnung

    public static double winkelBerechnen(double[] v1, double[] v2) {
        double skalarprodukt = 0;

        for (int i = 0; i < v1.length; i++) {
            skalarprodukt = skalarprodukt + v1[i] * v2[i];
        }

        double vl1=Math.sqrt(v1[0]*v1[0]+v1[1]*v1[1]);
        double vl2=Math.sqrt(v2[0]*v2[0]+v2[1]*v2[1]);

        winkel = Math.acos(skalarprodukt/(vl1*vl2))*180/Math.PI;

        return winkel;
    }



    //Berechnung für Y-Achse

    public float geradeYR(int i,float v0, float a){
        berechnungGerade(v0,a);

        if((ball.getTranslateY()+s)<=points[i+1][1]){
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), ball.getTranslateY()+s)));
            geradeYR(i,v0,a);
        }else{
            kf.add(new KeyFrame(Duration.millis(((t*50)-45)/slider.getValue()), new KeyValue (ball.translateYProperty(), points[i+1][1])));
            v=v0+a*zeit;
            return v;
        }
        return v0;
    }

    public float geradeYH(int i,float v0, float a){
        berechnungGerade(v0,a);

        if((ball.getTranslateY()-s)>=points[i+1][1]){
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), ball.getTranslateY()-s)));
            geradeYH(i,v0,a);
        }else{
            kf.add(new KeyFrame(Duration.millis(((t*50)-45)/slider.getValue()), new KeyValue (ball.translateYProperty(), points[i+1][1])));
            v=v0+a*zeit;
            return v;
        }
        return v0;
    }




    //Berechnung der X-Achse

    public float geradeXL(int i,float v0, float a){
        berechnungGerade(v0,a);

        if((ball.getTranslateX()-s)>=points[i+1][0]){
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateXProperty(), ball.getTranslateX()-s)));
            geradeXL(i,v0,a);
        }else{
            kf.add(new KeyFrame(Duration.millis(((t*50)-45)/slider.getValue()), new KeyValue (ball.translateXProperty(), points[i+1][0])));
            v=v0+a*zeit;
            return v;
        }
        return v0;
    }

    public float geradeXR(int i,float v0, float a){
        berechnungGerade(v0,a);

        if((ball.getTranslateX()+s)<=points[i+1][0]){
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateXProperty(), ball.getTranslateX()+s)));
            geradeXR(i,v0,a);
        }else{
            kf.add(new KeyFrame(Duration.millis(((t*50)-45)/slider.getValue()), new KeyValue (ball.translateXProperty(), points[i+1][0])));
            v=v0+a*zeit;
            return v;
        }
        return v0;
    }




    //Berechnung der Z-Achse

    public float geradeZ(int i, float v0, float a){
        berechnungGerade(v0,a);

        if((ball.getTranslateZ()-s)>=points[i+1][2]){
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateZProperty(), ball.getTranslateZ()-s)));
            geradeZ(i,v0,a);
        }else{
            kf.add(new KeyFrame(Duration.millis(((t*50)-45)/slider.getValue()), new KeyValue (ball.translateZProperty(), points[i+1][2])));
            v=(v0+a*zeit)/2;
            return v;
        }
        return v0;
    }




    //Geradensegmentberechnungen

    public void fallkurveBerechnen(float v0, float a,double faktorx, double faktory){
        float ag=9.81f;
        afall= (float) (ag*faktory+a*faktorx);
        t++;
        zeit++;
        s0=0;
        s=s0+(v0*zeit)+((0.5f)*afall*(zeit*zeit));
    }

    public void berechnungGerade(float v0, float a){
        zeit++;
        t++;
        s0=0;
        s=s0+(v0*zeit)+((0.5f)*a*(zeit*zeit));
    }





    //Animation

    public void animation(){
        Punkte();

        ball.setTranslateX(points[0][0]);
        ball.setTranslateY(points[0][1]);
        ball.setTranslateZ(points[0][2]);
        timeline.setCycleCount(Animation.INDEFINITE);

        for(int i=0;i<kf.size();i++){
            timeline.getKeyFrames().add(kf.get(i));
            geschwindigkeitlabel.setText(v0+a*zeit+"px/s");
        }

        timeline.play();
    }


}