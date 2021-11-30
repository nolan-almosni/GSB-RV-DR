package fr.gsb.rv.dr.panneaux;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PanneauAccueil extends StackPane {

    public PanneauAccueil(){
        super();
        VBox accueil = new VBox();
        Label labelAccueil = new Label("Accueil");

        accueil.setStyle("-fx-background-color: white;");
        accueil.getChildren().add(labelAccueil);

        this.getChildren().add(accueil);

    }


    public void toFront() {
    }
}
