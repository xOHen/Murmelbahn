package lhm;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.scene.control.Slider;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

public class Pfadberechnung{
    Sphere ball;
    Slider slider;
    final Path pfad = pfadErstellen();
    PathTransition pfadtrans = generatePathTransition(ball,pfad);

    public Pfadberechnung(Sphere ball, Slider slider){
        this.ball=ball;
        this.slider=slider;
    }

    private Path pfadErstellen(){
        Path pfad = new Path();

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

    private PathTransition generatePathTransition(final Sphere shape, final Path path)
    {
        final PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(8.0));
        //pathTransition.setRate(slider.getValue());
        pathTransition.setPath(path);
        pathTransition.setNode(shape);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Animation.INDEFINITE);
        return pathTransition;
    }
}
