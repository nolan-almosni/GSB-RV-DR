package fr.gsb.rv.dr.utilitaires;

import fr.gsb.rv.dr.entites.Praticien;

import java.util.Comparator;

public class ComparateurCoefConfiance implements Comparator<Praticien> {

    public int compare(Praticien o1, Praticien o2){

        if( o1.getDernierCoefConfiance() == o2.getDernierCoefConfiance()){
            return 0;
        }else if( o1.getDernierCoefConfiance() > o2.getDernierCoefConfiance()){
            return 1;
        }else{
            return -1;
        }
    }
}
