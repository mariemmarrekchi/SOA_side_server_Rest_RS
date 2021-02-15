package services;

import java.util.List;

public interface InterfaceMatch {

    public Match addMatch(Match m) ;
    public List<Match> listMatch();
    public  Match getMatchById(int id);
    public Reponse updateMatch(Match m);
    public void deleteMatch(int id);
}
