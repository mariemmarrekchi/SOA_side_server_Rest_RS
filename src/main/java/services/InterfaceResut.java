package services;

import java.util.List;

public interface InterfaceResut {
    public Result addResult(Result r) ;
    public List<Result> listResult();
    public  Result getResById(int id);
    public Reponse updateResult(Result m);
    public void deleteResult(int id);
}
