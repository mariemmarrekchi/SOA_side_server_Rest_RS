package services;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "match")
public class Match {
    private int id;
    private String libelle;
    private String date;
    private int id_tour;

    public Match(String libelle) {
        this.libelle = libelle;
    }

    public Match(String libelle, String date, int id_tour) {
        this.libelle = libelle;
        this.date = date;
        this.id_tour = id_tour;
    }

    public Match(int id, String libelle, String date, int id_tour) {

this.id=id;
this.libelle = libelle;
        this.date = date;
        this.id_tour = id_tour;
    }



    public Match(){
        System.out.println("creation Match");
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId_tour() {
        return id_tour;
    }

    public void setId_tour(int id_tour) {
        this.id_tour = id_tour;
    }
}
