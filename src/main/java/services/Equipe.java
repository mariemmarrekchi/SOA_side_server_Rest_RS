package services;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "equipe")

public class Equipe {
    private int id;

    public Equipe(String nom) {
        this.nom = nom;

    }

    private String nom;



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


    public Equipe(int id,String nom) {
        this.id=id;
        this.nom = nom;

    }

    public Equipe() {
        System.out.println("création equipe");
    }
}
