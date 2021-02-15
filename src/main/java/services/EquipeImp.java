package services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Consumes( {MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//spécifier le type de représentation de la valeur de retour
@Produces( {MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
@Path("/equipe")
public class EquipeImp implements InterfaceEquipe{

    private final static String URL = "jdbc:mysql://localhost:3306/soa";

    /**
     * Nom du user
     */
    private final static String LOGIN = "root";

    /**
     * Mot de passe du user
     */
    private final static String PASSWORD = "";

    private Connection getConnexion() throws SQLException {
        final Connection con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        return con;
    }
    @Override
    @POST
    @Path("/ajout")
    // ajouter une personne (en se basant sur l'objet reçu en pramètre)
    public Equipe addEquipe(Equipe e) {
        Connection con = null;
        PreparedStatement ps = null;
        Statement stmt =null;
        Equipe np =null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" insert into equipe (id, nom, )  values (0,?)");
            // passer les paramètres (nom et age) ( la colonne id est auto incrémentable)
            ps.setString(1, e.getNom());


            // executer la requête insert
            ps.executeUpdate();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `equipe` WHERE id =( select max(id) from equipe)");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");


                np = new Equipe(id,nom);
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
    public List<Equipe> listEquipe() {
        List<Equipe> pers = new ArrayList<Equipe>();

        Connection con = null;
        Statement stmt = null;

        try {
            con = getConnexion();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM equipe ");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");



                Equipe p = new Equipe(id,nom);
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

    @PUT
    @Path("/update")
    public Reponse updateEquipe(Equipe ep ){
        Reponse reponse = new Reponse();
        Connection con = null;

        try {
            con = getConnexion();
            // préparer la requête SQL
            PreparedStatement ps = con.prepareStatement("update equipe set  nom = ?  where id= ?");
            ps.setString(1, ep.getNom());

            ps.setInt(5, ep.getId());
            ps.executeUpdate();
            reponse.setMessage("equipe modifié avec succès...");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            reponse.setMessage("Error :( ");
        }
        return  reponse;
    }
    @GET
    @Path("/{id}/get")
    public Equipe getEquipeById(@PathParam("id") int id) {
        Equipe e=null;
        try {
            Connection cnx= getConnexion();
            PreparedStatement ps=cnx.prepareStatement("select * from equipe where id=? ");
            ps.setInt(1,id);

            ResultSet rs=ps.executeQuery();
            if(rs!=null)
            {
                while(rs.next())
                {
                    e=new Equipe(rs.getInt("id"),rs.getString("nom"));
                }
            }
            ps.close();
        }
        catch(SQLException e1)
        {
            e1.printStackTrace();
        }
        return e;

    }
    @Override
    @DELETE
    @Path("/{id}/delete")
    public Reponse deleteEquipe(@PathParam("id") int id) {
        Reponse reponse = new Reponse();
        try {
            Equipe tour=getEquipeById(id);
            if (tour == null) {
                reponse.setStatus(false);
                reponse.setMessage("Equipe non existante...");
                return reponse;
            }
            Connection cnx=getConnexion();
            PreparedStatement ps = cnx.prepareStatement("delete from equipe where id= ?");
            ps.setInt(1,id);
            ps.executeUpdate();
            reponse.setMessage("equipe supprimé avec succès...");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            reponse.setMessage("Error :( ");

        }
        return reponse;
    }

    @DELETE
    @Path("/delete")
    public void deleteJ(Equipe ep) {
        Connection con = null;
        PreparedStatement ps = null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" delete from equipe  where id=? ");
            // passer le paramètre id
            ps.setInt(1, ep.getId());
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
