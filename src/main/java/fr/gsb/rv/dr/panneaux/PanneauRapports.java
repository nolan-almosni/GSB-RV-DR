package fr.gsb.rv.dr.panneaux;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PanneauRapports extends StackPane {

    public PanneauRapports(){

        VBox rapport = new VBox();

        rapport.setStyle("-fx-background-color: white;");

        Label labelRaport = new Label("Raport");

        rapport.getChildren().add(labelRaport);
        this.getChildren().add(rapport);

    }

}
