package fr.gsb.rv.dr.utilitaires;

import fr.gsb.rv.dr.entites.Praticien;

import java.util.Comparator;

public class ComparateurDateVisite implements Comparator<Praticien> {

    public int compare(Praticien o1, Praticien o2){

        if( o1.getDateDerniereVisite() == o2.getDateDerniereVisite()){
            return 0;
        }else if(!(o1.getDateDerniereVisite().isAfter(o2.getDateDerniereVisite()))){
            return 1;
        }else{
            return -1;
        }
    }
}
