package fr.gsb.rv.dr.panneaux;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PanneauPraticiens extends StackPane{

    public PanneauPraticiens(){
        VBox praticiens = new VBox();

        Label labelPraticiens = new Label("Praticiens");

        praticiens.getChildren().add(labelPraticiens);
        this.getChildren().add(praticiens);

    }

}
