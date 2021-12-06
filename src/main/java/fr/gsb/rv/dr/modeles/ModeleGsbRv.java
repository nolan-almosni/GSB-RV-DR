package fr.gsb.rv.dr.modeles;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ModeleGsbRv {

    public static Visiteur seConnecter( String matricule , String mdp ) throws ConnexionException{

        // Code de test à compléter

        Connection connexion = ConnexionBD.getConnexion() ;

        String requete = "SELECT v.vis_matricule, v.vis_nom, v.vis_prenom "
                + "FROM ( SELECT vis_matricule, MAX(jjmmaa) AS MaxDate "
                + "FROM Travailler "
                + "GROUP BY vis_matricule) derniere_date_for_each_visiteur "
                + "JOIN Travailler t ON derniere_date_for_each_visiteur.vis_matricule = t.vis_matricule "
                + "JOIN Visiteur v ON v.vis_matricule = t.vis_matricule "
                + "AND derniere_date_for_each_visiteur.MaxDate = t.jjmmaa "
                + "AND t.tra_role = 'Délégué' "
                + "AND v.vis_mdp = '" + mdp + "' "
                + "AND v.vis_matricule = '" + matricule + "'";
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
            requetePreparee.setString( 1 , matricule );
            ResultSet resultat = requetePreparee.executeQuery() ;
            if( resultat.next() ){
                Visiteur visiteur = new Visiteur(resultat.getString("vis_matricule"), resultat.getString("vis_nom"), resultat.getString("vis_prenom")) ;

                requetePreparee.close() ;
                return visiteur ;
            }
            else {
                return null ;
            }
        }
        catch( Exception e ){
            return null ;
        }
    }

    public  static List<Praticien> getPraticiensHesitants() throws ConnexionException{

        Connection connexion = ConnexionBD.getConnexion();

        List<Praticien> praticiens = FXCollections.observableArrayList();

        String requete = "SELECT p.pra_num, p.pra_nom, p.pra_ville, p.pra_coefnotoriete, rv.rap_date_visite, rv.rap_coef_confiance "
                + "FROM Praticien as p "
                + "INNER JOIN ( SELECT MAX(rap_date_visite) AS dateDerniereVisite "
                + "FROM RapportVisite "
                + "GROUP BY pra_num) r "
                + "INNER JOIN RapportVisite as rv ON p.pra_num=rv.pra_num "
                + "WHERE rv.rap_date_visite=r.dateDerniereVisite "
                + "AND rv.rap_coef_confiance < 5 "
                + "GROUP BY pra_num";

        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
            ResultSet resultat = requetePreparee.executeQuery() ;
            while( resultat.next() ){
                Praticien praticien = new Praticien(resultat.getInt("p.pra_num"),
                        resultat.getString("p.pra_nom"),
                        resultat.getString("p.pra_ville"),
                        resultat.getDouble("p.pra_coefnotoriete"),
                        resultat.getDate("rv.rap_date_visite").toLocalDate(),
                        resultat.getInt("rv.rap_coef_confiance")) ;
                praticiens.add(praticien);
            }
            return praticiens ;
        }
        catch( Exception e ){
            e.printStackTrace();
        }
        return praticiens;
    }
}