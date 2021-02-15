package services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Consumes( {MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//spécifier le type de représentation de la valeur de retour
@Produces( {MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
@Path("/result")
public class ResultImp implements InterfaceResut {
    private final static String URL = "jdbc:mysql://localhost:3306/soa";
    private final static String LOGIN = "root";
    private final static String PASSWORD = "";
    private Connection getConnexion() throws SQLException {
        final Connection con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        return con;
    }
    @Override
    @POST
    @Path("/ajout")
    public Result addResult(Result r) {
        Connection con = null;
        PreparedStatement ps = null;
        Statement stmt =null;
        Result np =null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" insert into resl (id, id_eq,id_mat,id_jour,nom_joueur,carte_j,carte_r,etat)  values (0,?,?,?,?,?,?,?)");
            // passer les paramètres (nom et age) ( la colonne id est auto incrémentable)
            ps.setInt(1, r.getId_eq());
            ps.setInt(2, r.getId_mat());
            ps.setInt(3, r.getId_jour());
            ps.setString(4, r.getNom_joueur());
            ps.setInt(5, r.getCarte_j());
            ps.setInt(6, r.getCarte_r());
            ps.setString(7, r.getEtat());

            // executer la requête insert
            ps.executeUpdate();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `resl` WHERE id =( select max(id) from resl)");

            while (rs.next()) {
                int id = rs.getInt("id");
                int id_eq = rs.getInt("id_eq");
                int id_mat = rs.getInt("id_mat");
                int id_jour = rs.getInt("id_jour");
                String nom_joueur = rs.getString("nom_joueur");

                int carte_j = rs.getInt("carte_j");
                int carte_r = rs.getInt("carte_r");
                String  etat = rs.getString("etat");


                np = new Result(id,id_eq,id_mat,id_jour,nom_joueur,carte_j,carte_r,etat);
            }

        }
        catch (SQLException e1) {
            e1.printStackTrace();
        } finally {

            if (ps != null) {
                try {
                    // Le ps.close ferme automatiquement le resultSet
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return np;
    }

    @Override
    @GET
    @Path("/getAll")
    public List<Result> listResult() {
        List<Result> pers = new ArrayList<Result>();

        Connection con = null;
        Statement stmt = null;

        try {
            con = getConnexion();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM resl ");

            while (rs.next()) {
                int id = rs.getInt("id");
                int id_eq = rs.getInt("id_eq");
                int id_mat = rs.getInt("id_mat");
                int id_jour = rs.getInt("id_jour");
                int carte_j = rs.getInt("carte_j");
                int carte_r = rs.getInt("carte_r");
                String nom_joueur = rs.getString("nom_joueur");
                String  etat = rs.getString("etat");


                Result p = new Result(id,id_eq,id_mat,id_jour,nom_joueur,carte_j,carte_r,etat);

                pers.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (stmt != null) {
                try {
                    // Le stmt.close ferme automatiquement le resultSet
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }return pers;
    }

    @Override
    @GET
    @Path("/{id}/get")
    public Result getResById(@PathParam("id")int id) {
        Result t=null;
        try {
            Connection cnx= getConnexion();
            PreparedStatement ps=cnx.prepareStatement("select * from resl where id=? ");
            ps.setInt(1,id);

            ResultSet rs=ps.executeQuery();
            if(rs!=null)
            {
                while(rs.next())
                {
                    t=new Result(rs.getInt("id"),rs.getInt("id_eq"),rs.getInt("id_mat"),rs.getInt("id_jour"),rs.getString("nom_joueur"),rs.getInt("carte_j"),rs.getInt("carte_r"),rs.getString("etat"));
                }
            }
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    @PUT
    @Path("/update")
    public Reponse updateResult(Result m) {
        Reponse reponse = new Reponse();
        Connection con = null;

        try {
            con = getConnexion();
            // préparer la requête SQL
            PreparedStatement ps = con.prepareStatement("update resl set  id_eq = ?,id_mat = ?,id_jour=? ,nom_joueur=?,carte_j=?,carte_r =? ,etat=? where id= ?");
            ps.setInt(1, m.getId_eq());
            ps.setInt(2, m.getId_mat());
            ps.setInt(3, m.getId_jour());
            ps.setString(4, m.getNom_joueur());
            ps.setInt(5, m.getCarte_j());
            ps.setInt(6, m.getCarte_r());
            ps.setString(7, m.getEtat());

            ps.setInt(8, m.getId());
            ps.executeUpdate();
            reponse.setMessage("reslt modifié avec succès...");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            reponse.setMessage("Error :( ");
        }
        return  reponse;
    }

    @Override
    @DELETE
    @Path("/{id}/delete")
    public void deleteResult(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" delete from resl  where id=? ");
            // passer le paramètre id
            ps.setInt(1, id);
            // executer la requête
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (ps != null) {
                try {
                    // Le ps.close ferme automatiquement le resultSet
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @DELETE
    @Path("/delete")
    public void deleteJ(Result r) {
        Connection con = null;
        PreparedStatement ps = null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" delete from resl  where id=? ");
            // passer le paramètre id
            ps.setInt(1, r.getId());
            // executer la requête
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (ps != null) {
                try {
                    // Le ps.close ferme automatiquement le resultSet
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    }

