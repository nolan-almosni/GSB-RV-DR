package fr.gsb.rv.dr.technique;

import fr.gsb.rv.dr.entites.Visiteur;

public class Session {

    private static Session session = null;
    private Visiteur leVisiteur;

    private Session(Visiteur leVisiteur){
        this.leVisiteur = leVisiteur;
    }

    public static void ouvrir(Visiteur visiteur){
        session = new Session(visiteur);
    }

    public static void fermer(){
        session = null;
    }

    public static Session getSession(){
        return session;
    }

    public Visiteur getLeVisiteur(){
        return leVisiteur;
    }

    public Boolean estOuverte(){
        if(session == null){
            return false;
        }else{
            return true;
        }
    }

}