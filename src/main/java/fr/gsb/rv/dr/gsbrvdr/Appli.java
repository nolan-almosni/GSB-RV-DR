package fr.gsb.rv.dr.gsbrvdr;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.panneaux.PanneauAccueil;
import fr.gsb.rv.dr.panneaux.PanneauPraticiens;
import fr.gsb.rv.dr.panneaux.PanneauRapports;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Session;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefConfiance;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefNotoriete;
import fr.gsb.rv.dr.utilitaires.ComparateurDateVisite;
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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Appli extends Application {
    @Override
    public void start(Stage stage) throws IOException, ConnexionException {

        BorderPane conteneur = new BorderPane();
        Scene scene = new Scene(conteneur, 600, 550);

        MenuBar barreMenus = new MenuBar();

        conteneur.setTop(barreMenus);

        stage.setTitle("GSB");
        stage.setScene(scene);

        Menu menuFichier = new Menu("Fichier");
        Menu menuRapports = new Menu("Rapports");
        Menu menuPraticiens = new Menu("Praticiens");
        menuRapports.setDisable(true);
        menuPraticiens.setDisable(true);

        MenuItem itemSeConnecter = new MenuItem("Se connecter");
        MenuItem itemSeDeconnecter = new MenuItem("Se deconnecter");
        MenuItem itemQuitter = new MenuItem("Quitter    Ctrl+Q");
        MenuItem itemConsulter = new MenuItem("Consulter");


        MenuItem itemHesitant = new MenuItem("Hésitants");
        itemSeDeconnecter.setDisable(true);

        PanneauAccueil vueAccueil = new PanneauAccueil();
        PanneauRapports vueRapports = new PanneauRapports();
        PanneauPraticiens vuePraticiens = new PanneauPraticiens();
        //StackPane pile = new StackPane();
        //pile.getChildren().addAll(vueAccueil, vueRapports, vuePraticiens);
        //vueAccueil.toFront();
        conteneur.setCenter(vueAccueil);

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
                        VueConnexion vueConnexion = new VueConnexion();
                        Optional<Pair<String,String>> response = vueConnexion.showAndWait();

                        if(response.isPresent()) {
                            try {
                                Visiteur visiteur = ModeleGsbRv.seConnecter(response.get().getKey(), response.get().getValue());
                                if(visiteur != null){
                                    Session.ouvrir(new Visiteur(visiteur.getMatricule(), visiteur.getNom(), visiteur.getPrenom()));
                                    stage.setTitle(Session.getSession().getLeVisiteur().getNom() + " " + Session.getSession().getLeVisiteur().getPrenom());
                                    menuRapports.setDisable(false);
                                    menuPraticiens.setDisable(false);
                                    itemSeDeconnecter.setDisable(false);
                                    itemSeConnecter.setDisable(true);
                                }else {
                                    Alert alertConnectionFailed = new Alert(Alert.AlertType.ERROR);

                                    alertConnectionFailed.setTitle("Erreur de connexion");
                                    alertConnectionFailed.setHeaderText("Votre matricule ou votre mot de passe est incorrect ");

                                    ButtonType btnOk = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);

                                    alertConnectionFailed.getButtonTypes().setAll(btnOk );

                                    Optional<ButtonType> reponse = alertConnectionFailed.showAndWait();
                                }

                            } catch (Exception e) {
                                e.getMessage();
                            }
                        }
                        //try {
                          //  ConnexionBD.getConnexion();
                        //} catch (ConnexionException e) {
                          //  e.printStackTrace();
                        //}
                        //System.out.println("Connexion");
                        //Session.ouvrir(new Visiteur("OB001", "BOUACHI", "Oumayma"));
                        //stage.setTitle(Session.getSession().getLeVisiteur().getNom() + " " + Session.getSession().getLeVisiteur().getPrenom());

                    }
                }
        );

        itemSeDeconnecter.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Session.fermer();
                        stage.setTitle("Hello World");
                        conteneur.setCenter(vueAccueil);
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
                        //vueRapports.toFront();
                        conteneur.setCenter(vueRapports);
                    }
                }
        );

        itemHesitant.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.out.println("[Praticien] " + Session.getSession().getLeVisiteur().getPrenom() + " " + Session.getSession().getLeVisiteur().getNom());
                        //vuePraticiens.toFront();
                        conteneur.setCenter(vuePraticiens);
                        try {
                            List<Praticien>praticiens = ModeleGsbRv.getPraticiensHesitants();
                            System.out.println(praticiens);
                            Collections.sort( praticiens, new ComparateurCoefConfiance());
                            for (Praticien unPraticien : praticiens){
                                System.out.println(unPraticien);
                            }
                            System.out.println("coefNotoriete");
                            Collections.sort( praticiens, new ComparateurCoefNotoriete());
                            for (Praticien unPraticien : praticiens){
                                System.out.println(unPraticien);
                            }
                            System.out.println("date");
                            Collections.sort( praticiens, new ComparateurDateVisite());
                            for (Praticien unPraticien : praticiens){
                                System.out.println(unPraticien);
                            }
                        } catch (ConnexionException e) {
                            e.printStackTrace();
                        }

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