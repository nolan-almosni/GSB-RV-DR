package fr.gsb.rv.dr.modeles;

import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                + "AND v.vis_mdp = '" + mdp + "'"
                + " AND v.vis_matricule = '" + matricule + "'";
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
}