package fr.gsb.rv.dr.panneaux;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefConfiance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


import java.util.Collections;
import java.util.List;

public class PanneauPraticiens extends StackPane{

    public static int CRITERE_COEF_CONFIANCE = 1;
    public static int CRITERE_COEF_NOTORIETE = 2;
    public static int CRITERE_COEF_VISITE = 3;

    private int critereTri = CRITERE_COEF_CONFIANCE;

    private RadioButton rbCoefConfiance = new RadioButton("confiance");
    private RadioButton rbCoefNotoriete = new RadioButton("notoriete");
    private RadioButton rbDateVisite = new RadioButton("Date visite");

    private TableView<Praticien> tablePraticien= new TableView();

    public PanneauPraticiens(){
        VBox praticiens = new VBox();

        ToggleGroup boutons = new ToggleGroup();

        rbCoefConfiance.setToggleGroup(boutons);
        rbCoefNotoriete.setToggleGroup(boutons);
        rbDateVisite.setToggleGroup(boutons);
        rbCoefConfiance.setSelected(true);

        Label labelPraticiens = new Label("Praticiens");

        TableColumn<Praticien, Integer> colNumero= new TableColumn<Praticien,Integer>("numero");
        TableColumn<Praticien, String> colNom= new TableColumn<>("nom");
        TableColumn<Praticien, String> colVille= new TableColumn<>("ville");

        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colVille.setCellValueFactory(new PropertyValueFactory<>("ville"));

        tablePraticien.getColumns().setAll(colNumero, colNom, colVille);

        praticiens.getChildren().add(labelPraticiens);
        praticiens.getChildren().addAll(rbCoefConfiance, rbCoefNotoriete, rbDateVisite);
        praticiens.getChildren().add(tablePraticien);
        this.getChildren().add(praticiens);

        List<Praticien> listePraticiens = null;
        try {
            listePraticiens = ModeleGsbRv.getPraticiensHesitants();
            ObservableList<Praticien> observablePraticiens= FXCollections.observableArrayList(listePraticiens);
            Collections.sort(observablePraticiens, new ComparateurCoefConfiance());

            for( Praticien praticien : observablePraticiens){
                tablePraticien.getItems().add(praticien);
            }
        } catch (ConnexionException e) {
            e.printStackTrace();
        }


        rbCoefConfiance.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(critereTri == CRITERE_COEF_CONFIANCE){
                            rafraichir();
                            }
                        }
                }
        );

    }

    public void rafraichir(){
        try {
            List<Praticien> listePraticiens = ModeleGsbRv.getPraticiensHesitants();
            ObservableList<Praticien> praticiens= FXCollections.observableArrayList(listePraticiens);

            if(this.getCritereTri() == CRITERE_COEF_CONFIANCE){
                Collections.sort(praticiens, new ComparateurCoefConfiance());
                tablePraticien.setItems(praticiens);
            }

        } catch (ConnexionException e) {
            e.printStackTrace();
        }
        new PanneauPraticiens();
    }

    public int getCritereTri(){
        return critereTri;
    }

    public void setCritereTri(int critere){
        this.critereTri = critere;
        rafraichir();
    }

}
