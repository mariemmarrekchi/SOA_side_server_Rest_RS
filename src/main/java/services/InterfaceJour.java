package services;
import java.util.List;
public interface InterfaceJour {
    public Jour addJour(Jour j) ;
    public List<Jour> listJour();
    public  Jour getJourById(int id);
    public Reponse updateJour(Jour j);
    public Reponse deleteJour(int id);
}
