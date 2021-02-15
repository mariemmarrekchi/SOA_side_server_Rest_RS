package services;

import java.util.List;

public interface InterfaceEquipe {

    public Equipe addEquipe(Equipe e) ;
    public List<Equipe> listEquipe();
    public  Equipe getEquipeById(int id);
    public Reponse updateEquipe(Equipe e);
    public Reponse deleteEquipe(int id);
}
