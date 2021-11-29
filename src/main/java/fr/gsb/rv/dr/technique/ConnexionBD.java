package fr.gsb.rv.dr.technique;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnexionBD {

    private static String dbURL = "jdbc:mariadb://localhost:3306/gsbrv" ;
    private static String user = "gsb" ;
    private static String password = "azerty" ;

    private static Connection connexion = null ;

    private ConnexionBD() throws ConnexionException, SQLException {
        try {
            Class.forName( "org.mariadb.jdbc.Driver" ) ;
            connexion = (Connection) DriverManager.getConnection(dbURL, user, password) ;
        }
        catch( Exception e ){
            System.out.println(" nono " + e.getMessage());
            throw new ConnexionException() ;
        }
    }

    public static Connection getConnexion() throws ConnexionException {
        if( connexion == null ){
            try {
                new ConnexionBD() ;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connexion ;
    }
}