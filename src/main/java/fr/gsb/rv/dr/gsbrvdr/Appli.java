package fr.gsb.rv.dr.gsbrvdr;

import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Session;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Appli extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        BorderPane conteneur = new BorderPane();
        Scene scene = new Scene(conteneur, 600, 550);

        MenuBar barreMenus = new MenuBar();

        conteneur.setTop(barreMenus);

        stage.setTitle("Hello World!");
        stage.setScene(scene);

        Menu menuFichier = new Menu("Fichier");
        Menu menuRapports = new Menu("Rapports");
        Menu menuPraticiens = new Menu("Praticiens");
        menuRapports.setDisable(true);
        menuPraticiens.setDisable(true);

        //SeparatorMenuItem sep = new SeparatorMenuItem();
        //menuFichier.getItems().add(2,sep);

        MenuItem itemSeConnecter = new MenuItem("Se connecter");
        MenuItem itemSeDeconnecter = new MenuItem("Se deconnecter");
        MenuItem itemQuitter = new MenuItem("Quitter    Ctrl+Q");
        MenuItem itemConsulter = new MenuItem("Consulter");
        MenuItem itemHesitant = new MenuItem("Hésitants");
        itemSeDeconnecter.setDisable(true);



        itemQuitter.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Alert alertQuitter = new Alert(Alert.AlertType.CONFIRMATION);

                        alertQuitter.setTitle("Quitter");
                        alertQuitter.setHeaderText("Demande de confirmation");
                        alertQuitter.setContentText(" Voulez-vous quitter l'application ?");

                        ButtonType btnOui = new ButtonType("oui");
                        ButtonType btnNon = new ButtonType("non");

                        alertQuitter.getButtonTypes().setAll(btnOui, btnNon);

                        Optional<ButtonType> reponse = alertQuitter.showAndWait();

                        if(reponse.get() == btnOui) {
                            Platform.exit();
                        }
                    }
                }
        );

        itemSeConnecter.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            ConnexionBD.getConnexion();
                        } catch (ConnexionException e) {
                            e.printStackTrace();
                        }
                        //System.out.println("Connexion");
                        //Session.ouvrir(new Visiteur("OB001", "BOUACHI", "Oumayma"));
                        //stage.setTitle(Session.getSession().getLeVisiteur().getNom() + " " + Session.getSession().getLeVisiteur().getPrenom());
                        menuRapports.setDisable(false);
                        menuPraticiens.setDisable(false);
                        itemSeDeconnecter.setDisable(false);
                        itemSeConnecter.setDisable(true);
                    }
                }
        );

        itemSeDeconnecter.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        //Session.fermer();
                        //stage.setTitle("Hello World");
                        System.out.println("Deconnexion");
                        menuRapports.setDisable(true);
                        menuPraticiens.setDisable(true);
                        itemSeDeconnecter.setDisable(true);
                        itemSeConnecter.setDisable(false);
                    }
                }
        );

        itemConsulter.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.out.println("[Rapports] " + Session.getSession().getLeVisiteur().getPrenom() + " " + Session.getSession().getLeVisiteur().getNom());
                    }
                }
        );

        itemHesitant.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.out.println("[Praticien] " + Session.getSession().getLeVisiteur().getPrenom() + " " + Session.getSession().getLeVisiteur().getNom());
                    }
                }
        );


        menuFichier.getItems().add( itemSeConnecter );
        menuFichier.getItems().add( itemSeDeconnecter );
        menuFichier.getItems().add( itemQuitter );
        menuRapports.getItems().add( itemConsulter );
        menuPraticiens.getItems().add( itemHesitant );

        barreMenus.getMenus().add(menuFichier);
        barreMenus.getMenus().add(menuRapports);
        barreMenus.getMenus().add(menuPraticiens);



        stage.getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                KeyCombination keyComb = new KeyCodeCombination(KeyCode.Q,
                        KeyCombination.CONTROL_DOWN);
                if (keyComb.match(e)) {
                    Platform.exit();
                }
            }
        });
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}