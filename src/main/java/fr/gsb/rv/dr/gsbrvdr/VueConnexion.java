package fr.gsb.rv.dr.gsbrvdr;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

import java.util.Optional;
import java.util.stream.Stream;

public class VueConnexion extends Dialog<Pair<String,String>> {

    public VueConnexion() {

        this.setTitle("Connexion");

        this.setContentText("Saisir vos données de connexion.");

        Label label1 = new Label(" matricule : ");
        Label label2 = new Label(" mot de passe : ");

        TextField inputMatricule = new TextField();
        PasswordField inputMdp = new PasswordField();

        GridPane gridPane = new GridPane();
        gridPane.add(label1,0,0);
        gridPane.add(label2,0,1);
        gridPane.add(inputMatricule,1,0);
        gridPane.add(inputMdp,1,1);


        ButtonType submit = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        this.getDialogPane().getButtonTypes().addAll(submit, cancel);

        this.setResultConverter(new Callback<ButtonType, Pair<String, String>>() {
            @Override
            public Pair<String, String> call(ButtonType buttonType) {
                if(buttonType == submit ){
                    return new Pair<String,String>(inputMatricule.getText(),inputMdp.getText());
                }else{
                    return null;
                }
            }
        });


        this.getDialogPane().setContent(gridPane);
    }
}
