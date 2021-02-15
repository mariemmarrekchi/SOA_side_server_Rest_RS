package services;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "jour")
public class Jour {
    private int id;
    private String date;
    private int id_mat;
    private int id_eq;
private int id_eq2;
    private int carte_j;
    private int carte_r;
    private int carte_j2;
    private int carte_r2;
    private String nb_bute;
    public int getId_eq2() {
        return id_eq2;
    }

    public void setId_eq2(int id_eq2) {
        this.id_eq2 = id_eq2;
    }

    public String getNb_bute() {
        return nb_bute;
    }

    public void setNb_bute(String nb_bute) {
        this.nb_bute = nb_bute;
    }


    public Jour() {
        System.out.println("création journnée");
    }

    public int getId_mat() {
        return id_mat;
    }

    public void setId_mat(int id_mat) {
        this.id_mat = id_mat;
    }

    public int getId_eq() {
        return id_eq;
    }

    public void setId_eq(int id_eq) {
        this.id_eq = id_eq;
    }

    public Jour(int id, String date, int id_mat,int id_eq, int carte_j, int carte_r,String nb_bute,int id_eq2,int carte_j2, int carte_r2) {
        this.id = id;
        this.id_eq2=id_eq2;
        this.date = date;
        this.id_mat=id_mat;
        this.id_eq=id_eq;
        this.carte_j = carte_j;
        this.carte_r = carte_r;   this.carte_j2 = carte_j2;
        this.carte_r2 = carte_r2;
        this.nb_bute=nb_bute;
    }

    public int getCarte_j2() {
        return carte_j2;
    }

    public void setCarte_j2(int carte_j2) {
        this.carte_j2 = carte_j2;
    }

    public int getCarte_r2() {
        return carte_r2;
    }

    public void setCarte_r2(int carte_r2) {
        this.carte_r2 = carte_r2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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


}
