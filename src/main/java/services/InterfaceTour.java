package services;

import java.util.List;

public interface InterfaceTour {

    public Tournoir addTournoi(Tournoir t) ;
    public List<Tournoir> listTournoi();
    public  Tournoir getTourById(int id);
    public Reponse updateTournoi(Tournoir m);
    public void deleteTournoi(int id);
}
