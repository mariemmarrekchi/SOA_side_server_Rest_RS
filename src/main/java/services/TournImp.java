package services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Consumes( {MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//spécifier le type de représentation de la valeur de retour
@Produces( {MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
@Path("/tournoir")
public class TournImp implements InterfaceTour{

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
    public Tournoir addTournoi(Tournoir p) {
        Connection con = null;
        PreparedStatement ps = null;
        Statement stmt =null;
        Tournoir np =null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" insert into tournoir (id, libelle, date)  values (0,?,?)");
            // passer les paramètres (nom et age) ( la colonne id est auto incrémentable)
            ps.setString(1, p.getLibelle());
            ps.setString(2, p.getDate());

            // executer la requête insert
            ps.executeUpdate();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `tournoir` WHERE id =( select max(id) from tournoir)");

            while (rs.next()) {
                int id = rs.getInt("id");
                String libelle = rs.getString("libelle");
                String date = rs.getString("date");

                np = new Tournoir(id,libelle,date);
            }

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
        return np;
    }


    @Override
    @GET
    @Path("/getAll")
    public List<Tournoir> listTournoi() {
        List<Tournoir> pers = new ArrayList<Tournoir>();

        Connection con = null;
        Statement stmt = null;

        try {
            con = getConnexion();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tournoir");

            while (rs.next()) {
                int id = rs.getInt("id");
                String libelle = rs.getString("libelle");
                String date = rs.getString("date");

                Tournoir p = new Tournoir(id,libelle,date);
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
    public Reponse updateTournoi(Tournoir t ){
        Reponse reponse = new Reponse();
        Connection con = null;

        try {
            con = getConnexion();
            // préparer la requête SQL
            PreparedStatement ps = con.prepareStatement("update tournoir set  libelle = ?,date = ? where id= ?");
            ps.setString(1, t.getLibelle());
            ps.setString(2, t.getDate());
            ps.setInt(3, t.getId());
            ps.executeUpdate();
            reponse.setMessage("tournoir modifié avec succès...");
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
    public Tournoir getTourById(@PathParam("id") int id) {
        Tournoir t=null;
        try {
            Connection cnx= getConnexion();
            PreparedStatement ps=cnx.prepareStatement("select * from tournoir where id=? ");
            ps.setInt(1,id);

            ResultSet rs=ps.executeQuery();
            if(rs!=null)
            {
                while(rs.next())
                {
                    t=new Tournoir(rs.getInt("id"),rs.getString("libelle"),rs.getString("date"));
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
    @DELETE
    @Path("/{id}/delete")
    public void deleteTournoi(@PathParam("id") int id) {
        Connection con = null;
        PreparedStatement ps = null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" delete from tournoir  where id=? ");
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
    public void deleteJ(Tournoir t) {
        Connection con = null;
        PreparedStatement ps = null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" delete from tournoir  where id=? ");
            // passer le paramètre id
            ps.setInt(1, t.getId());
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
