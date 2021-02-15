package services;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tourn")
public class Tournoir {
    private int id;
    private String libelle;
private  String date;
    public Tournoir(int id, String libelle, String date) {
        this.id = id;
        this.libelle = libelle;
        this.date = date;
    }

    public Tournoir(String libelle, String date) {
        this.libelle = libelle;
        this.date = date;
    }

    public Tournoir() {
        System.out.println("création Tournoir");
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setDate(String date) {
        this.date = date;
    }
}
