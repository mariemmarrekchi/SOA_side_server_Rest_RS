package services;

import java.util.List;

public interface InterfaceJoeur {
    public Joueur addJoueur(Joueur j) ;
    public List<Joueur> listJoueur();
    public  Joueur getJoueurById(int id);
    public Reponse updateJoueur(Joueur j);
    public Reponse deleteJoueur(int id);
}
