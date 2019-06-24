package lhm;

import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.control.Slider;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

import java.util.ArrayList;

public class Berechnung{
    float t=0;
    float s;
    float s0=0;
    float v;
    float v0=0;
    float a;
    static double winkel;

    double[][] points ={{400,483,500},{450,483,450},{500,433,450},
            {500,433,400},{500,233,400},{550,183,400},{600,233,400},
            {550,283,400},{400,283,400}, {400,233,350},{400,233,300},
            {400,133,300},{450,83,300},{700,83,300}, {750,83,250},
            {800,133,250},{800,133,200},{800,183,200},{800,233,150},
            {800,233,100},{750,283,100},{700,333,100},{700,433,100},
            {750,483,100},{800,483,100},{750,483,0}};

    ArrayList<Float> g = new ArrayList<>();
    ArrayList<Float> g2 = new ArrayList<>();
    float k=0,j=0;

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
                rechtsKurve(i,15,-0.1f);
            }else if(points[i][0]!=points[i+1][0]&&points[i][2]!=points[i+1][2]){

            }else if(points[i][1]!=points[i+1][1]&&points[i][2]!=points[i+1][2]){
                //fallKurveY(i,10,-0.1f);
            }else if(points[i][0]!=points[i+1][0]){
                if(points[i][0]>points[i+1][0]){
                    geradeXL(i,15,-0.1f);
                }else if (points[i][0]<points[i+1][0]){
                    geradeXR(i,15,-0.1f);
                }
            }else if(points[i][1]!=points[i+1][1]){
                if(points[i][1]>points[i+1][1]){
                    geradeYH(i,15,-0.1f);
                    System.out.println("Achtung");
                }else if (points[i][1]<points[i+1][1]){
                    geradeYR(i,15,-0.1f);
                    System.out.println("Och");
                }
            }else if(points[i][2]!=points[i+1][2]) {
                geradeZ(i, 15, 9.81f);
            }
            System.out.println(v);
            System.out.println("Yeaj");
            ball.setTranslateX(points[i+1][0]);
            ball.setTranslateY(points[i+1][1]);
            ball.setTranslateZ(points[i+1][2]);
        }
    }

    /*public void fallKurveY(int i,float v0,float a){
        Point3D p;
        double[] v1=new double[2];
        double[] v2=new double[2];
            p = new Point3D(points[i+1][0],points[i+1][1],points[i][2]);
            v1[0]=points[i+1][1]-p.getY()+k;
            v1[1]=points[i+1][2]-p.getZ()-j;
            v2[0]=points[i][1]-p.getY();
            v2[1]=points[i][2]-p.getZ();
            winkelBerechnen(v1,v2);

            yKurvefall(i,v0,a);

    }

    public float yKurvefall(int i, float v0, float a){
        fallkurveBerechnen(i,v0,a);

        if((ball.getTranslateY()-k)>=points[i+1][1]){
            kf.add(new KeyFrame(Duration.millis((t*500)/slider.getValue()), new KeyValue (ball.translateXProperty(), ball.getTranslateZ()+j)));
            kf.add(new KeyFrame(Duration.millis((t*500)/slider.getValue()), new KeyValue (ball.translateYProperty(), ball.getTranslateY()-k+v0/2)));
            fallKurveY(i,v0,a);
        }else{
            kf.add(new KeyFrame(Duration.millis((t*500)/slider.getValue()), new KeyValue (ball.translateXProperty(), points[i+1][0])));
            kf.add(new KeyFrame(Duration.millis((t*500)/slider.getValue()), new KeyValue (ball.translateYProperty(), points[i+1][1])));
            winkel=90;
            j=0;
            k=0;
            v=v0+a*t;
            return v;
        }
        return v0;
    }

    public void fallkurveBerechnen(int i, float v0, float a){
        float m=s;

        fallkurveBerechnen(v0,a);

        g.add(s-m);
        g2.add(s-m);
        System.out.println(m-s+"m");

        double faktorx =1-(winkel/0.9)/100;
        double faktory =(winkel/0.9)/100;

        for(int n=0;n<g.size();n++){
            if(n<g.size()-1){
                k=k+g.get(n);
                j=j+g2.get(n);
            }else{
                k= (float) (k+g.get(n)*faktory);
                j= (float) (j+g.get(n)*faktorx);

                g.removeAll(g);

                g2.removeAll(g2);
            }
        }
        System.out.println(k+"k");
        System.out.println(j+"j");
    }

    public void fallkurveBerechnen(float v0, float a){
        float ag=9.81f;
        a= (float) (ag*Math.sin(winkel));
        t++;
        s0=0;
        s=s0+(v0*t)+((0.5f)*a*(t*t));
    }*/


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

            System.out.println("a");
            rKurveR(i,v0,a);
        }else if(points[i][0]<points[i+1][0]&&points[i][1]<points[i+1][1]){
            p = new Point2D(points[i][0],points[i+1][1]);
            v1[0]=points[i+1][0]-p.getX()-j;
            v1[1]=points[i+1][1]-p.getY()-k;
            v2[0]=points[i][0]-p.getX();
            v2[1]=points[i][1]-p.getY();
            winkelBerechnen(v1,v2);

            System.out.println(j+" "+k);
            System.out.println(v1[0]+" "+v1[1]);
            System.out.println(v2[0]+" "+v2[1]);

            System.out.println("b");
            rKurveA(i,v0,a);
        }else if(points[i][0]>points[i+1][0]&&points[i][1]<points[i+1][1]){
            p = new Point2D(points[i][0],points[i+1][1]);
            v1[0]=points[i+1][0]-p.getX()+j;
            v1[1]=points[i+1][1]-p.getY()-k;
            v2[0]=points[i][0]-p.getX();
            v2[1]=points[i][1]-p.getY();
            winkelBerechnen(v1,v2);

            System.out.println(j+" "+k);
            System.out.println(v1[0]+" "+v1[1]);
            System.out.println(v2[0]+" "+v2[1]);

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
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateXProperty(), points[i+1][0])));
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), points[i+1][1])));
            winkel=90;
            j=0;
            k=0;
            v=v0+a*t;
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
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateXProperty(), points[i+1][0])));
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), points[i+1][1])));
            winkel=90;
            j=0;
            k=0;
            v=v0+a*t;
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
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateXProperty(), points[i+1][0])));
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), points[i+1][1])));
            winkel=90;
            j=0;
            k=0;
            v=v0+a*t;
            return v;
        }
        return v0;
    }

    public void kurveBerechnen(int i, float v0, float a){
        float m=s;

        berechnungGerade(v0,a);

        g.add(s-m);
        g2.add(s-m);
        System.out.println(m-s+"m");

        double faktorx =1-(winkel/0.9)/100;
        double faktory =(winkel/0.9)/100;

        for(int n=0;n<g.size();n++){
            if(n<g.size()-1){
                k=k+g.get(n);
                j=j+g2.get(n);
            }else{
                k= (float) (k+g.get(n)*faktory);
                j= (float) (j+g.get(n)*faktorx);

                g.removeAll(g);

                g2.removeAll(g2);
            }
        }
        System.out.println(k+"k");
        System.out.println(j+"j");
    }

    public static double winkelBerechnen(double[] v1, double[] v2)
    {
        double skalarprodukt = 0;

        for (int i = 0; i < v1.length; i++) {
            skalarprodukt = skalarprodukt + v1[i] * v2[i];
        }
        //System.out.println(skalarprodukt);
        double vl1=Math.sqrt(v1[0]*v1[0]+v1[1]*v1[1]);
        double vl2=Math.sqrt(v2[0]*v2[0]+v2[1]*v2[1]);
        //System.out.println(vl1+" "+vl2);
        winkel = Math.acos(skalarprodukt/(vl1*vl2))*180/Math.PI;
        System.out.println("Winkel:"+winkel);
        return winkel;
    }

    //Berechnung für Y-Achse

    public float geradeYR(int i,float v0, float a){
        berechnungGerade(v0,a);
        System.out.println(ball.getTranslateY()+s+"y");
        if((ball.getTranslateY()+s)<=points[i+1][1]){
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), ball.getTranslateY()+s)));
            geradeYR(i,v0,a);
        }else{
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), points[i+1][1])));
            v=v0+a*t;
            return v;
        }
        return v0;
    }

    public float geradeYH(int i,float v0, float a){
        berechnungGerade(v0,a);
        System.out.println(ball.getTranslateY()-s+"y");
        if((ball.getTranslateY()-s)>=points[i+1][1]){
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), ball.getTranslateY()-s)));
            System.out.println(ball.getTranslateY()-s+"y");
            geradeYH(i,v0,a);
        }else{
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateYProperty(), points[i+1][1])));
            v=v0+a*t;
            return v;
        }
        return v0;
    }


    //Berechnung der X-Achse

    public float geradeXL(int i,float v0, float a){
        berechnungGerade(v0,a);
        System.out.println(ball.getTranslateX()-s+"x");
        if((ball.getTranslateX()-s)>=points[i+1][0]){
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateXProperty(), ball.getTranslateX()-s)));
            geradeXL(i,v0,a);
        }else{
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateXProperty(), points[i+1][0])));
            v=v0+a*t;
            return v;
        }
        return v0;
    }

    public float geradeXR(int i,float v0, float a){
        berechnungGerade(v0,a);
        System.out.println(ball.getTranslateX()+s+"x");
        if((ball.getTranslateX()+s)<=points[i+1][0]){
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateXProperty(), ball.getTranslateX()+s)));
            geradeXR(i,v0,a);
        }else{
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateXProperty(), points[i+1][0])));
            v=v0+a*t;
            return v;
        }
        return v0;
    }


    //Berechnung der Z-Achse

    public float geradeZ(int i, float v0, float a){
        berechnungGerade(v0,a);
        System.out.println(ball.getTranslateZ()-s+"z");
        if((ball.getTranslateZ()-s)>=points[i+1][2]){
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateZProperty(), ball.getTranslateZ()-s)));
            geradeZ(i,v0,a);
        }else{
            kf.add(new KeyFrame(Duration.millis((t*50)/slider.getValue()), new KeyValue (ball.translateZProperty(), points[i+1][2])));
            v=v0+a*t;
            return v;
        }
        return v0;
    }

    public void berechnungGerade(float v0, float a){
        t++;
        s0=0;
        s=s0+(v0*t)+((0.5f)*a*(t*t));
    }



    public void animation(){
        /*translate = new TranslateTransition();
        translate.setDuration(Duration.millis(1000));
        translate.setCycleCount(1);
        translate.setAutoReverse(true);
        translate.setNode(ball);*/
        Punkte();
        ball.setTranslateX(points[0][0]);
        ball.setTranslateY(points[0][1]);
        ball.setTranslateZ(points[0][2]);
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        for(int i=0;i<kf.size();i++){
            timeline.getKeyFrames().add(kf.get(i));
        }
        timeline.play();
    }


}