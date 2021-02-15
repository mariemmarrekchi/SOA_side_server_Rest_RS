package services;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Blob;

@XmlRootElement(name = "joueur")
public class Joueur {
    private int id;
    private String nom;
    private int id_eq;
    private String prenom;
    private int age;
    public Joueur() {
        System.out.println("creation joueur");
    }


    public Joueur(int id, String nom, int id_eq,String prenom,int age) {
        this.id = id;
        this.nom = nom;

        this.id_eq = id_eq;
        this.prenom=prenom;
        this.age=age;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }



    public int getId_eq() {
        return id_eq;
    }

    public void setId_eq(int id_eq) {
        this.id_eq = id_eq;
    }
}
