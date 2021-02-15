package services;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "reslt")
public class Result {
    private int id;
    private int id_eq;
    private int id_mat;
    private String nom_joueur;
    private int carte_j;
    private int carte_r;
    private String etat;
    private int id_jour;


    public int getId_jour() {
        return id_jour;
    }

    public void setId_jour(int id_jour) {
        this.id_jour = id_jour;
    }

    public Result(int id, int id_eq, int id_mat, int id_jour, String nom_joueur, int carte_j, int carte_r, String etat) {
        this.id = id;
        this.id_eq = id_eq;
        this.id_mat = id_mat;
        this.nom_joueur=nom_joueur;
        this.carte_j=carte_j;
        this.carte_r=carte_r;
        this.etat=etat;
        this.id_jour=id_jour;

    }

    public Result(int id_eq, int id_mat, int id_jour,String nom_joueur,int carte_j,int carte_r,String etat) {
        this.id_eq = id_eq;
        this.id_mat = id_mat;
        this.nom_joueur=nom_joueur;
        this.carte_j=carte_j;
        this.carte_r=carte_r;
        this.etat=etat;
        this.id_jour=id_jour;

    }
    public String getNom_joueur() {
        return nom_joueur;
    }

    public void setNom_joueur(String nom_joueur) {
        this.nom_joueur = nom_joueur;
    }

    public int getCarte_j() {
        return carte_j;
    }

    public void setCarte_j(int carte_j) {
        this.carte_j = carte_j;
    }

    public int getCarte_r() {
        return carte_r;
    }

    public void setCarte_r(int carte_r) {
        this.carte_r = carte_r;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Result(){
        System.out.println("création Resultat");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_eq() {
        return id_eq;
    }

    public void setId_eq(int id_eq) {
        this.id_eq = id_eq;
    }

    public int getId_mat() {
        return id_mat;
    }

    public void setId_mat(int id_mat) {
        this.id_mat = id_mat;
    }

}
