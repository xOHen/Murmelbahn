package lhm;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class Felder{
    int[][] ebenearr;
    TilePane tile = new TilePane();
    ImageView[][] felder;

    public Felder(ImageView[][]felder){
        this.felder=felder;
    }

    public void felderBauen(){
        tile.setAlignment(Pos.CENTER);
        tile.setPrefColumns(5);
        tile.setPrefRows(5);

        ebenearr= new int[][]{
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
    }

}
